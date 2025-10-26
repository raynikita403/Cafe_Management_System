package in.nikita.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nikita.entity.UserRegisterEntity;
import in.nikita.repository.UserRegisterRepository;
import in.nikita.repository.OrdersRepository;
import in.nikita.util.JWTUtils;

import jakarta.annotation.PostConstruct;

@Service
@Transactional
public class UserRegisterServiceImpli implements UserRegisterService {

    @Autowired
    private UserRegisterRepository userRepo;

    @Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtil;

    // ---------------- Registration ----------------
    @Override
    public UserRegisterEntity registerUser(UserRegisterEntity register) {
        if (emailExists(register.getUserEmail())) {
            return null; // email already exists
        }
        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
        register.setActive(true); 
        return userRepo.save(register);
    }

    // ---------------- Check Email Exists ----------------
    @Override
    public boolean emailExists(String email) {
        return userRepo.existsByUserEmail(email);
    }

    // ---------------- Add User ----------------
    @Override
    public UserRegisterEntity addUser(String userName, String userEmail, String userPassword, String role) {
        UserRegisterEntity u = new UserRegisterEntity();
        u.setUserName(userName);
        u.setUserEmail(userEmail);
        u.setUserPassword(passwordEncoder.encode(userPassword));
        u.setRole(role != null ? role : "ROLE_USER");
        u.setToken(null);
        u.setActive(true); 
        return userRepo.save(u);
    }

    // ---------------- Login ----------------
    @Override
    public UserRegisterEntity loginUser(String email, String password) {
    	   System.out.println("👉 Attempting login for email: " + email);
        Optional<UserRegisterEntity> optionalUser = userRepo.findByUserEmail(email);
        if (optionalUser.isPresent()) {
            UserRegisterEntity user = optionalUser.get();
         
            // First check password
            if (!passwordEncoder.matches(password, user.getUserPassword())) {
            	 System.out.println("❌ Invalid password for email: " + email);
                return null; // invalid password
            }

          
            if (!user.isActive()) {
                return user; 
            }

            // User is active, generate token
            String token = jwtUtil.generateToken(user.getUserEmail(), user.getRole(), user.getUserName());
            user.setToken(token);
            return userRepo.save(user);
        }
        return null;
    }

    // ---------------- Logout ----------------
    @Override
    @Transactional
    public void logoutUser(String email) {
        userRepo.findByUserEmail(email).ifPresent(user -> {
            user.setToken(null);
            userRepo.saveAndFlush(user); // force update immediately
        });
    }

    // ---------------- Admin Setup ----------------
    @PostConstruct
    public void createAdminIfNotExist() {
        String adminEmail = "admin9977@gmail.com";
        if (!userRepo.existsByUserEmail(adminEmail)) {
            UserRegisterEntity admin = new UserRegisterEntity();
            admin.setUserName("Admin");
            admin.setUserEmail(adminEmail);
            admin.setUserPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole("ROLE_ADMIN");
            admin.setActive(true);
            admin.setToken(null);
            userRepo.save(admin);
            System.out.println("Admin user created!");
        }
    }

    // ---------------- Get All Users ----------------
    @Override
    public List<UserRegisterEntity> getAllUsers() {
        return userRepo.findAll();
    }

    // ---------------- Get User By ID ----------------
    @Override
    public UserRegisterEntity getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    // ---------------- Toggle Active Status (Soft Delete) ----------------
    @Override
    public UserRegisterEntity toggleUserActive(Integer id) {
        UserRegisterEntity user = getUserById(id);
        if (user != null) {
            user.setActive(!user.isActive()); 
            return userRepo.save(user);       
        }
        
        return null;
    }
}

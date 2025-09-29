package in.nikita.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.nikita.entity.UserRegisterEntity;
import in.nikita.repository.UserRegisterRepository;
import in.nikita.util.JWTUtils;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserRegisterServiceImpli implements UserRegisterService {

    @Autowired
    private UserRegisterRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtil;  // use your JWTUtils class

    // ---------------- Registration ----------------
    @Override
    public UserRegisterEntity registerUser(UserRegisterEntity register) {
        if (emailExists(register.getUserEmail())) {
            return null; // email already exists
        }
        // encode password before saving
        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
        return repo.save(register);
    }

    // ---------------- Check if email exists ----------------
    @Override
    public boolean emailExists(String email) {
        return repo.existsByUserEmail(email);
    }

    // ---------------- Add User ----------------
    @Override
    public UserRegisterEntity addUser(String userName, String userEmail, String userPassword, String role) {
        UserRegisterEntity u = new UserRegisterEntity();
        u.setUserName(userName);
        u.setUserEmail(userEmail);
        u.setUserPassword(passwordEncoder.encode(userPassword)); // encode password
        u.setRole(role != null ? role : "ROLE_USER");
        u.setToken(null);

        return repo.save(u);
    }

    // ---------------- Login method ----------------
    @Override
    public UserRegisterEntity loginUser(String email, String password) {
        Optional<UserRegisterEntity> optionalUser = repo.findByUserEmail(email);
        if (optionalUser.isPresent()) {
            UserRegisterEntity user = optionalUser.get();
            // check password
            if (passwordEncoder.matches(password, user.getUserPassword())) {
                // generate JWT token
                String token = jwtUtil.generateToken(user.getUserEmail(), user.getRole());
                user.setToken(token);
                return repo.save(user);  // save updated token in DB
            }
        }
        return null; // login failed
    }

    // ---------------- Logout method ----------------
    @Override
    public void logoutUser(String email) {
        Optional<UserRegisterEntity> optionalUser = repo.findByUserEmail(email);
        optionalUser.ifPresent(user -> {
            user.setToken(null);
            repo.save(user);
        });
    }

    // ---------------- Admin setup ----------------
    @PostConstruct
    public void createAdminIfNotExist() {
        String adminEmail = "admin9977@gmail.com";
        if (!repo.existsByUserEmail(adminEmail)) {
            UserRegisterEntity admin = new UserRegisterEntity();
            admin.setUserName("Admin");
            admin.setUserEmail(adminEmail);
            admin.setUserPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole("ROLE_ADMIN");
            admin.setToken(null);
            repo.save(admin);
            System.out.println("Admin user created!");
        }
    }
}

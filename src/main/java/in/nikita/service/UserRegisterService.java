package in.nikita.service;

import java.util.List;

import in.nikita.entity.UserRegisterEntity;

public interface UserRegisterService {
    // User registration & login
    UserRegisterEntity registerUser(UserRegisterEntity register);
    boolean emailExists(String email);
    UserRegisterEntity addUser(String userName, String userEmail, String userPassword, String role);
    UserRegisterEntity loginUser(String email, String password);
    void logoutUser(String email);

    // Admin features
    List<UserRegisterEntity> getAllUsers();
	UserRegisterEntity toggleUserActive(Integer id);
	UserRegisterEntity getUserById(Integer id);
}

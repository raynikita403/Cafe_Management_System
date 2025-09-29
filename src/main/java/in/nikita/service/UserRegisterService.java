package in.nikita.service;

import in.nikita.entity.UserRegisterEntity;

public interface UserRegisterService {
	//for User Registration 
    UserRegisterEntity registerUser(UserRegisterEntity register);
    boolean emailExists(String email); 
    UserRegisterEntity addUser(String userName, String userEmail, String userPassword, String role);

   //for user Login and registration
    UserRegisterEntity loginUser(String email, String password);
    void logoutUser(String email);
}

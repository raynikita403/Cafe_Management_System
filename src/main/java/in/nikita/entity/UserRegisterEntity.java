package in.nikita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "userregister", uniqueConstraints = {@UniqueConstraint(columnNames = "userEmail")})
public class UserRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;    

    private String userName;
    private String userEmail;
    private String userPassword;
    private String role = "ROLE_USER";
    private String token;

    public UserRegisterEntity() {
        super();
    }

    public UserRegisterEntity(Integer userId, String userName, String userEmail, String userPassword, String role, String token) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.role = role != null ? role : "ROLE_USER";
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role != null ? role : "ROLE_USER";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserRegisterEntity [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail
                + ", userPassword=" + userPassword + ", role=" + role + ", token=" + token + "]";
    }
}

package com.example.cafeteria.web;

import com.example.cafeteria.domain.User;
import com.example.cafeteria.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    // ✅ Inyección por constructor, sin Lombok
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Login simple para probar (HU009)
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getUsername(), request.getPassword());

        LoginResponse resp = new LoginResponse();
        resp.setId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setFullName(user.getFullName());
        resp.setRole(user.getRole().name());

        return resp;
    }

    // Registro de nuevo usuario (HU009)
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse register(@RequestBody RegisterRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        
        if (request.getRole() != null && !request.getRole().isEmpty()) {
            try {
                newUser.setRole(com.example.cafeteria.domain.UserRole.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                newUser.setRole(com.example.cafeteria.domain.UserRole.EMPLOYEE);
            }
        }
        
        User savedUser = userService.register(newUser);

        LoginResponse resp = new LoginResponse();
        resp.setId(savedUser.getId());
        resp.setUsername(savedUser.getUsername());
        resp.setFullName(savedUser.getFullName());
        resp.setRole(savedUser.getRole().name());

        return resp;
    }

    // ===== Clases internas para la petición y respuesta =====

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String id;
        private String username;
        private String fullName;
        private String role;

        public LoginResponse() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String fullName;
        private String email;
        private String role;

        public RegisterRequest() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}

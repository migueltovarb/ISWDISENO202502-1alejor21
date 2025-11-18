package com.example.cafeteria.service;

import com.example.cafeteria.domain.User;
import com.example.cafeteria.domain.UserRole;
import com.example.cafeteria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Crear empleado o admin
    public User create(User user) {
        validar(user);
        user.setId(null);
        if (user.getRole() == null) {
            user.setRole(UserRole.EMPLOYEE);
        }
        user.setActive(true);
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
    }

    public User update(String id, User updated) {
        User existing = findById(id);
        validarParaUpdate(updated);

        if (StringUtils.hasText(updated.getUsername())) {
            existing.setUsername(updated.getUsername());
        }

        if (StringUtils.hasText(updated.getFullName())) {
            existing.setFullName(updated.getFullName());
        }

        if (StringUtils.hasText(updated.getEmail())) {
            existing.setEmail(updated.getEmail());
        }

        if (StringUtils.hasText(updated.getPassword())) {
            existing.setPassword(updated.getPassword());
        }

        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }

        existing.setActive(updated.isActive());

        return userRepository.save(existing);
    }

    public void delete(String id) {
        User existing = findById(id);
        existing.setActive(false);
        userRepository.save(existing);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña inválidos"));

        if (!user.isActive() || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Usuario o contraseña inválidos");
        }

        return user;
    }

    // Registro de nuevo usuario (HU009)
    public User register(User user) {
        validarParaRegistro(user);
        
        // Verificar que el username no exista
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        
        user.setId(null);
        if (user.getRole() == null) {
            user.setRole(UserRole.EMPLOYEE); // Por defecto empleado
        }
        user.setActive(true);
        
        return userRepository.save(user);
    }

    // ===== Validaciones =====

    private void validar(User u) {
        if (!StringUtils.hasText(u.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (!StringUtils.hasText(u.getPassword())) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
    }

    private void validarParaRegistro(User u) {
        if (!StringUtils.hasText(u.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (!StringUtils.hasText(u.getPassword())) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (!StringUtils.hasText(u.getFullName())) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if (!StringUtils.hasText(u.getEmail())) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
    }

    // Para update no obligo a mandar todos los campos
    private void validarParaUpdate(User u) {
        if (u == null) {
            throw new IllegalArgumentException("Datos de usuario inválidos");
        }
    }
}

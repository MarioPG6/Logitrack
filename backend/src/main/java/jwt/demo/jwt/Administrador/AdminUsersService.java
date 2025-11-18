package jwt.demo.jwt.Administrador;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jwt.demo.jwt.User.Role;
import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;
import jwt.demo.jwt.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Crear usuario nuevo desde DTO RegisterRequest
    public User createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setDirection(request.getDirection());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.CLIENTE);

        return userRepository.save(user);
    }

    // Editar usuario existente
    public User updateUser(Integer id, User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existing.setFirstname(updatedUser.getFirstname());
        existing.setLastname(updatedUser.getLastname());
        existing.setDirection(updatedUser.getDirection());
        existing.setPhone(updatedUser.getPhone());
        existing.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRole() != null) {
            existing.setRole(updatedUser.getRole());
        }

        return userRepository.save(existing);
    }

    // Eliminar usuario
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    // Listar todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Obtener todos los trabajadores
    public List<User> getAllWorkers() {
        return userRepository.findByRole(Role.TRABAJADOR);
    }

    // Obtener todos los clientes
    public List<User> getAllClients() {
        return userRepository.findByRole(Role.CLIENTE);
    }

}

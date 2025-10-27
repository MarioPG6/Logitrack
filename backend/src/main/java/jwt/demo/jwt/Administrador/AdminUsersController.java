package jwt.demo.jwt.Administrador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwt.demo.jwt.User.User;
import jwt.demo.jwt.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final AdminUsersService adminUsersService;

    // Solo los ADMINISTRADORES pueden acceder
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        User user = adminUsersService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        return adminUsersService.updateUser(id, user);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        adminUsersService.deleteUser(id);
        return "Usuario eliminado correctamente";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return adminUsersService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return adminUsersService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}

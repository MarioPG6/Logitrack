package jwt.demo.jwt.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jwt.demo.jwt.User.Role;
import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;
import jwt.demo.jwt.jwt.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }

        public AuthResponse register(RegisterRequest request) {
                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .direction(request.getDirection())
                                .phone(request.getPhone())
                                .role(request.getRole() != null ? request.getRole() : Role.CLIENTE)
                                .build();

                userRepository.save(user);

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }

        public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no encontrado o inválido");
                }

                String token = authHeader.substring(7);
                String email = jwtService.getUsernameFromToken(token);

                return userRepository.findByEmail(email)
                                .<ResponseEntity<?>>map(ResponseEntity::ok)
                                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
        }

}

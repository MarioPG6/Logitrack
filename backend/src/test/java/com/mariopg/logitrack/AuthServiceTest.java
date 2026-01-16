package com.mariopg.logitrack;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mariopg.logitrack.auth.AuthResponse;
import com.mariopg.logitrack.auth.AuthService;
import com.mariopg.logitrack.auth.LoginRequest;
import com.mariopg.logitrack.auth.RegisterRequest;
import com.mariopg.logitrack.security.JwtService;
import com.mariopg.logitrack.user.User;
import com.mariopg.logitrack.user.UserRepository;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_DeberiaRetornarTokenSiUsuarioExiste() {
        // Arrange
        LoginRequest request = new LoginRequest("cliente@gmail.com", "qwerty");
        User user = User.builder().email("cliente@gmail.com").password("qwerty").build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn("fake-jwt");

        // Act
        AuthResponse response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("fake-jwt", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_DeberiaLanzarExcepcionSiUsuarioNoExiste() {
        // Arrange
        LoginRequest request = new LoginRequest("notfound@email.com", "password");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class,
                () -> authService.login(request));
        assertEquals("Usuario no encontrado", thrown.getMessage());
    }

    @SuppressWarnings("null")
    @Test
    void register_DeberiaGuardarUsuarioYRetornarToken() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("new@email.com");
        request.setPassword("1234");
        request.setFirstname("Mario");
        request.setLastname("Palencia");
        request.setDirection("Calle 123");
        request.setPhone("123456");

        User user = User.builder()
                .email(request.getEmail())
                .password("encoded-pass")
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .direction(request.getDirection())
                .phone(request.getPhone())
                .build();

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-pass");
        when(jwtService.getToken(any(User.class))).thenReturn("fake-jwt");
        when(userRepository.save(any(User.class))).thenReturn(user);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("fake-jwt", response.getToken());
        verify(userRepository).save(any(User.class));
    }

   @Test
void login_DeberiaLanzarExcepcionSiPasswordIncorrecto() {
    // Arrange
    LoginRequest request = new LoginRequest("cliente@gmail.com", "wrong-pass");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new RuntimeException("credenciales incorrectas"));

    // Act & Assert
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> authService.login(request));
    assertEquals("credenciales incorrectas", thrown.getMessage());
}


    @Test
    void register_DeberiaLanzarExcepcionSiUsuarioYaExiste() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("cliente@gmail.com");
        request.setPassword("qwerty");

        User existingUser = User.builder().email(request.getEmail()).build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> authService.register(request));

        assertEquals("Usuario ya registrado", thrown.getMessage());
    }

}

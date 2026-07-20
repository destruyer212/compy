package pe.celucheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.celucheck.domain.*;
import pe.celucheck.dto.RegisterRequest;
import pe.celucheck.repository.AppUserRepository;

@Service @RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    @Override @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) {
        AppUser user = users.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe una cuenta con ese correo"));
        return User.withUsername(user.getEmail()).password(user.getPassword())
                .roles(user.getRole().name()).disabled(!user.isEnabled()).build();
    }

    @Transactional
    public AppUser register(RegisterRequest request) {
        if (users.existsByEmailIgnoreCase(request.getEmail())) throw new IllegalArgumentException("Ese correo ya está registrado");
        if (!request.getPassword().equals(request.getConfirmPassword())) throw new IllegalArgumentException("Las contraseñas no coinciden");
        AppUser user = new AppUser();
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return users.save(user);
    }

    public AppUser byEmail(String email) { return users.findByEmailIgnoreCase(email).orElseThrow(); }
}

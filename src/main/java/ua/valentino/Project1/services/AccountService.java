package ua.valentino.Project1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.valentino.Project1.dtos.account.RegisterDto;
import ua.valentino.Project1.entities.RoleEntity;
import ua.valentino.Project1.entities.UserEntity;
import ua.valentino.Project1.repositories.IRoleRepository;
import ua.valentino.Project1.repositories.IUserRepository;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity register(RegisterDto dto) {
        // Перевірка username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Користувач з таким username вже існує");
        }

        // Перевірка email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Користувач з таким email вже існує");
        }

        // Перевірка паролів
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Паролі не совпадают");
        }

        // Перевірка довжини пароля
        if (dto.getPassword().length() < 6) {
            throw new RuntimeException("Пароль має бути мінімум 6 символів");
        }

        // Створення користувача
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Додання ролі User (за замовчуванням)
        RoleEntity userRole = roleRepository.findByName("User")
                .orElseThrow(() -> new RuntimeException("Role User не знайдена. Виконайте сідування ролей."));
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }
}

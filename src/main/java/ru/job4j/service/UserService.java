package ru.job4j.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.job4j.dto.request.SignupRequestDto;
import ru.job4j.dto.response.RegisterDto;
import ru.job4j.model.Post;
import ru.job4j.model.Role;
import ru.job4j.model.RoleEnum;
import ru.job4j.model.User;
import ru.job4j.repository.FileRepository;
import ru.job4j.repository.RoleRepository;
import ru.job4j.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class UserService {
    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(int id) {
        return userRepository.deleteUserById(id) > 0L;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean update(User user) {
        return userRepository.update(user) > 0L;
    }

    public RegisterDto signUp(SignupRequestDto signUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByName(signUpRequest.getUsername()))
                || Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return new RegisterDto(HttpStatus.BAD_REQUEST, "Error: Username or Email is already taken!");
        }
        User person = User.builder()
                .name(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Supplier<RuntimeException> supplier = () -> new RuntimeException("Error: Role is not found.");

        if (strRoles == null) {
            roles.add(roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(supplier));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(supplier));
                    case "mod" -> roles.add(roleRepository.findByName(RoleEnum.ROLE_MODERATOR).orElseThrow(supplier));
                    default -> roles.add(roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(supplier));
                }
            });
        }
        person.setRoles(roles);
        userRepository.save(person);
        return new RegisterDto(HttpStatus.OK, "Person registered successfully!");
    }
}

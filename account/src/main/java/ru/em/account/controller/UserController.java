package ru.em.account.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.em.account.service.api.UserService;
import ru.em.entity.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить список активных пользователей",
            description = "Возвращает всех пользователей, у которых поле isActive равно true.")
    @GetMapping
    public ResponseEntity<List<User>> getAllIsActiveUsers() {
        return ResponseEntity.ok(userService.findIsActive(true));
    }

    @Operation(summary = "Получить список всех пользователей",
            description = "Возвращает всех пользователей из базы данных.")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Получить пользователя по идентификатору",
            description = "Возвращает пользователя по UUID. Если пользователь не найден, возвращается код 404.")
    @Parameter(name = "id", description = "Идентификатор пользователя (UUID)", required = true)
    @GetMapping("/uuid/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить пользователя по имени пользователя",
            description = "Возвращает пользователя с заданным username. Поиск не чувствителен к регистру.")
    @Parameter(name = "username", description = "Имя пользователя", required = true)
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsernameIgnoreCase(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

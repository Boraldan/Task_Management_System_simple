package ru.em.account.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.account.repository.UserRepository;
import ru.em.account.service.api.UserService;
import ru.em.entity.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService_v1 implements UserService {

    private final UserRepository userRepository;

    /**
     * Получает список всех пользователей из базы данных.
     *
     * @return список пользователей.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return Optional<User> с найденным пользователем или пустой Optional.
     */
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }


    /**
     * Получает пользователя по имени пользователя, игнорируя регистр.
     *
     * @param username имя пользователя.
     * @return Optional<User> с найденным пользователем или пустой Optional.
     */
    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }


    @Override
    public List<User> findIsActive(Boolean isActive) {
        return userRepository.findAllByIsActive(isActive);
    }

}
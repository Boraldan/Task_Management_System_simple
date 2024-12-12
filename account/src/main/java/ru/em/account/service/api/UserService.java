package ru.em.account.service.api;


import ru.em.entity.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(UUID id);
    Optional<User> findByUsernameIgnoreCase(String username);
    List<User> findIsActive(Boolean isActive);

}

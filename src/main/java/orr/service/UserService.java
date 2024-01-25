package orr.service;


import orr.dto.UserDto;
import orr.models.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<User> getAll();

    Optional<User> findById(Long id);

    User getById(Long id);

    User update(Long id, UserDto userDto);

    void delete(Long id);

    User add(User user);

    Optional<User> findUserByUsername(String username);
}

package orr.dao;

import orr.dto.UserDto;
import orr.models.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Collection<User> getAll();
    Optional<User> findById(Long id);
    User getById(Long id);
    User update(Long id, UserDto userDto);
    void delete(Long id);
    Long add(User user);
    Optional<User> findUserByUsername(String username);
}

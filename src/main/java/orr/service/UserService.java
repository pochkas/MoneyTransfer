package orr.service;


import com.google.inject.ImplementedBy;
import orr.dto.UserDto;
import orr.models.User;
import orr.service.Impl.MoneyTransferServiceImpl;
import orr.service.Impl.UserServiceImpl;

import java.util.Collection;
import java.util.Optional;
@ImplementedBy(UserServiceImpl.class)
public interface UserService {

    Collection<User> getAll();

    Optional<User> findById(Long id);

    User getById(Long id);

    User update(Long id, UserDto userDto);

    void delete(Long id);

    Long add(User user);

    Optional<User> findUserByUsername(String username);
}

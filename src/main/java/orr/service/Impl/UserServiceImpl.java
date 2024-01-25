package orr.service.Impl;

import com.google.inject.Inject;
import generated.tables.records.UserRecord;
import orr.dao.impl.UserDaoImpl;
import orr.dto.UserDto;
import orr.exception.UserNotFoundException;
import orr.models.User;
import orr.service.UserService;

import java.util.Collection;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDaoImpl userDao;

    @Inject
    public UserServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User update(Long id, UserDto userDto) {
        return userDao.update(id, userDto);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return findUserByUsername(username);
    }
}

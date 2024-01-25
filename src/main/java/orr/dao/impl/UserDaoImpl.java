package orr.dao.impl;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import orr.dao.UserDao;
import orr.dto.UserDto;
import orr.exception.UserNotFoundException;
import orr.models.User;

import java.util.Collection;
import java.util.Optional;

import static generated.tables.User.USER;

public class UserDaoImpl implements UserDao {
    private final DSLContext context;

    @Inject
    public UserDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public Collection<User> getAll() {
        return context.select().from(USER).fetchInto(User.class);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = context.select().from(USER).where(USER.ID.equal(id)).fetchOneInto(User.class);
        return Optional.ofNullable(user);
    }

    @Override
    public User getById(Long id) {
        return findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User update(Long id, UserDto userDto) {

        context.update(USER)
                .set(USER.FIRSTNAME, userDto.getFirstName())
                .set(USER.LASTNAME, userDto.getLastName())
                .set(USER.EMAIL, userDto.getEmail())
                .set(USER.USERNAME, userDto.getUsername()).where(USER.ID.eq(id)).execute();

        return getById(id);
    }

    @Override
    public void delete(Long id) {
        context.delete(USER).where(USER.ID.eq(id)).execute();
    }

    @Override
    public User add(User user) {
        context.insertInto(USER, USER.FIRSTNAME, USER.LASTNAME, USER.EMAIL, USER.USERNAME, USER.PASSWORD)
                .values(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getPassword()).execute();
        return user;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        User user = context.select().from(USER).where(USER.USERNAME.eq(username)).fetchOneInto(User.class);
        return Optional.ofNullable(user);
    }
}

package orr.service.impl;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import orr.dao.impl.UserDaoImpl;
import orr.dto.UserDto;
import orr.exception.UserNotFoundException;
import orr.models.User;
import orr.service.Impl.UserServiceImpl;
import orr.service.UserService;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest extends PostgreSQLContainerAbstract{
    private UserService service = new UserServiceImpl(new UserDaoImpl(dslContext(providedConfiguration())));

    @Test
    public void test1AllTest(){
        assertEquals(service.getAll().size(), 0);
    }

    @Test
    public void test2AddUserTest(){
        User user=new User(1L,"test", "test", "test@gmail.com", "test", "test");
        Long id = service.add(user);
        assertEquals(user, service.getById(id));
    }

    @Test
    public void test3GetByIdAndUpdateTest(){
        User user=new User(2L,"test2", "test2", "test2@gmail.com", "test2", "test2");
        Long id = service.add(user);
        assertEquals(user, service.getById(id));

        UserDto userDto = new UserDto("newTest", "newTest", "newTest", "newTest@gmail.com");
        User updatesUser = service.update(id, userDto);

        assertEquals(updatesUser, service.getById(id));
    }

    @Test
    public void test4delete(){
        Long id= 3L;

        Exception exception = assertThrows(UserNotFoundException.class, () ->
               service.getById(id));
        assertEquals("No user with id " + id + " found.", exception.getMessage());

        User user=new User(3L,"test2", "test2", "test2@gmail.com", "test2", "test2");
        Long userId = service.add(user);
        assertEquals(user, service.getById(userId));
        service.delete(userId);

        Exception exception2 = assertThrows(UserNotFoundException.class, () ->
                service.getById(userId));
        assertEquals("No user with id " + id + " found.", exception.getMessage());
  }
}

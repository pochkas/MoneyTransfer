package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import orr.dto.UserDto;
import orr.models.User;
import orr.service.UserService;
import spark.Spark;
import spark.servlet.SparkApplication;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UserControllerTest {

    static UserService service = EasyMock.createMock(UserService.class);

    public static class TestControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            new UserController(service);
        }
    }

    @BeforeClass
    public static void init() {
        Spark.port(4567);
        new TestControllerTestSparkApplication().init();
        Spark.awaitInitialization();
    }

    @Test
    public void getAllTest() throws Exception {
        reset(service);
        expect(service.getAll())
                .andReturn(Collections.EMPTY_LIST).once();
        replay(service);
        URL uri = new URL("http://localhost:4567/users");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("GET");
        assertEquals(new String(con.getInputStream().readAllBytes()), Collections.EMPTY_LIST.toString());
    }

    @Test
    public void getByIdTest() throws Exception {
        reset(service);

        User user1 = new User(1L, "null", null, null, null, null);
        expect(service.getById(anyLong())).andReturn(user1);
        replay(service);

        URL uri = new URL("http://localhost:4567/users/1");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("GET");
        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(new String(con.getInputStream().readAllBytes()), User.class);
        assertEquals(user, user1);
    }

    @Test
    public void addTest() throws Exception {
        reset(service);
        expect(service.add(anyObject())).andReturn(1L);
        replay(service);
        URL uri = new URL("http://localhost:4567/users");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("POST");
        assertEquals(new String(con.getInputStream().readAllBytes()), "1");
    }

    @Test
    public void updateTest() throws Exception {
        reset(service);
        Gson gson = new GsonBuilder().create();
        UserDto userDto = new UserDto("update", "update", null, null);
        User user = new User(1L, "update", "update", null, null, null);
        expect(service.update(eq(1L), eq(userDto))).andReturn(user);
        replay(service);

        URL uri = new URL("http://localhost:4567/users/1");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("PUT");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        String user2 = gson.toJson(userDto, UserDto.class);
        osw.write(user2);
        osw.flush();
        osw.close();
        os.close();  //don't forget to close the OutputStream

        User user1 = gson.fromJson(new String(con.getInputStream().readAllBytes()), User.class);
        assertEquals(user1, user);
    }
}

package orr.controller;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import orr.MoneyTransferApplication;
import orr.dao.impl.AccountDaoImpl;
import orr.dto.AccountDto;
import orr.dto.UserDto;
import orr.models.Account;
import orr.models.User;
import orr.service.AccountService;
import orr.service.Impl.AccountServiceImpl;

import orr.service.UserService;
import spark.Spark;
import spark.servlet.SparkApplication;


import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AccountControllerTest {

    static AccountService service = EasyMock.createMock(AccountService.class);

    public static class TestControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            new AccountController(service);
        }
    }

    @BeforeClass
    public static void init() {
        Spark.port(4567);
        new AccountControllerTest.TestControllerTestSparkApplication().init();
        Spark.awaitInitialization();
    }

    @Test
    public void getAllTest() throws Exception {
        reset(service);
        expect(service.getAll())
                .andReturn(Collections.EMPTY_LIST).once();
        replay(service);
        URL uri = new URL("http://localhost:4567/accounts");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("GET");
        assertEquals(new String(con.getInputStream().readAllBytes()), Collections.EMPTY_LIST.toString());
    }

    @Test
    public void getByIdTest() throws Exception {
        reset(service);
        Account account = new Account(1L, null, 2000.0, 1L);
        expect(service.getById(anyLong())).andReturn(account);
        replay(service);

        URL uri = new URL("http://localhost:4567/accounts/1");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("GET");
        Gson gson = new GsonBuilder().create();
        Account account1 = gson.fromJson(new String(con.getInputStream().readAllBytes()), Account.class);
        assertEquals(account1, account);
    }

    @Test
    public void addTest() throws Exception {
        reset(service);
        AccountDto accountDto = new AccountDto("a", 2000.0);
        expect(service.add(eq(1L), eq(accountDto))).andReturn(1L);
        replay(service);
        URL uri = new URL("http://localhost:4567/accounts/1");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        Gson gson = new GsonBuilder().create();
        String accountDtoStr = gson.toJson(accountDto, AccountDto.class);
        osw.write(accountDtoStr);
        osw.flush();
        osw.close();
        os.close();

        assertEquals(new String(con.getInputStream().readAllBytes()), "1");
    }

    @Test
    public void updateTest() throws Exception {
        reset(service);
        Gson gson = new GsonBuilder().create();
        AccountDto accountDto = new AccountDto("UPDATED", 2000.0);
        expect(service.add(eq(1L), eq(accountDto))).andReturn(1L);
        Account account = new Account(1L, null, 2000.0, 1L);
        expect(service.update(eq(1L), eq(accountDto))).andReturn(account);
        replay(service);

        URL uri = new URL("http://localhost:4567/accounts/1");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("PUT");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        String str = gson.toJson(accountDto, AccountDto.class);
        osw.write(str);
        osw.flush();
        osw.close();
        os.close();

        Account acc = gson.fromJson(new String(con.getInputStream().readAllBytes()), Account.class);
        assertEquals(acc, account);
    }
}

package orr.controller;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;
import orr.MoneyTransferApplication;
import orr.dao.impl.AccountDaoImpl;
import orr.service.AccountService;
import orr.service.Impl.AccountServiceImpl;

import spark.servlet.SparkApplication;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AccountControllerTest {

    public static class WebAppTestSparkApp implements SparkApplication {
        @Override
        public void init() {
            AccountService accountService = EasyMock.createMock(AccountService.class);
            new AccountController(accountService);
        }

        @ClassRule
        public static SparkServer<WebAppTestSparkApp> testServer = new SparkServer<>(WebAppTestSparkApp.class, 4567);

        @Test
        public void serverRespondsSuccessfully() throws HttpClientException {
            GetMethod request = testServer.get("/", false);
            HttpResponse httpResponse = testServer.execute(request);
            assertEquals(200, httpResponse.code());
        }
    }
}

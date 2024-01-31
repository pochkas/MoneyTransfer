package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import orr.models.MoneyTransfer;
import orr.service.MoneyTransferService;
import spark.Spark;
import spark.servlet.SparkApplication;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class MoneyTransferControllerTest {

    static MoneyTransferService service = EasyMock.createMock(MoneyTransferService.class);

    public static class TestControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            new MoneyTransferController(service);
        }
    }

    @BeforeClass
    public static void init() {
        Spark.port(4567);
        new MoneyTransferControllerTest.TestControllerTestSparkApplication().init();
        Spark.awaitInitialization();
    }

    @Test
    public void getAllTest() throws Exception {
        reset(service);
        expect(service.getAll())
                .andReturn(Collections.EMPTY_LIST).once();
        replay(service);
        URL uri = new URL("http://localhost:4567/moneyTransfers");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("GET");
        assertEquals(new String(con.getInputStream().readAllBytes()), Collections.EMPTY_LIST.toString());
    }

    @Test
    public void getByIdTest() throws Exception {
        reset(service);

        MoneyTransfer moneyTransfer = new MoneyTransfer(1L, 2L,10);
        expect(service.getById(anyLong())).andReturn(moneyTransfer);
        replay(service);

        URL uri = new URL("http://localhost:4567/moneyTransfers/1");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("GET");
        Gson gson = new GsonBuilder().create();
        MoneyTransfer moneyTransfer1 = gson.fromJson(new String(con.getInputStream().readAllBytes()), MoneyTransfer.class);
        assertEquals(moneyTransfer, moneyTransfer1);
    }

    @Test
    public void performTransactionTest() throws Exception {
        reset(service);
        service.performTransaction(eq(4343322889283454872L), eq(5343322889283454872L),  eq(1000.0));

        EasyMock.expectLastCall().anyTimes();

        replay(service);
        URL uri = new URL("http://localhost:4567/moneyTransfer?fromAccountNumber=4343322889283454872&toAccountNumber=5343322889283454872&amount=1000");
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestMethod("POST");
        assertEquals("\"ok\"", new String(con.getInputStream().readAllBytes()));
    }
}

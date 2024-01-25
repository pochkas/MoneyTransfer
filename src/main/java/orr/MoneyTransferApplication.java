package orr;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.qmetric.spark.authentication.AuthenticationDetails;
import com.qmetric.spark.authentication.BasicAuthenticationFilter;
import orr.controller.AccountController;
import orr.controller.UserController;
import orr.module.GuiceModule;

import static spark.Spark.*;

public class MoneyTransferApplication {

    private final UserController userController;
    private final AccountController accountController;

    @Inject
    public MoneyTransferApplication(UserController userController, AccountController accountController) {
        this.userController = userController;
        this.accountController = accountController;
    }

//    void run(final int port) {
//        //port(port);
//        before(new BasicAuthenticationFilter("/path/*", new AuthenticationDetails("user", "password")));
//        before("/*", (request, response) -> String.format("%s: %s", request.requestMethod(), request.uri()));
//
//        get("/", (request, response) -> "Hello");
//
//        after("/*", (request, response) -> response.body());
//    }

    public static void main(String[] args) {
        Guice.createInjector(new GuiceModule()).getInstance(MoneyTransferApplication.class);
    }
}

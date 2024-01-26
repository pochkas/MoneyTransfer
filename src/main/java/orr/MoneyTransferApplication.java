package orr;

import com.google.inject.Guice;
import com.google.inject.Inject;
import orr.controller.AccountController;
import orr.controller.MoneyTransferController;
import orr.controller.UserController;
import orr.module.GuiceModule;

public class MoneyTransferApplication {

    private final UserController userController;
    private final AccountController accountController;
    private final MoneyTransferController moneyTransferController;

    @Inject
    public MoneyTransferApplication(UserController userController, AccountController accountController, MoneyTransferController moneyTransferController) {
        this.userController = userController;
        this.accountController = accountController;
        this.moneyTransferController = moneyTransferController;
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

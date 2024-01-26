package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import orr.dto.AccountDto;
import orr.errors.ResponseError;
import orr.models.Account;
import orr.service.Impl.AccountServiceImpl;

import java.util.Optional;

import static orr.utils.JsonUtil.json;
import static orr.utils.JsonUtil.toJson;
import static spark.Spark.*;

public class AccountController {
    @Inject
    public AccountController(final AccountServiceImpl accountService) {

        get("/accounts", (req, res) -> accountService.getAll(), json());

        get("/accounts/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Optional<Account> account = accountService.findById(id);
            if (!account.isPresent()) {
                res.status(400);
                return new ResponseError("No account with id '%s' found", String.valueOf(id));
            }

            res.status(200);
            return account.get();
        }, json());

        get("/account", (req, res) -> {
            Long id = Long.valueOf(req.queryParams("accountNumber"));
            Optional<Account> account = accountService.findByAccountNumber(id);
            if (!account.isPresent()) {
                res.status(400);
                return new ResponseError("No account with id '%s' found", String.valueOf(id));
            }
            res.status(200);
            return account.get();
        }, json());

        post("/accounts/:userId", (req, res) -> {
            Long userId = Long.valueOf(req.params(":userId"));

            String request = "" + req.body();
            Gson gson = new GsonBuilder().create();
            Account account = gson.fromJson(request, Account.class);

            Account newAccount = accountService.add(userId, account);
            if (account != null) {
                return newAccount;
            }
            return new ResponseError("No user created");
        }, json());

        put("/accounts/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Optional<Account> account = accountService.findById(id);
            if (!account.isPresent()) {
                res.status(400);
                return new ResponseError("No account with id '%s' found", String.valueOf(id));
            }
            String request = "" + req.body();
            Gson gson = new GsonBuilder().create();
            AccountDto accountDto = gson.fromJson(request, AccountDto.class);
            res.status(200);
            return accountService.update(id, accountDto);
        }, json());

        delete("/accounts/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Optional<Account> account = accountService.findById(id);
            if (!account.isPresent()) {
                res.status(400);
                return new ResponseError("No account with id '%s' found", String.valueOf(id));
            }
            accountService.delete(id);
            res.status(200);
            return "Account was deleted.";
        }, json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}

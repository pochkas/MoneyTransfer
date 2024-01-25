package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import orr.dto.AccountDto;
import orr.errors.ResponseError;
import orr.exception.AccountNotFoundException;
import orr.models.Account;
import orr.service.Impl.AccountServiceImpl;

import static orr.utils.JsonUtil.json;
import static orr.utils.JsonUtil.toJson;
import static spark.Spark.*;

public class AccountController {
    @Inject
    public AccountController(final AccountServiceImpl accountService) {

        get("/accounts", (req, res) -> accountService.getAll(), json());

        get("/accounts/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Account account = accountService.getById(id);
            if (account != null) {
                return account;
            }
            res.status(400);
            return new ResponseError("No account with id '%s' found", String.valueOf(id));
        }, json());

        put("/accounts/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            String request = "" + req.body();
            Gson gson = new GsonBuilder().create();
            AccountDto accountDto = gson.fromJson(request, AccountDto.class);
            Account account = accountService.update(id, accountDto);
            if (account != null) {
                return account;
            }
            return new ResponseError("No account with id '%s' found", String.valueOf(id));
        }, json());

        delete("/accounts/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            try {
                accountService.delete(id);
                res.status(400);
            } catch (Exception e){
                throw new AccountNotFoundException();
            }
            return new ResponseError("No account with id '%s' found", String.valueOf(id));
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

package orr.controller;

import com.google.inject.Inject;
import orr.errors.ResponseError;
import orr.exception.MoneyTransferException;
import orr.exception.UserFacingException;
import orr.models.MoneyTransfer;
import orr.service.Impl.MoneyTransferServiceImpl;
import orr.service.MoneyTransferService;

import java.util.Optional;

import static orr.utils.JsonUtil.json;
import static orr.utils.JsonUtil.toJson;
import static spark.Spark.*;
import static spark.Spark.exception;

public class MoneyTransferController {
    @Inject
    public MoneyTransferController(final MoneyTransferServiceImpl moneyTransferService) {

        post("/moneyTransfer", (req, res) -> {
            Long fromAccountNumber = Long.valueOf(req.queryParams("fromAccountNumber"));
            Long toAccountNumber = Long.valueOf(req.queryParams("toAccountNumber"));
            double amount = Double.parseDouble(req.queryParams("amount"));
            res.status(200);

            moneyTransferService.performTransaction(fromAccountNumber, toAccountNumber, amount);

            return "-"+amount;
        }, json());

        get("/moneyTransfers", (req, res) -> moneyTransferService.getAll(), json());

        get("/moneyTransfers/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            return moneyTransferService.getById(id);
        }, json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(UserFacingException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}

package orr.controller;

import com.google.inject.Inject;
import orr.errors.ResponseError;
import orr.models.MoneyTransfer;
import orr.service.Impl.MoneyTransferServiceImpl;

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

            return amount;
        }, json());

        get("/moneyTransfers", (req, res) -> moneyTransferService.getAll(), json());

        get("/moneyTransfer/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Optional<MoneyTransfer> moneyTransfer = moneyTransferService.findById(id);
            if (!moneyTransfer.isPresent()) {
                res.status(400);
                return new ResponseError("No moneyTransfer with id '%s' found", String.valueOf(id));
            }
            res.status(200);
            return moneyTransfer.get();
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

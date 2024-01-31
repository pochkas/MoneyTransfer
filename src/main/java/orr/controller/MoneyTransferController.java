package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import orr.dto.AccountDto;
import orr.dto.MoneyTransferDto;
import orr.errors.ResponseError;
import orr.exception.UserFacingException;
import orr.service.MoneyTransferService;

import static orr.utils.JsonUtil.json;
import static orr.utils.JsonUtil.toJson;
import static spark.Spark.*;
import static spark.Spark.exception;

public class MoneyTransferController {
    @Inject
    public MoneyTransferController(final MoneyTransferService moneyTransferService) {

        post("/moneyTransfer", (req, res) -> {
            String request = req.body();
            Gson gson = new GsonBuilder().create();
            MoneyTransferDto moneyTransferDto = gson.fromJson(request, MoneyTransferDto.class);
            moneyTransferService.performTransaction(moneyTransferDto);
            return "ok";
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

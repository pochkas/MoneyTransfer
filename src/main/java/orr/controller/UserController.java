package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import orr.dto.UserDto;
import orr.errors.ResponseError;
import orr.models.User;
import orr.service.UserService;

import static orr.utils.JsonUtil.json;
import static orr.utils.JsonUtil.toJson;
import static spark.Spark.*;

public class UserController {
    @Inject
    public UserController(final UserService userService) {

        get("/users", (req, res) -> userService.getAll(), json());

        get("/users/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            return userService.getById(id);
        }, json());

        post("/users", (req, res) -> {
            String request = "" + req.body();
            Gson gson = new GsonBuilder().create();
            User user = gson.fromJson(request, User.class);
            return userService.add(user);
        }, json());

        put("/users/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            String request = req.body();
            Gson gson = new GsonBuilder().create();
            UserDto userDto = gson.fromJson(request, UserDto.class);
            return userService.update(id, userDto);
        }, json());

        get("/users/:username", (req, res) -> {
            String username = req.params(":username");
            return userService.findUserByUsername(username).get();
        }, json());

        delete("/users/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            userService.delete(id);
            return "User was deleted.";
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

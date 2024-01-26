package orr.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import orr.dto.UserDto;
import orr.errors.ResponseError;
import orr.exception.UserNotFoundException;
import orr.models.User;
import orr.service.Impl.UserServiceImpl;

import java.util.Optional;

import static orr.utils.JsonUtil.json;
import static orr.utils.JsonUtil.toJson;
import static spark.Spark.*;

public class UserController {
    @Inject
    public UserController(final UserServiceImpl userService) {

        get("/users", (req, res) -> userService.getAll(), json());

        get("/users/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                return user.get();
            }
            res.status(400);
            return new ResponseError("No user with id '%s' found", String.valueOf(id));
        }, json());

        post("/users", (req, res) -> {
            String request = "" + req.body();
            Gson gson = new GsonBuilder().create();
            User user = gson.fromJson(request, User.class);
            User newUser = userService.add(user);
            if (user != null) {
                return newUser;
            }
            return new ResponseError("No user created");
        }, json());

        put("/users/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                res.status(400);
                return new ResponseError("No user with id '%s' found", String.valueOf(id));
            }
            String request = "" + req.body();
            Gson gson = new GsonBuilder().create();
            UserDto userDto = gson.fromJson(request, UserDto.class);
            return userService.update(id, userDto);
        }, json());

        get("/users/:username", (req, res) -> {
            String username = req.params(":username");
            User user = userService.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
            if (user != null) {
                return user;
            }
            res.status(400);
            return new ResponseError("No user with username '%s' found", username);
        }, json());

        delete("/users/:id", (req, res) -> {
            Long id = Long.valueOf(req.params(":id"));Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                return new ResponseError("No user with id '%s' found", String.valueOf(id));
            }
            userService.delete(id);
            res.status(200);
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

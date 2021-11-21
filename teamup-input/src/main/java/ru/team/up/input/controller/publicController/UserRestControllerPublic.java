package ru.team.up.input.controller.publicController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.User;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.util.List;
import java.util.Optional;

/**
 * REST-контроллер для пользователей
 *
 * @author Pavel Kondrashov
 */

@Slf4j
@RestController
@RequestMapping("api/public/account")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestControllerPublic {
    private final UserServiceRest userServiceRest;

    /**
     * Метод для поиска поьзователя по id
     *
     * @param userId id пользователя
     * @return Ответ поиска и статус
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        log.debug("Запрос на поиск пользователя с id = {}", userId);
        Optional<User> userOptional = Optional.ofNullable(userServiceRest.getUserById(userId));

        return userOptional
                .map(user -> {
                    log.debug("Пользователь с id = {} найден", userId);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.error("Пользователь с id = {} не найден", userId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    /**
     * Метод поиска пользователя по почте
     *
     * @param userEmail почта пользователя
     * @return Ответ поиска и статус проверки
     */
    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String userEmail) {
        log.debug("Запрос на поиск пользователя с почтой: {}", userEmail);
        Optional<User> userOptional = Optional.ofNullable(userServiceRest.getUserByEmail(userEmail));

        log.debug("Пользователь с почтой {} найден", userEmail);
        return userOptional
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Метод поиска всех пользователей
     *
     * @return Ответ поиска и статус проверки
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> getUsersList() {
        log.debug("Получен запрос на список всех пользоватей");
        List<User> users = userServiceRest.getAllUsers();

        if (users.isEmpty()) {
            log.error("Список пользователей пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список пользователей получен");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Метод обновления пользователя
     *
     * @param user   Данные пользователя для изменения
     * @param userId идентификатор пользователя
     * @return Ответ обновления и статус проверки
     */
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody UserRequest user, @PathVariable("id") Long userId) {
        log.debug("Получен запрос на обновление пользователя");
        User existUser = userServiceRest.updateUser(userId, user);

        if (existUser == null) {
            log.error("Пользователь не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.debug("Пользователь обновлен");
        return new ResponseEntity<>(existUser, HttpStatus.OK);
    }

    /**
     * Метод для удаления пользователя
     *
     * @param userId идентификатор пользователя
     * @return Ответ удаления и статус проверки
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long userId) {
        log.debug("Получен запрос на удаления пользователя с id = {}", userId);
        User user = userServiceRest.getUserById(userId);

        if (user == null) {
            log.error("Пользователь с id = {} не найден", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userServiceRest.deleteUserById(userId);

        log.debug("Пользователь с id = {} успешно удален", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

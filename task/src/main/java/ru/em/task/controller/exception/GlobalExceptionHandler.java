package ru.em.task.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения ResourceNotFoundException.
     * Возвращает сообщение об ошибке с кодом состояния 404 (NOT FOUND).
     *
     * @param ex Исключение ResourceNotFoundException.
     * @return Ответ с текстом ошибки и статусом 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Обрабатывает исключения MethodArgumentNotValidException, возникающие при валидации параметров запроса.
     * Возвращает карту с ошибками, где ключ — это имя поля, а значение — сообщение об ошибке.
     *
     * @param ex Исключение MethodArgumentNotValidException.
     * @return Ответ с картой ошибок и статусом 400 (BAD REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Обрабатывает исключения BindException, возникающие при ошибках биндинга параметров.
     * Возвращает карту с ошибками, где ключ — это имя поля, а значение — сообщение об ошибке.
     *
     * @param ex Исключение BindException.
     * @return Ответ с картой ошибок и статусом 400 (BAD REQUEST).
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Обрабатывает исключения MissingServletRequestParameterException, возникающие при отсутствии обязательных параметров запроса.
     * Возвращает карту с информацией о пропущенном параметре.
     *
     * @param ex Исключение MissingServletRequestParameterException.
     * @return Ответ с информацией о пропущенном параметре и статусом 400 (BAD REQUEST).
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParam(MissingServletRequestParameterException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("parameter", ex.getParameterName());
        errors.put("message", "Parameter '" + ex.getParameterName() + "' is required.");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Обрабатывает исключения IllegalArgumentException, возникающие при передаче некорректных аргументов.
     * Возвращает карту с сообщением об ошибке.
     *
     * @param ex Исключение IllegalArgumentException.
     * @return Ответ с информацией об ошибке и статусом 400 (BAD REQUEST).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Invalid argument");
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Обрабатывает исключения MethodArgumentTypeMismatchException, возникающие при несоответствии типов параметров.
     * Возвращает карту с сообщением об ошибке.
     *
     * @param ex Исключение MethodArgumentTypeMismatchException.
     * @return Ответ с информацией о параметре и ожидаемом типе и статусом 400 (BAD REQUEST).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("parameter", ex.getName());
        errors.put("message", "Invalid value for parameter '" + ex.getName() + "'. Expected type: " + ex.getRequiredType().getSimpleName());
        return ResponseEntity.badRequest().body(errors);
    }
}

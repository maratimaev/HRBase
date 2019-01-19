package ru.bellintegrator.hrbase.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bellintegrator.hrbase.view.result.Error;

import java.util.Arrays;

/**
 * Класс обработки ошибок
 */
@RestControllerAdvice
public class ControllersExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllersExceptionHandler.class.getName());
    /** Обработка исключения при поиске объекта по параметрам
     * @param ex исключение
     * @return Error
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CantFindByParam.class)
    public Error handlerCantFindByParam(CantFindByParam ex) {
        String message = String.format("There is no finding by parameter, %s", ex.getParam());
        this.log(message, ex.getEx());
        return new Error(message);
    }

    /** Обработка исключения при сохранении объекта в БД
     * @param ex исключение
     * @return Error
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CantSaveNewObject.class)
    public Error handlerCantSaveNewObject(CantSaveNewObject ex) {
        String message = String.format("There is a problem while saving new %s to DB", ex.getObj());
        this.log(message, ex.getEx());
        return new Error(message);
    }

    /** Обработка исключения при обновлении данных объекта в БД
     * @param ex исключение
     * @return Error
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CantUpdateObject.class)
    public Error handlerCantUpdateOffice(CantUpdateObject ex) {
        String message = String.format("There is a problem while updating %s to DB", ex.getObj());
        this.log(message, ex.getEx());
        return new Error(message);
    }

    /** Обработка исключения при запросе list и валидации id организации из PathVariable и RequestBody
     * @param ex исключение
     * @return Error
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrganizationsIdMustBeEquals.class)
    public Error handlerOrganizationsIdMustBeEquals(OrganizationsIdMustBeEquals ex) {
        String message = String.format("Organization id must be the same url_id=%s, json_id=%s", ex.getUrlId(), ex.getJsonId());
        this.log(message, null);
        return new Error(message);
    }

    /** Обработка исключения при конвертировании строки в дату
     * @param ex исключение
     * @return Error
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(WrongDateFormat.class)
    public Error handlerWrongDateFormat(WrongDateFormat ex) {
        String message = String.format("Date must be like 2001-01-30, get date=%s", ex.getDate());
        this.log(message, ex.getEx());
        return new Error(message);
    }

    /** Обработка прочих исключений
     * @param ex исключение
     * @return Error
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handlerException(Exception ex) {
        return new Error(String.format("There is an Exception Error: %s", ex.getMessage()));
    }

    /** Обработка исключений при проверке входящих данных json
     * @param ex исключение
     * @param headers заголовок
     * @param status статус
     * @param request запрос
     * @return ResponseEntity с текстом ошибки
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(String.format("Throw exception binding : \n %s", ex.getBindingResult().toString()));
        FieldError bindingError = ex.getBindingResult().getFieldErrors().get(0);
        Error error = new Error(String.format("Field (%s) can't be: %s (%s)", bindingError.getField(), bindingError.getRejectedValue(), bindingError.getDefaultMessage()));
        return super.handleExceptionInternal(ex, error, headers, HttpStatus.NOT_ACCEPTABLE, request);
    }

    /** Обработка исключений парсинга json
     @param ex исключение
      * @param headers заголовок
     * @param status статус
     * @param request запрос
     * @return ResponseEntity с текстом ошибки
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error("Error parsing json");
        return super.handleExceptionInternal(ex, error, headers, HttpStatus.NOT_ACCEPTABLE, request);
    }

    private void log(String message, Exception ex) {
        if (ex != null) {
            LOGGER.debug(Arrays.toString(ex.getStackTrace()));
        }
        LOGGER.error(String.format("%s : parent exception - %s", message, ex));
    }
}

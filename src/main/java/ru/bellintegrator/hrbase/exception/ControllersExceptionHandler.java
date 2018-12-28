package ru.bellintegrator.hrbase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bellintegrator.hrbase.view.result.Error;

/**
 * Класс обработки ошибок с объектами organization
 */
@ControllerAdvice
public class ControllersExceptionHandler extends ResponseEntityExceptionHandler {
    /** Обработка исключения при поиске объекта по параметрам
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantFindByParam.class)
    public ResponseEntity<Error> handlerCantFindByParam(CantFindByParam ex) {
        return new ResponseEntity<>(new Error(String.format("There is no finding by parameter, %s",
                ex.getParam())), HttpStatus.NOT_FOUND);
    }

    /** Обработка исключения при сохранении объекта в БД
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantSaveNewObject.class)
    public ResponseEntity<Error> handlerCantSaveNewObject(CantSaveNewObject ex) {
        return new ResponseEntity<>(new Error(String.format("There is a problem while saving new %s to DB",
                ex.getObj())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Обработка исключения при обновлении данных объекта в БД
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantUpdateObject.class)
    public ResponseEntity<Error> handlerCantUpdateOffice(CantUpdateObject ex) {
        return new ResponseEntity<>(new Error(String.format("There is a problem while updating %s to DB",
                ex.getObj())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Обработка исключения при запросе list и валидации id организации из PathVariable и RequestBody
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(OrganizationsIdMustBeEquals.class)
    public ResponseEntity<Error> handlerOrganizationsIdMustBeEquals(OrganizationsIdMustBeEquals ex) {
        return new ResponseEntity<>(new Error(String.format("Organization id must be the same url_id=%s, json_id=%s",
                ex.getUrlId(), ex.getJsonId())), HttpStatus.NOT_FOUND);
    }

    /** Обработка исключения при конвертировании строки в дату
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(WrongDateFormat.class)
    public ResponseEntity<Error> handlerWrongDateFormat(WrongDateFormat ex) {
        return new ResponseEntity<>(new Error(String.format("Date must be like 2001-01-30, get date=%s",
                ex.getDate())), HttpStatus.NOT_FOUND);
    }

    /** Обработка исключения при конвертировании строки в дату
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(WrongInputField.class)
    public ResponseEntity<Error> handlerWrongInputField(WrongInputField ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), ex.getHttpStatus());
    }


    /** Обработка прочих исключений
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handlerException(Exception ex) {
        return new ResponseEntity<>(new Error(String.format("There is an Eception Error: %s", ex.getMessage())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

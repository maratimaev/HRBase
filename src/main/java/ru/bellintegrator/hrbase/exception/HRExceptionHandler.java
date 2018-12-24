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
public class HRExceptionHandler extends ResponseEntityExceptionHandler {
    /** Обработка исключения при поиске организации по Id
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantFindById.class)
    public ResponseEntity<Error> handlerCantFindById(CantFindById ex) {
        return new ResponseEntity<>(
                new Error(String.format("There is error finding by id, %s", ex.getId())),
                HttpStatus.NOT_FOUND);
    }

    /** Обработка исключения при поиске по имени, инн, признаку активности
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantFindByNameInnActive.class)
    public ResponseEntity<Error> handlerCantFindByNameInnActive(CantFindByNameInnActive ex) {
        return new ResponseEntity<>(
                new Error(String.format("There is no organization by such name = %s, inn = %s, isActive = %s",
                        ex.getName(), ex.getInn(), ex.getIsActive())),
                HttpStatus.NOT_FOUND);
    }

    /** Обработка исключения при сохранении оранизации в БД
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantSaveNewOrganization.class)
    public ResponseEntity<Error> handlerCantSaveNewOrganization() {
        return new ResponseEntity<>(new Error("There is a problem while saving new organization to DB"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Обработка исключения при обновлении данных организации в БД
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantUpdateOrganization.class)
    public ResponseEntity<Error> handlerCantUpdateOrganization() {
        return new ResponseEntity<>(new Error("There is a problem while updating organization to DB"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Обработка исключения при сохранении офиса в БД
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantSaveNewOffice.class)
    public ResponseEntity<Error> handlerCantSaveNewOffice() {
        return new ResponseEntity<>(new Error("There is a problem while saving new office to DB"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Обработка исключения при обновлении данных офиса в БД
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantUpdateOffice.class)
    public ResponseEntity<Error> handlerCantUpdateOffice() {
        return new ResponseEntity<>(new Error("There is a problem while updating office to DB"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** Обработка исключения при поиске офисов по имени, телефону, признаку активности, id организации
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantFindByNamePhoneActive.class)
    public ResponseEntity<Error> handlerCantFindByNamePhoneActive(CantFindByNamePhoneActive ex) {
        return new ResponseEntity<>(
                new Error(String.format("There is no offices by such name = %s, inn = %s, isActive = %s and organization id=%s",
                        ex.getName(), ex.getPhone(), ex.getIsActive(), ex.getOrgId())),
                HttpStatus.NOT_FOUND);
    }

    /** Обработка исключения при запросе list и валидации id организации из PathVariable и RequestBody
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(OrganizationsIdMustBeEquals.class)
    public ResponseEntity<Error> handlerOrganizationsIdMustBeEquals(OrganizationsIdMustBeEquals ex) {
        return new ResponseEntity<>(
                new Error(String.format("Organization id must be the same url_id=%s, json_id=%s",
                        ex.getUrlId(), ex.getJsonId())),
                HttpStatus.NOT_FOUND);
    }

}

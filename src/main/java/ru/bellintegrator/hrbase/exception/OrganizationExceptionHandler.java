package ru.bellintegrator.hrbase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bellintegrator.hrbase.view.status.Error;

/**
 * Класс обработки ошибок с объектами Organization
 */
@ControllerAdvice
public class OrganizationExceptionHandler extends ResponseEntityExceptionHandler {
    /** Обработка исключения при поиске организации по Id
     * @param ex исключение
     * @return Error, HttpStatus
     */
    @ExceptionHandler(CantFindById.class)
    public ResponseEntity<Error> handlerCantFindById(CantFindById ex) {
        return new ResponseEntity<>(
                new Error(String.format("There is no organization by such id = %s", ex.getId())),
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
}

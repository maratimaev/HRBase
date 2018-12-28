package ru.bellintegrator.hrbase.service;

/**
 * Сервис для работы с типом документа
 */
public interface GenericGetByNameService<T> {

    /** Возвращает объект по name из БД
     * @param name объекта
     * @return entity объекта
     */
    T getByName(String name);
}

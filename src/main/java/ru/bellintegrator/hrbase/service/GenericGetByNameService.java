package ru.bellintegrator.hrbase.service;

/**
 * Сервисы для работы с типом документа и гражданством
 */
public interface GenericGetByNameService<T> {

    /** Возвращает объект по name из БД
     * @param name объекта
     * @return entity объекта
     */
    T getByName(String name);
}

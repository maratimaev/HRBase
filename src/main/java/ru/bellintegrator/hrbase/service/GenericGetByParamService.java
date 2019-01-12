package ru.bellintegrator.hrbase.service;

/**
 * Сервис для работы с гражданством
 */
public interface GenericGetByParamService<T> {

    /** Возвращает объект по code из БД
     * @param code объекта
     * @return entity объекта
     */
    T getByCode(String code);
}

package ru.bellintegrator.hrbase.service;

/**
 * Сервисы для работы с типом документа и гражданством
 */
public interface GenericGetByParamService<T> {

    /** Возвращает объект по code из БД
     * @param code объекта
     * @return entity объекта
     */
    T getByCode(String code);
}

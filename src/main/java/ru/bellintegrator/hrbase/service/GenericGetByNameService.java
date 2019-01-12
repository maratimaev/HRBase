package ru.bellintegrator.hrbase.service;

/**
 * Сервис для работы с типом документа, документом и гражданством
 */
public interface GenericGetByNameService<T> extends GenericGetByParamService<T> {

    /** Возвращает объект по name из БД
     * @param name объекта
     * @return entity объекта
     */
    T getByName(String name);

    /** Возвращает объект по code из БД
     * @param code объекта
     * @return entity объекта
     */
    T getByCode(String code);
}

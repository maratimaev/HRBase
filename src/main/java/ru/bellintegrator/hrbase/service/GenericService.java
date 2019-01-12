package ru.bellintegrator.hrbase.service;

import java.util.List;

/**
 * Сервисы
 */
public interface GenericService<T, E> {
    /** Поиск объекта по id
     * @param id объекта
     * @return обертка над view
     */
    T find(String id);

    /** Поиск объектов по параметрам
     * @param t входящий объект json c constraints
     * @return обертка над view
     */
    List<T> list(T t);

    /** Сохранение нового объекта
     * @param t входящий объект json c constraints
     */
    void save(T t);

    /** Изменение данных объекта
     * @param t объект json c constraints
     */
    void update(T t);

    /** Возвращает entity объект по id
     * @param sid объекта
     * @return entity объекта
     */
    E getById(String sid);
}

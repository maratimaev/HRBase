package ru.bellintegrator.hrbase.service;

import ru.bellintegrator.hrbase.view.result.Wrapper;

/**
 * Сервисы
 */
public interface GenericService<T, E> {
    /** Поиск в БД по id
     * @param id объекта
     * @return обертка над view
     */
    Wrapper<T> find(String id);

    /** Поиск в БД по параметрам
     * @param t входящий объект json c constraints
     * @return обертка над view
     */
    Wrapper<T> list(T t);

    /** Сохранение нового объекта в БД
     * @param t входящий объект json c constraints
     */
    void save(T t);

    /** Изменение данных
     * @param t объект json c constraints
     */
    void update(T t);

    /** Возвращает entity объект по id из БД
     * @param sid объекта
     * @return entity объекта
     */
    E getById(String sid);
}

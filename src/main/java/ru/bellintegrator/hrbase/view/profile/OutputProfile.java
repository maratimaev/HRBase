package ru.bellintegrator.hrbase.view.profile;

/**
 * Профили JsonView для фильтрации полей json
 */
public interface OutputProfile {
    /**
     * JsonView для всех отправляемых данных
     */
    interface Data { }

    /**
     * Краткий вид, поля
     *  name
     *  inn
     *  isActive
     */
    interface Short extends Data { }

    /**
     * Расширенный вид, поля
     *   id
     *   name
     *   fullName
     *   inn
     *   kpp
     *   address
     *   phone
     *   isActive
     */
    interface Full extends Short { }
}

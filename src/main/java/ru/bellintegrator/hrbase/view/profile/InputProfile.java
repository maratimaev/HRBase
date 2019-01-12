package ru.bellintegrator.hrbase.view.profile;

/**
 * Профили @Validated для входящих json объектов
 */
public interface InputProfile {

    /**
     * Проверка корректности полей при запросе списка
     */
    interface GetList { }

    /**
     * Проверка корректности полей при сохранении объекта
     */
    interface Save { }

    /**
     * Проверка корректности полей при обновлении объекта
     */
    interface Update { }
}

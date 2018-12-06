package ru.bellintegrator.hrbase.profile;

/**
 * Профили JsonView для фильтрации полей класса Organization
 */
public interface OrganizationProfile {
    /**
     * Краткий вид, поля
     *  name
     *  inn
     *  isActive
     */
    interface Short {}

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
    interface Full extends Short {}
}

package ru.bellintegrator.hrbase.service;

import ru.bellintegrator.hrbase.view.Wrapper;
import ru.bellintegrator.hrbase.view.OrganizationView;

/**
 * Сервисы для работы с организацией
 */
public interface OrganizationService {
    /** Поиск в БД организации по id
     * @param id
     * @return обертка над view
     */
    Wrapper<OrganizationView> findOrganizationById(String id);

    /** Поиск в БД организации по параметрам
     * @param name название
     * @param inn ИНН
     * @param isActive признак активности
     * @return обертка над view
     */
    Wrapper<OrganizationView> getOrganizations(String name, String inn, String isActive);

    /** Сохранение новой организации в БД
     * @param organizationView входящий объект json
     */
    void saveOrganization(OrganizationView organizationView);

    /** Изменение данных организации
     * @param organizationView объеккт json
     */
    void updateOrganization(OrganizationView organizationView);
}

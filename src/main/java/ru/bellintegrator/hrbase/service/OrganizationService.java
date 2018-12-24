package ru.bellintegrator.hrbase.service;

import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.view.organization.OrganizationView;
import ru.bellintegrator.hrbase.view.result.Wrapper;

/**
 * Сервисы для работы с организацией
 */
public interface OrganizationService {
    /** Поиск в БД организации по id
     * @param id организации
     * @return обертка над view
     */
    Wrapper<OrganizationView> findOrganizationById(String id);

    /** Поиск в БД организации по параметрам
     * @param organizationView входящий объект json c constraints
     * @return обертка над view
     */
    Wrapper<OrganizationView> getOrganizations(OrganizationView organizationView);

    /** Сохранение новой организации в БД
     * @param organizationView входящий объект json c constraints
     */
    void saveOrganization(OrganizationView organizationView);

    /** Изменение данных организации
     * @param organizationView объект json c constraints
     */
    void updateOrganization(OrganizationView organizationView);

    /** Возвращает организацию по id из БД
     * @param sid организации
     * @return entity организации
     */
    Organization getOrgById(String sid);
}

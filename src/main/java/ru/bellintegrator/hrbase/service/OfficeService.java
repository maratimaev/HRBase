package ru.bellintegrator.hrbase.service;

import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.view.office.OfficeView;
import ru.bellintegrator.hrbase.view.result.Wrapper;

/**
 * Сервисы для работы с организацией
 */
public interface OfficeService {
    /** Поиск в БД организации по id
     * @param id организации
     * @return обертка над view
     */
    Wrapper<OfficeView> findOfficeById(String id);

    /** Поиск в БД офисов по параметрам
     * @param id организации
     * @param officeView входящий объект json c constraints
     * @return обертка над view
     */
    Wrapper<OfficeView> getOffices(String org_id, OfficeView officeView);

    /** Сохранение нового офиса в БД
     * @param officeView входящий объект json c constraints
     */
    void saveOffice(OfficeView officeView);

    /** Изменение данных офиса
     * @param officeView объект json c constraints
     */
    void updateOffice(OfficeView officeView);

    /** Возвращает офис по id из БД
     * @param sid офиса
     * @return entity офиса
     */
    Office getOfficeById(String sid);
}

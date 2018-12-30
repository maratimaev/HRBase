package ru.bellintegrator.hrbase.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.hrbase.service.CitizenshipServiceImpl;
import ru.bellintegrator.hrbase.service.DocumentTypeServiceImpl;
import ru.bellintegrator.hrbase.service.EmployerServiceImpl;
import ru.bellintegrator.hrbase.service.OfficeServiceImpl;
import ru.bellintegrator.hrbase.service.OrganizationServiceImpl;
import ru.bellintegrator.hrbase.view.country.CountryViewSave;
import ru.bellintegrator.hrbase.view.doctype.DocTypeViewSave;
import ru.bellintegrator.hrbase.view.employer.EmployerView;
import ru.bellintegrator.hrbase.view.office.OfficeView;
import ru.bellintegrator.hrbase.view.organization.OrganizationView;
import ru.bellintegrator.hrbase.view.result.Error;
import ru.bellintegrator.hrbase.view.result.Success;
import ru.bellintegrator.hrbase.view.result.Wrapper;

import java.net.URI;
import java.util.List;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Интеграционные тесты работы с сотрудником
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class EmployerTest {
    /**
     * Порт web сервера. Генерируется случайным образом.
     */
    @LocalServerPort
    private int port;

    /**
     * Объект для взаимодействия с RESTful Web Service.
     */
    @Autowired
    private TestRestTemplate restTemplate;
    /**
     * Объект для подготовки тестов. Сохранение примера офиса в БД.
     */
    @Autowired
    private OfficeServiceImpl officeService;
    /**
     * Объект для подготовки тестов. Сохранение примера организации в БД.
     */
    @Autowired
    private OrganizationServiceImpl organizationService;
    /**
     * Объект для подготовки тестов. Сохранение примера гражданства в БД.
     */
    @Autowired
    private CitizenshipServiceImpl citizenshipService;
    /**
     * Объект для подготовки тестов. Сохранение примера типа документа в БД.
     */
    @Autowired
    private DocumentTypeServiceImpl documentTypeService;
    /**
     * Объект для подготовки тестов. Сохранение примера сотрудника в БД.
     */
    @Autowired
    private EmployerServiceImpl employerService;

    /**
     * Проверка поиска сотрудника по id.
     */
    @Test
    public void whenGetEmployerByExistingIdThenCheckJsonData() {
        EmployerView sampleEmployer = saveSampleEmployer();

        String url = String.format("http://localhost:%s/user/%s", port, sampleEmployer.getId());
        ResponseEntity<Wrapper<EmployerView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<EmployerView>>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        EmployerView result = response.getBody().getData().get(0);

        assertThat(result, is(notNullValue()));
        assertThat(result.getFirstName(), is(sampleEmployer.getFirstName()));
        assertThat(result.getId(), is(sampleEmployer.getId()));
        assertThat(result.getMiddleName(), is(sampleEmployer.getMiddleName()));
        assertThat(result.getSecondName(), is(sampleEmployer.getSecondName()));
        assertThat(result.getPosition(), is(sampleEmployer.getPosition()));
        assertThat(result.getPhone(), is(sampleEmployer.getPhone()));
        assertThat(result.getDocName(), is(sampleEmployer.getDocName()));
        assertThat(result.getDocNumber(), is(sampleEmployer.getDocNumber()));
        assertThat(result.getDocDate(), is(sampleEmployer.getDocDate()));
        assertThat(result.getCitizenshipName(), is(sampleEmployer.getCitizenshipName()));
        assertThat(result.getCitizenshipCode(), is(sampleEmployer.getCitizenshipCode()));
        assertThat(result.getIsIdentified(), is(sampleEmployer.getIsIdentified()));
    }

    /**
     * Проверка генерации ошибки при поиске несуществующего сотрудника
     *
     * @throws Exception URISyntaxEception
     */
    @Test
    public void whenGetEmployerByFakeIdThenCheck404() throws Exception {
        String fakeId = "8888";
        URI uri = new URI(String.format("http://localhost:%s/user/%s", port, fakeId));
        ResponseEntity<Error> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<Error>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));

        assertThat(response.getBody().getError(),
                is(String.format("There is no finding by parameter, no such user id=%s", fakeId)));
    }

    /**
     * Проверка поиска сотрудника по имени, фамилии, отчеству, id офиса, должности, типу документа и гражданству
     */
    @Test
    public void whenGetEmployerListThenCheckJsonData() {
        EmployerView sampleEmployer = saveSampleEmployer();
        String url = String.format("http://localhost:%s/user/list", port);
        RequestEntity<EmployerView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(sampleEmployer);

        ResponseEntity<Wrapper<EmployerView>> response = restTemplate.exchange(
                request, new ParameterizedTypeReference<Wrapper<EmployerView>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        EmployerView result = response.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(sampleEmployer.getId()));
        assertThat(result.getFirstName(), is(sampleEmployer.getFirstName()));
        assertThat(result.getSecondName(), is(sampleEmployer.getSecondName()));
        assertThat(result.getMiddleName(), is(sampleEmployer.getMiddleName()));
        assertThat(result.getPosition(), is(sampleEmployer.getPosition()));
    }

    /**
     * Проверка генерации ошибки при поиске сотрудника без указания id офиса
     */
    @Test
    public void whenGetEmployerListByNullOfficeIdThenCheck406() {
        EmployerView sampleEmployer = saveSampleEmployer();
        sampleEmployer.setOfficeId(null);

        String url = String.format("http://localhost:%s/user/list", port);
        RequestEntity<EmployerView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(sampleEmployer);

        ResponseEntity<Error> response = restTemplate.exchange(request, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(), is("Field (officeId) can't be: null"));
    }

    /**
     * Проверка сохранения нового сотрудника
     */
    @Test
    public void whenSaveEmployerThenCheckSuccess() {
        String saveUrl = String.format("http://localhost:%s/user/save", port);
        EmployerView sampleEmployer = getSampleEmployer();

        ResponseEntity<Success> saveResponse = restTemplate.postForEntity(saveUrl, sampleEmployer, Success.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getResult(), is("success"));

        String getUrl = String.format("http://localhost:%s/user/%s", port, getSavedEmployerId(sampleEmployer));
        ResponseEntity<Wrapper<EmployerView>> getResponse = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<EmployerView>>() {
                });
        assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getResponse.getBody(), is(notNullValue()));
        EmployerView result = getResponse.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getFirstName(), is(sampleEmployer.getFirstName()));
    }

    /**
     * Проверка генерации ошибки при сохранении сотрудника при указании неверного имени
     */
    @Test
    public void whenSaveEmployerWithWrongFirstNameThenCheck406() {
        String url = String.format("http://localhost:%s/user/save", port);
        EmployerView sampleEmployer = getSampleEmployer();
        sampleEmployer.setFirstName(null);

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, sampleEmployer, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError(), is("Field (firstName) can't be: null"));
    }

    /**
     * Проверка генерации ошибки при сохранении сотрудника без заполнения всех полей
     */
    @Test
    public void whenSaveEmployerWithWrongBooleanThenCheck406() {
        String url = String.format("http://localhost:%s/user/save", port);
        EmployerView sampleEmployer = getSampleEmployer();
        sampleEmployer.setIsIdentified("wrongBoolean");

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, sampleEmployer, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError(), is("Field (isIdentified) can't be: wrongBoolean"));
    }

    /**
     * Проверка обновления полей сотрудника
     */
    @Test
    public void whenUpdateEmployerPhoneThenCheckSuccess() {
        EmployerView sampleEmployer = saveSampleEmployer();
        String url = String.format("http://localhost:%s/user/update", port);
        sampleEmployer.setPhone("987654321");

        ResponseEntity<Success> response = restTemplate.postForEntity(url, sampleEmployer, Success.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getResult(), is("success"));
        String getUrl = String.format("http://localhost:%s/user/%s", port, sampleEmployer.getId());
        ResponseEntity<Wrapper<EmployerView>> responseEntity = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<EmployerView>>() {
                });
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        EmployerView result = responseEntity.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPhone(), is(sampleEmployer.getPhone()));
    }

    /**
     * Проверка генерации ошибки при обновлении полей сотрудника без указания должности
     */
    @Test
    public void whenUpdateEmployerWithoutPositionThenCheckError406() {
        String url = String.format("http://localhost:%s/user/update", port);
        EmployerView sampleEmployer = saveSampleEmployer();
        sampleEmployer.setPosition(null);

        ResponseEntity<Error> response = restTemplate.postForEntity(url, sampleEmployer, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(), is("Field (position) can't be: null"));
    }

    /**
     * Проверка генерации ошибки при обновлении поля id сотрудника =0
     */
    @Test
    public void whenUpdateEmployerWithWrongIdThenCheck406() {
        String url = String.format("http://localhost:%s/user/update", port);
        EmployerView sampleEmployer = saveSampleEmployer();
        sampleEmployer.setId("0");

        ResponseEntity<Error> response = restTemplate.postForEntity(url, sampleEmployer, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(), is("Field (id) can't be: 0"));
    }

    /**
     * Создание тестового объекта OfficeView
     * @return созданный объект
     */
    private OfficeView getSampleOffice() {
        String orgId = this.saveSampleOrganization();
        OfficeView officeView = new OfficeView();
        officeView.setName(generateAlphabet(50));
        officeView.setAddress(generateAlphabet(200));
        officeView.setIsActive(String.valueOf(new Random().nextBoolean()));
        officeView.setPhone(generateDigits(20));
        officeView.setOrgId(orgId);
        return officeView;
    }

    /**
     * Сохранение тестового объекта офис в базе данных
     */
    private OfficeView saveSampleOffice() {
        OfficeView officeView = getSampleOffice();
        this.officeService.save(officeView);
        List<OfficeView> list = this.officeService.list(officeView);
        OfficeView result = list.get(0);
        result.setOrgId(officeView.getOrgId());
        return result;
    }

    /** Получение id сохраненного в БД сотрудника
     * @param employerView сотрудника
     * @return id сотрудника
     */
    private String getSavedEmployerId(EmployerView employerView) {
        List<EmployerView> list = this.employerService.list(employerView);
        EmployerView result = list.get(0);
        return result.getId();
    }
    /**
     * Создание тестового объекта EmployerView
     * @return созданный объект
     */
    private EmployerView getSampleEmployer() {
        OfficeView officeView = saveSampleOffice();
        String code = saveSampleCountry();
        String name = saveSampleDocType();
        EmployerView employerView = new EmployerView();
        employerView.setFirstName(generateAlphabet(50));
        employerView.setLastName(generateAlphabet(50));
        employerView.setMiddleName(generateAlphabet(50));
        employerView.setSecondName(generateAlphabet(50));
        employerView.setPhone(generateDigits(12));
        employerView.setPosition(generateAlphabet(50));
        employerView.setPhone(generateDigits(20));
        employerView.setOfficeId(officeView.getId());
        employerView.setCitizenshipCode(code);
        employerView.setDocNumber(generateDigits(20));
        employerView.setDocDate("2001-02-02");
        employerView.setDocName(name);
        employerView.setIsIdentified(String.valueOf(new Random().nextBoolean()));
        return employerView;
    }

    /**
     * Сохранение тестового объекта сотрудник в базе данных
     */
    private EmployerView saveSampleEmployer() {
        EmployerView employerView = getSampleEmployer();
        this.employerService.save(employerView);
        List<EmployerView> list = this.employerService.list(employerView);
        return list.get(0);
    }

    /**
     * Создание тестового объекта OrganizationView
     * @return созданный объект
     */
    private OrganizationView getSampleOrganization() {
        OrganizationView orgView = new OrganizationView();
        orgView.setName(generateAlphabet(50));
        orgView.setFullName(generateAlphabet(100));
        orgView.setInn(generateDigits(12));
        orgView.setKpp(generateDigits(9));
        orgView.setAddress(generateAlphabet(200));
        orgView.setPhone(generateDigits(20));
        orgView.setIsActive(String.valueOf(new Random().nextBoolean()));
        return orgView;
    }

    /**
     * Сохранение тестового объекта организация в базе данных
     */
    private String saveSampleOrganization() {
        OrganizationView organizationView = getSampleOrganization();
        this.organizationService.save(organizationView);
        List<OrganizationView> list = this.organizationService.list(organizationView);
        return list.get(0).getId();
    }

    /**
     * Создание тестового объекта DocTypeViewSave
     * @return созданный объект
     */
    private DocTypeViewSave getSampleDocType() {
        DocTypeViewSave docTypeViewSave = new DocTypeViewSave();
        docTypeViewSave.setCode(generateDigits(2));
        docTypeViewSave.setName(generateAlphabet(100));
        return docTypeViewSave;
    }

    /**
     * Сохранение тестового объекта тип документа в базе данных
     */
    private String saveSampleDocType() {
        DocTypeViewSave docTypeViewSave = getSampleDocType();
        this.documentTypeService.save(docTypeViewSave);
        return docTypeViewSave.getName();
    }

    /**
     * Создание тестового объекта CountryViewSave
     * @return созданный объект
     */
    private CountryViewSave getSampleCountry() {
        CountryViewSave countryViewSave = new CountryViewSave();
        countryViewSave.setCode(generateDigits(3));
        countryViewSave.setName(generateAlphabet(100));
        return countryViewSave;
    }

    /**
     * Сохранение тестового объекта страна в базе данных
     */
    private String saveSampleCountry() {
        CountryViewSave countryViewSave = getSampleCountry();
        this.citizenshipService.save(countryViewSave);
        return countryViewSave.getCode();
    }

    /** Генерация строки из случайных символов алфавита
     * @param size длина строки
     * @return строка
     */
    private String generateAlphabet(int size) {
        return generateCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ", size);
    }

    /** Генерация строки из случайных цифровых символов
     * @param size длина строки
     * @return строка
     */
    private String generateDigits(int size) {
        return generateCode("0123456789", size);
    }

    /** Генерация случайной строки из набора символов
     * @param pattern список символов
     * @param size длина строки
     * @return строка
     */
    private String generateCode(String pattern, int size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            result.append(pattern.charAt(random.nextInt(pattern.length())));
        }
        return result.toString();
    }
}
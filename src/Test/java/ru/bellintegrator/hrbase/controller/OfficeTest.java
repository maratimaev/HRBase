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
import ru.bellintegrator.hrbase.service.OfficeServiceImpl;
import ru.bellintegrator.hrbase.service.OrganizationServiceImpl;
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
 * Интеграционные тесты работы с office
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class OfficeTest {
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
     * Проверка поиска офисов по id.
     */
    @Test
    public void whenGetOfficeByExistingIdThenCheckJsonData() {
        OfficeView sampleOffice = saveSampleOffice();

        String url = String.format("http://localhost:%s/office/%s", port, sampleOffice.getId());
        ResponseEntity<Wrapper<OfficeView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OfficeView>>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        OfficeView officeView = response.getBody().getData().get(0);

        assertThat(officeView, is(notNullValue()));
        assertThat(officeView.getName(), is(sampleOffice.getName()));
        assertThat(officeView.getAddress(), is(sampleOffice.getAddress()));
        assertThat(officeView.getIsActive(), is(sampleOffice.getIsActive()));
        assertThat(officeView.getPhone(), is(sampleOffice.getPhone()));
    }

    /**
     * Проверка генерации ошибки при поиске несуществующего офиса
     *
     * @throws Exception URISyntaxEception
     */
    @Test
    public void whenGetOfficeByFakeIdThenCheck404() throws Exception {
        URI uri = new URI(String.format("http://localhost:%s/office/8888", port));
        ResponseEntity<Error> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<Error>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));

        assertThat(response.getBody().getError(), is("There is no finding by parameter, no such office id=8888"));
    }

    /**
     * Проверка поиска оффиса по имени, телефону, признаку активности, id организации
     */
    @Test
    public void whenGetOfficeListThenCheckJsonData() {
        OfficeView sampleOffice = saveSampleOffice();
        String url = String.format("http://localhost:%s/office/list/%s", port, sampleOffice.getOrgId());
        RequestEntity<OfficeView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(sampleOffice);

        ResponseEntity<Wrapper<OfficeView>> response = restTemplate.exchange(
                request, new ParameterizedTypeReference<Wrapper<OfficeView>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        OfficeView result = response.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(sampleOffice.getId()));
        assertThat(result.getName(), is(sampleOffice.getName()));
        assertThat(result.getIsActive(), is(sampleOffice.getIsActive()));
    }

    /**
     * Проверка генерации ошибки при поиске офиса без указания id организации
     */
    @Test
    public void whenGetOfficeListByFakeOrgIdThenCheck404() {
        OfficeView officeView = saveSampleOffice();
        String fakeOrgID = "2";
        officeView.setOrgId(fakeOrgID);

        String url = String.format("http://localhost:%s/office/list/%s", port, fakeOrgID);
        RequestEntity<OfficeView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(officeView);

        ResponseEntity<Error> response = restTemplate.exchange(request, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(),
                is(String.format("There is no finding by parameter, name=%s, phone=%s, isActive=%s, organization id=%s",
                        officeView.getName(), officeView.getPhone(), officeView.getIsActive(), fakeOrgID)));
    }

    /**
     * Проверка сохранения нового офиса
     */
    @Test
    public void whenSaveOfficeThenCheckSuccess() {
        String saveUrl = String.format("http://localhost:%s/office/save", port);
        OfficeView sampleOffice = getSampleOffice();

        ResponseEntity<Success> saveResponse = restTemplate.postForEntity(saveUrl, sampleOffice, Success.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getResult(), is("success"));

        String getUrl = String.format("http://localhost:%s/office/%s", port, getSavedOfficeId(sampleOffice));
        ResponseEntity<Wrapper<OfficeView>> getResponse = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OfficeView>>() {
                });
        assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getResponse.getBody(), is(notNullValue()));
        OfficeView result = getResponse.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(sampleOffice.getName()));
    }

    /**
     * Проверка генерации ошибки при сохранении офиса при указании неверного id организации
     */
    @Test
    public void whenSaveOfficeWithWrongOrgIdThenCheck404() {
        String fakeOrgId = "8888";
        String url = String.format("http://localhost:%s/office/save", port);
        OfficeView officeView = getSampleOffice();
        officeView.setOrgId(fakeOrgId);

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, officeView, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError(), is(String.format("There is no finding by parameter, no such organization id=%s", fakeOrgId)));
    }

    /**
     * Проверка генерации ошибки при сохранении офиса без заполнения всех полей
     */
    @Test
    public void whenSaveOfficeWithWrongBooleanThenCheck406() {
        String url = String.format("http://localhost:%s/office/save", port);
        OfficeView officeView = getSampleOffice();
        officeView.setIsActive("wrongBoolean");

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, officeView, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError(), is("Field (isActive) can't be: wrongBoolean"));
    }

    /**
     * Проверка обновления полей офиса
     */
    @Test
    public void whenUpdateOfficePhoneThenCheckSuccess() {
        OfficeView sampleOffice = saveSampleOffice();
        String url = String.format("http://localhost:%s/office/update", port);
        sampleOffice.setPhone("987654321");

        ResponseEntity<Success> response = restTemplate.postForEntity(url, sampleOffice, Success.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getResult(), is("success"));
        String getUrl = String.format("http://localhost:%s/office/%s", port, sampleOffice.getId());
        ResponseEntity<Wrapper<OfficeView>> responseEntity = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OfficeView>>() {
                });
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        OfficeView result = responseEntity.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPhone(), is(sampleOffice.getPhone()));
    }

    /**
     * Проверка генерации ошибки при обновлении полей офиса без указания имени
     */
    @Test
    public void whenUpdateOfficeWithoutNameThenCheckError406() {
        String url = String.format("http://localhost:%s/office/update", port);
        OfficeView sampleOffice = saveSampleOffice();
        sampleOffice.setName(null);

        ResponseEntity<Error> response = restTemplate.postForEntity(url, sampleOffice, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(), is("Field (name) can't be: null"));
    }

    /**
     * Проверка генерации ошибки при обновлении поля id офиса =0
     */
    @Test
    public void whenUpdateOfficeWithWrongIdThenCheck406() {
        String url = String.format("http://localhost:%s/office/update", port);
        OfficeView sampleOffice = getSampleOffice();
        sampleOffice.setId("0");

        ResponseEntity<Error> response = restTemplate.postForEntity(url, sampleOffice, Error.class);
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

    /** Получение id сохраненного в БД офиса
     * @param officeView офиса
     * @return id офиса
     */
    private String getSavedOfficeId(OfficeView officeView) {
        List<OfficeView> list = this.officeService.list(officeView);
        OfficeView result = list.get(0);
        return result.getId();
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
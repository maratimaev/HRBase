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
import ru.bellintegrator.hrbase.service.OrganizationServiceImpl;
import ru.bellintegrator.hrbase.view.OrganizationView;
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
 * Интеграционные тесты работы с organization
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class OrganizationTest {
    /**
     * Порт web сервера. Генерируется случайным образом.
     */
    @LocalServerPort
    private int port;

    /**
     * Объект для взаимодействия с RESTful Web Service
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Объект для подготовки тестов. Сохранение примера организации в БД.
     */
    @Autowired
    private OrganizationServiceImpl service;

    /**
     * Проверка поиска организации по id
     */
    @Test
    public void whenGetOrganizationByExistingIdThenCheckJsonData() {
        OrganizationView sampleOrganization = saveSampleOrganization();

        String url = String.format("http://localhost:%s/organization/%s", port, sampleOrganization.getId());
        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        OrganizationView result = response.getBody().getData();

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(sampleOrganization.getName()));
        assertThat(result.getFullName(), is(sampleOrganization.getFullName()));
        assertThat(result.getInn(), is(sampleOrganization.getInn()));
        assertThat(result.getKpp(), is(sampleOrganization.getKpp()));
        assertThat(result.getAddress(), is(sampleOrganization.getAddress()));
        assertThat(result.getPhone(), is(sampleOrganization.getPhone()));
        assertThat(result.getIsActive(), is(sampleOrganization.getIsActive()));
    }

    /** Проверка генерации ошибки при поиске несуществующей организации
     * @throws Exception URISyntaxEception
     */
    @Test
    public void whenGetOrganizationByFakeIdThenCheck404() throws Exception {
        String fakeId = "8888";
        URI uri = new URI(String.format("http://localhost:%s/organization/%s", port, fakeId));
        ResponseEntity<Error> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<Error>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));

        assertThat(response.getBody().getError(),
                is(String.format("There is no finding by parameter, no such organization id=%s", fakeId)));
    }

    /**
     * Проверка поиска организации по имени, инн, признаку активности
     */
    @Test
    public void whenGetOrganizationListThenCheckJsonData() {
        OrganizationView sampleOrganization = saveSampleOrganization();

        String url = String.format("http://localhost:%s/organization/list", port);
        RequestEntity<OrganizationView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(sampleOrganization);

        ResponseEntity<Wrapper<List<OrganizationView>>> response = restTemplate.exchange(
                request, new ParameterizedTypeReference<Wrapper<List<OrganizationView>>>() {
        });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        OrganizationView result = response.getBody().getData().get(0);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(sampleOrganization.getId()));
        assertThat(result.getName(), is(sampleOrganization.getName()));
        assertThat(result.getIsActive(), is(sampleOrganization.getIsActive()));
    }

    /**
     * Проверка генерации ошибки при поиске организации без указания имени
     */
    @Test
    public void whenGetOrganizationListByFakeNameThenCheck404() {
        String fakeName = "fakeName";
        OrganizationView sampleOrganization = saveSampleOrganization();
        sampleOrganization.setName(fakeName);

        String url = String.format("http://localhost:%s/organization/list", port);
        RequestEntity<OrganizationView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(sampleOrganization);

        ResponseEntity<Error> response = restTemplate.exchange(request, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(),
                is(String.format("There is no finding by parameter, name=%s, inn=%s, isActive=%s",
                        fakeName, sampleOrganization.getInn(), sampleOrganization.getIsActive())));
    }

    /**
     * Проверка сохранения новой организации
     */
    @Test
    public void whenSaveOrganizationThenCheckSuccess() {
        String saveUrl = String.format("http://localhost:%s/organization/save", port);
        OrganizationView sampleOrg = getSampleOrganization();

        ResponseEntity<Success> saveResponse = restTemplate.postForEntity(saveUrl, sampleOrg, Success.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getResult(), is("success"));

        String getUrl = String.format("http://localhost:%s/organization/%s", port, getSavedOrgId(sampleOrg));
        ResponseEntity<Wrapper<OrganizationView>> getResponse = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getResponse.getBody(), is(notNullValue()));
        OrganizationView result = getResponse.getBody().getData();
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(sampleOrg.getName()));
    }

    /**
     * Проверка генерации ошибки при сохранении организации без указания имени
     */
    @Test
    public void whenSaveOrganizationWithoutNameThenCheck406() {
        String url = String.format("http://localhost:%s/organization/save", port);
        OrganizationView sampleOrg = getSampleOrganization();
        sampleOrg.setInn("8888");
        sampleOrg.setName(null);

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, sampleOrg, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError().contains("Field (name) can't be: null"), is(true));
    }

    /**
     *  Проверка генерации ошибки при сохранении организации без заполнения всех полей
     */
    @Test
    public void whenSaveOrganizationWithoutAnyFieldsThenCheck406() {
        String url = String.format("http://localhost:%s/organization/save", port);
        OrganizationView sampleOrg = new OrganizationView();

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, sampleOrg, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError().contains("can't be: null"), is(true));
    }

    /**
     * Проверка обновления полей организации
     */
    @Test
    public void whenUpdateOrganizationThenCheckSuccess() {
        OrganizationView sampleOrg = saveSampleOrganization();
        String url = String.format("http://localhost:%s/organization/update", port);
        sampleOrg.setInn("987654321");

        ResponseEntity<Success> response = restTemplate.postForEntity(url, sampleOrg, Success.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getResult(), is("success"));
        String getUrl = String.format("http://localhost:%s/organization/%s", port, sampleOrg.getId());
        ResponseEntity<Wrapper<OrganizationView>> responseEntity = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        OrganizationView result = responseEntity.getBody().getData();
        assertThat(result, is(notNullValue()));
        assertThat(result.getInn(), is(sampleOrg.getInn()));
    }

    /**
     * Проверка генерации ошибки при обновлении полей организации без указания инн
     */
    @Test
    public void whenUpdateOrganizationWithoutInnThenCheckError() {
        String url = String.format("http://localhost:%s/organization/update", port);
        OrganizationView sampleOrg = saveSampleOrganization();
        sampleOrg.setInn(null);

        ResponseEntity<Error> response = restTemplate.postForEntity(url, sampleOrg, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(), is("Field (inn) can't be: null (не может быть пусто)"));
    }

    /**
     * Проверка генерации ошибки при обновлении полей организации без указания всех полей
     */
    @Test
    public void whenUpdateOrganizationWithoutAnyFieldsThenCheck4xx()  {
        String url = String.format("http://localhost:%s/organization/update", port);
        OrganizationView sampleOrg = new OrganizationView();

        ResponseEntity<Error> response = restTemplate.postForEntity(url, sampleOrg, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError().contains("can't be: null"), is(true));
    }

    /**
     * Создание тестового объекта OrganizationView
     *
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
     * Сохранение тестового объекта в базе данных
     */
    private OrganizationView saveSampleOrganization() {
        OrganizationView organizationView = getSampleOrganization();
        this.service.save(organizationView);
        List<OrganizationView> list = this.service.list(organizationView);
        return list.get(0);
    }

    private String getSavedOrgId(OrganizationView organizationView) {
        List<OrganizationView> list = this.service.list(organizationView);
        OrganizationView result = list.get(0);
        return result.getId();
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

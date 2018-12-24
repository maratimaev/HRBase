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
import ru.bellintegrator.hrbase.view.organization.OrganizationView;
import ru.bellintegrator.hrbase.view.result.Error;
import ru.bellintegrator.hrbase.view.result.Success;
import ru.bellintegrator.hrbase.view.result.Wrapper;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Интеграционные тесты работы с organization
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        saveSampleOrganization();

        String url = String.format("http://localhost:%s/organization/1", port);
        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        OrganizationView organizationView = response.getBody().getData().get(0);

        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getName(), is("testName"));
        assertThat(organizationView.getFullName(), is("testFullName"));
        assertThat(organizationView.getInn(), is("123456789"));
        assertThat(organizationView.getKpp(), is("123456789"));
        assertThat(organizationView.getAddress(), is("testAddress"));
        assertThat(organizationView.getPhone(), is("123456789"));
        assertThat(organizationView.getIsActive(), is("true"));
    }

    /** Проверка генерации ошибки при поиске несуществующей организации
     * @throws Exception URISyntaxEception
     */
    @Test
    public void whenGetOrganizationByFakeIdThenCheck404() throws Exception {
        URI uri = new URI(String.format("http://localhost:%s/organization/1", port));
        ResponseEntity<Error> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<Error>() {
                });
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));

        assertThat(response.getBody().getError(), is("There is no organization by such id = 1"));
    }

    /**
     * Проверка поиска организации по имени, инн, признаку активности
     */
    @Test
    public void whenGetOrganizationListThenCheckJsonData() {
        saveSampleOrganization();
        OrganizationView orgView = new OrganizationView();
        orgView.setName("testName");
        orgView.setInn("123456789");
        orgView.setIsActive("true");

        String url = String.format("http://localhost:%s/organization/list", port);
        RequestEntity<OrganizationView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(orgView);

        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                request, new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
        });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));

        OrganizationView organizationView = response.getBody().getData().get(0);
        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getId(), is("1"));
        assertThat(organizationView.getName(), is("testName"));
        assertThat(organizationView.getIsActive(), is("true"));
    }

    /**
     * Проверка генерации ошибки при поиске организации без указания имени
     */
    @Test
    public void whenGetOrganizationListByFakeNameThenCheck404() {
        saveSampleOrganization();
        OrganizationView orgView = new OrganizationView();
        orgView.setName("fakeName");
        orgView.setInn("123456789");
        orgView.setIsActive("true");

        String url = String.format("http://localhost:%s/organization/list", port);
        RequestEntity<OrganizationView> request = RequestEntity
                .post(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(orgView);

        ResponseEntity<Error> response = restTemplate.exchange(request, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(),
                is("There is no organization by such name = fakeName, inn = 123456789, isActive = true"));
    }

    /**
     * Проверка сохранения новой организации
     */
    @Test
    public void whenSaveOrganizationThenCheckSuccess() {
        String saveUrl = String.format("http://localhost:%s/organization/save", port);
        OrganizationView orgView = getSampleOrganization();

        ResponseEntity<Success> saveResponse = restTemplate.postForEntity(saveUrl, orgView, Success.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getResult(), is("success"));

        String getUrl = String.format("http://localhost:%s/organization/1", port);
        ResponseEntity<Wrapper<OrganizationView>> getResponse = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getResponse.getBody(), is(notNullValue()));
        OrganizationView organizationView = getResponse.getBody().getData().get(0);
        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getName(), is("testName"));
    }

    /**
     * Проверка генерации ошибки при сохранении организации без указания имени
     */
    @Test
    public void whenSaveOrganizationWithoutNameThenCheck406() {
        String url = String.format("http://localhost:%s/organization/save", port);
        OrganizationView orgView = getSampleOrganization();
        orgView.setName(null);

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, orgView, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError(), is("Field (name) can't be: null"));
    }

    /**
     *  Проверка генерации ошибки при сохранении организации без заполнения всех полей
     */
    @Test
    public void whenSaveOrganizationWithoutAnyFieldsThenCheck406() {
        String url = String.format("http://localhost:%s/organization/save", port);
        OrganizationView orgView = new OrganizationView();

        ResponseEntity<Error> saveResponse = restTemplate.postForEntity(url, orgView, Error.class);
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(saveResponse.getBody(), is(notNullValue()));
        assertThat(saveResponse.getBody().getError().contains("can't be: null"), is(true));
    }

    /**
     * Проверка обновления полей организации
     */
    @Test
    public void whenUpdateOrganizationThenCheckSuccess() {
        saveSampleOrganization();

        String url = String.format("http://localhost:%s/organization/update", port);
        OrganizationView orgView = getSampleOrganization();
        orgView.setInn("987654321");

        ResponseEntity<Success> response = restTemplate.postForEntity(url, orgView, Success.class);
        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getResult(), is("success"));
        String getUrl = String.format("http://localhost:%s/organization/1", port);
        ResponseEntity<Wrapper<OrganizationView>> responseEntity = restTemplate.exchange(
                getUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        OrganizationView organizationView = responseEntity.getBody().getData().get(0);
        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getInn(), is("987654321"));
    }

    /**
     * Проверка генерации ошибки при обновлении полей организации без указания инн
     */
    @Test
    public void whenUpdateOrganizationWithoutInnThenCheckError() {
        String url = String.format("http://localhost:%s/organization/update", port);
        OrganizationView orgView = getSampleOrganization();
        orgView.setInn(null);

        ResponseEntity<Error> response = restTemplate.postForEntity(url, orgView, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError(), is("Field (inn) can't be: null"));
    }

    /**
     * Проверка генерации ошибки при обновлении полей организации без указания всех полей
     */
    @Test
    public void whenUpdateOrganizationWithoutAnyFieldsThenCheck4xx()  {
        String url = String.format("http://localhost:%s/organization/update", port);
        OrganizationView orgView = new OrganizationView();

        ResponseEntity<Error> response = restTemplate.postForEntity(url, orgView, Error.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getError().contains("can't be: null"), is(true));
    }

    /** Создание тестового объекта OrganizationView
     * @return созданный объект
     */
    private OrganizationView getSampleOrganization() {
        OrganizationView orgView = new OrganizationView();
        orgView.setId("1");
        orgView.setName("testName");
        orgView.setFullName("testFullName");
        orgView.setInn("123456789");
        orgView.setKpp("123456789");
        orgView.setAddress("testAddress");
        orgView.setPhone("123456789");
        orgView.setIsActive("true");
        return orgView;
    }

    /**
     * Сохранение тестового объекта в базе данных
     */
    private void saveSampleOrganization() {
        this.service.saveOrganization(getSampleOrganization());
    }
}

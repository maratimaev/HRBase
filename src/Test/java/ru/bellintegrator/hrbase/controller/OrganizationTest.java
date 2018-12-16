package ru.bellintegrator.hrbase.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.Wrapper;
import ru.bellintegrator.hrbase.view.status.Error;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Интеграционные тесты работы с Organization
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
     * Проверка поиска организации по id
     */
    @Test
    public void whenGetOrganizationByExistingIdThenCheckJsonData() {
        String url = String.format("http://localhost:%s/organization/1", port);
        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        OrganizationView organizationView = response.getBody().getData().get(0);

        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getName(), is("ООО «Пример»"));
        assertThat(organizationView.getFullName(), is("Общество с ограниченной ответственностью «Пример»"));
        assertThat(organizationView.getInn(), is("1234567891"));
        assertThat(organizationView.getKpp(), is("123456789"));
        assertThat(organizationView.getAddress(), is("124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1"));
        assertThat(organizationView.getPhone(), is("+7 499 333 4455"));
        assertThat(organizationView.isActive(), is(false));
    }

    /** Проверка генерации ошибки при поиске несуществующей организации
     * @throws Exception URISyntaxEception
     */
    @Test
    public void whenGetOrganizationByFakeIdThenCheck500() throws Exception {
        URI uri = new URI(String.format("http://localhost:%s/organization/10", port));
        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Проверка поиска организации по имени, инн, признаку активности
     */
    @Test
    public void whenGetOrganizationListThenCheckJsonData() {
        String url = new StringBuilder()
                .append(String.format("http://localhost:%s/organization/list", port))
                .append("?name=ООО «Пример»")
                .append("&inn=1234567891")
                .append("&isActive=false")
                .toString();

        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                url, HttpMethod.POST, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
         OrganizationView organizationView = response.getBody().getData().get(0);

        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getId(), is(1));
        assertThat(organizationView.getName(), is("ООО «Пример»"));
        assertThat(organizationView.isActive(), is(false));
    }

    /**
     * Проверка генерации ошибки при поиске организации без указания имени
     */
    @Test
    public void whenGetOrganizationListByFakeNameThenCheck500() {
        String url = new StringBuilder()
                .append(String.format("http://localhost:%s/organization/list", port))
                .append("?name=ООО «Error»")
                .append("&inn=1234567891")
                .append("&isActive=false")
                .toString();

        ResponseEntity<Error> response = restTemplate.exchange(
                url, HttpMethod.POST, null,
                new ParameterizedTypeReference<Error>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(response.getBody().getError(), is("There is no such organization"));
    }

    /** Проверка сохранения новой организации
     * @throws Exception JSONException
     */
    @Test
    public void whenSaveOrganizationThenCheckSuccess() throws Exception {
        String url = String.format("http://localhost:%s/organization/save", port);
        JSONObject request = new JSONObject();
        request.put("name", "ООО «New»");
        request.put("fullName", "Общество с ограниченной ответственностью «Пример»");
        request.put("inn", "1234567891");
        request.put("kpp", "123456789");
        request.put("address", "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<Error> responseBody = restTemplate.exchange(url, HttpMethod.POST, entity, Error.class);

        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getBody().getError(), is(nullValue()));

        url = String.format("http://localhost:%s/organization/3", port);
        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        OrganizationView organizationView = response.getBody().getData().get(0);

        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getName(), is("ООО «New»"));
        }

    /** Проверка генерации ошибки при сохранении организации без указания имени
     * @throws Exception JSONException
     */
    @Test
    public void whenSaveOrganizationWithoutNameThenCheckError() throws Exception {
        String url = String.format("http://localhost:%s/organization/save", port);
        JSONObject request = new JSONObject();
        request.put("fullName", "Общество с ограниченной ответственностью «Пример»");
        request.put("inn", "1234567891");
        request.put("kpp", "123456789");
        request.put("address", "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<Error> responseBody = restTemplate.exchange(url, HttpMethod.POST, entity, Error.class);

        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getBody().getError(), is("Field (name) can't be: null"));
    }

    /**
     *  Проверка генерации ошибки при сохранении организации без заполнения всех полей
     */
    @Test
    public void whenSaveOrganizationWithoutAnyFieldsThenCheck4xx() {
        String url = String.format("http://localhost:%s/organization/save", port);
        JSONObject request = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<Error> responseBody = restTemplate.exchange(url, HttpMethod.POST, entity, Error.class);

        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getBody(), is(notNullValue()));
    }

    /** Проверка обновления полей организации
     * @throws Exception JSONException
     */
    @Test
    public void whenUpdateOrganizationThenCheckSuccess() throws Exception {
        String url = String.format("http://localhost:%s/organization/update", port);
        JSONObject request = new JSONObject();
        request.put("id", "2");
        request.put("name", "ООО «New»");
        request.put("fullName", "Общество с ограниченной ответственностью «Пример»");
        request.put("inn", "1234567891");
        request.put("kpp", "123456789");
        request.put("address", "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<Error> responseBody = restTemplate.exchange(url, HttpMethod.POST, entity, Error.class);
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getBody().getError(), is(nullValue()));

        url = String.format("http://localhost:%s/organization/2", port);
        ResponseEntity<Wrapper<OrganizationView>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Wrapper<OrganizationView>>() {
                });
        OrganizationView organizationView = response.getBody().getData().get(0);

        assertThat(organizationView, is(notNullValue()));
        assertThat(organizationView.getName(), is("ООО «New»"));
    }

    /** Проверка генерации ошибки при обновлении полей организации без указания инн
     * @throws Exception JSONException
     */
    @Test
    public void whenUpdateOrganizationWithoutInnThenCheckError() throws Exception {
        String url = String.format("http://localhost:%s/organization/update", port);
        JSONObject request = new JSONObject();
        request.put("id", "1");
        request.put("name", "ООО «New»");
        request.put("fullName", "Общество с ограниченной ответственностью «Пример»");
        request.put("kpp", "123456789");
        request.put("address", "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<Error> responseBody = restTemplate.exchange(url, HttpMethod.POST, entity, Error.class);
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getBody().getError(), is("Field (inn) can't be: null"));
    }

    /**
     * Проверка генерации ошибки при обновлении полей организации без указания всех полей
     */
    @Test
    public void whenUpdateOrganizationWithoutAnyFieldsThenCheck4xx()  {
        String url = String.format("http://localhost:%s/organization/update", port);
        JSONObject request = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<Error> responseBody = restTemplate.exchange(url, HttpMethod.POST, entity, Error.class);
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getBody(), is(notNullValue()));
    }
}

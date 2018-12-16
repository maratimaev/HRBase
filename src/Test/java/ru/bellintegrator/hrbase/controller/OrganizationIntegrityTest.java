package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.bellintegrator.hrbase.view.OrganizationView;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Интеграционные тесты работы с Organization
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class OrganizationIntegrityTest {

    /**
     * Внедрение объекта для работы с mock
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Внедрение объекта-примера organizationView
     */
    private OrganizationView organizationView;

    /**
     * Инициализация organizationView тестовыми данными
     */
    @Before
    public void setUp() {
        this.organizationView = new OrganizationView(0,
                "ООО «Пример»",
                "Общество с ограниченной ответственностью «Пример»",
                "1234567891",
                "123456789",
                "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1"
        );
    }

    /** Проверка поиска организации по id
     * @throws Exception
     */
    @Test
    public void whenGetOrganizationByExistingIdThenCheckJsonData() throws Exception {
        this.mockMvc.perform(get("/organization/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("ООО «Пример»")))
                .andExpect(jsonPath("$.data[0].fullName", is("Общество с ограниченной ответственностью «Пример»")))
                .andExpect(jsonPath("$.data[0].inn", is("1234567891")))
                .andExpect(jsonPath("$.data[0].kpp", is("123456789")));
    }

    /** Проверка генерации ошибки при поиске несуществующей организации
     * @throws Exception
     */
    @Test
    public void whenGetOrganizationByFakeIdThenCheck500() throws Exception {
        this.mockMvc.perform(get("/organization/{id}", 10))
                .andExpect(status().isInternalServerError());
    }

    /** Проверка поиска организации по имени, инн, признаку активности
     * @throws Exception
     */
    @Test
    public void whenGetOrganizationListThenCheckJsonData() throws Exception {
        this.mockMvc.perform(post("/organization/list")
                .param("name", "ООО «Пример»")
                .param("inn", "1234567891")
                .param("isActive", "false")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("ООО «Пример»")))
                .andExpect(jsonPath("$.data[0].isActive", is(false)));
    }

    /** Проверка генерации ошибки при поиске организации без указания имени
     * @throws Exception
     */
    @Test
    public void whenGetOrganizationListByFakeNameThenCheck500() throws Exception {
        this.mockMvc.perform(post("/organization/list")
                .param("name", "ООО «Error»")
                .param("inn", "1234567891")
                .param("isActive", "false")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("There is no such organization")));
    }

    /** Проверка сохранения новой организации
     * @throws Exception
     */
    @Test
    public void whenSaveOrganizationThenCheckSuccess() throws Exception {
               this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(this.organizationView)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("success")));
    }

    /** Проверка генерации ошибки при сохранении организации без указания имени
     * @throws Exception
     */
    @Test
    public void whenSaveOrganizationWithoutNameThenCheckError() throws Exception {
        this.organizationView.setName(null);
        String inputJson = asJsonString(this.organizationView);

        this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.error", is("Field (name) can't be: null")));
    }

    /** Проверка генерации ошибки при сохранении организации без заполнения всех полей
     * @throws Exception
     */
    @Test
    public void whenSaveOrganizationWithoutAnyFieldsThenCheck4xx() throws Exception {
        this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    /** Проверка обновления полей организации
     * @throws Exception
     */
    @Test
    public void whenUpdateOrganizationThenCheckSuccess() throws Exception {
        this.organizationView.setId(2);
        this.organizationView.setName("ООО «New»");
        this.organizationView.setFullName("Общество с ограниченной ответственностью «New»");

        this.mockMvc.perform(post("/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(organizationView)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("success")));
    }

    /** Проверка генерации ошибки при обновлении полей организации без указания инн
     * @throws Exception
     */
    @Test
    public void whenUpdateOrganizationWithoutInnThenCheckError() throws Exception {
        this.organizationView.setInn(null);
        String inputJson = asJsonString(this.organizationView);

        this.mockMvc.perform(post("/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.error", is("Field (inn) can't be: null")));
    }

    /** Проверка генерации ошибки при обновлении полей организации без указания всех полей
     * @throws Exception
     */
    @Test
    public void whenUpdateOrganizationWithoutAnyFieldsThenCheck4xx() throws Exception {
        this.mockMvc.perform(post("/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    /** Преобразование объекта в строку Json
     * @param obj объект для преобразования
     * @return строка Json
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

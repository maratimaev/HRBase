package ru.bellintegrator.hrbase.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.core.Is.is;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.bellintegrator.hrbase.exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.service.OrganizationService;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.Wrapper;

@RunWith(SpringRunner.class)
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService service;

    private OrganizationView organizationView;

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

    @Test
    public void whenGetOrganizationByExistingIdThenCheckJsonData() throws Exception {
        Wrapper<OrganizationView> wrapper = new Wrapper<>(organizationView);
        when(service.findOrganizationById("1")).thenReturn(wrapper);

        this.mockMvc.perform(get("/organization/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data[0].id", is(0)))
                .andExpect(jsonPath("$.data[0].name", is("ООО «Пример»")))
                .andExpect(jsonPath("$.data[0].fullName", is("Общество с ограниченной ответственностью «Пример»")))
                .andExpect(jsonPath("$.data[0].inn", is("1234567891")))
                .andExpect(jsonPath("$.data[0].kpp", is("123456789")));
    }

    @Test
    public void whenGetOrganizationByFakeIdThenCheck500() throws Exception {
        when(service.findOrganizationById("10")).thenThrow(ThereIsNoSuchOrganization.class);
        this.mockMvc.perform(get("/organization/{id}",10))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void whenGetOrganizationListThenCheckJsonData() throws Exception {
        Wrapper<OrganizationView> wrapper = new Wrapper<>(organizationView);
        when(service.getOrganizations("ООО «Пример»", "1234567891", "false")).thenReturn(wrapper);

        this.mockMvc.perform(post("/organization/list")
                .param("name", "ООО «Пример»")
                .param("inn", "1234567891")
                .param("isActive", "false")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(0)))
                .andExpect(jsonPath("$.data[0].name", is("ООО «Пример»")))
                .andExpect(jsonPath("$.data[0].isActive", is(false)));
    }

    @Test
    public void whenGetOrganizationListByFakeNameThenCheck500() throws Exception {
        when(service.getOrganizations("ООО «Error»", "1234567891", "false")).thenThrow(ThereIsNoSuchOrganization.class);
        this.mockMvc.perform(post("/organization/list")
                .param("name", "ООО «Error»")
                .param("inn", "1234567891")
                .param("isActive", "false")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("There is no such organization")));
    }

    @Test
    public void whenSaveOrganizationThenCheckSuccess() throws Exception {
        this.organizationView.setName("ООО «New»");
        doNothing().when(service).saveOrganization(organizationView);

        this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(organizationView)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void whenSaveOrganizationWithoutNameThenCheckError() throws Exception {
        String inputJson = new StringBuilder()
                .append("{\"fullName\":\"Общество с ограниченной ответственностью «Пример»\",")
                .append("\"inn\":\"1234567891\",")
                .append("\"kpp\":\"123456789\",")
                .append("\"address\":\"124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1\"}")
                .toString();

        this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.error", is("wrong validation of required fields: name, fullName, inn, kpp, address")));
    }

    @Test
    public void whenSaveOrganizationWithoutAnyFieldsThenCheck4xx() throws Exception {
        this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenUpdateOrganizationThenCheckSuccess() throws Exception {
        this.organizationView.setName("ООО «New»");
        this.organizationView.setFullName("Общество с ограниченной ответственностью «New»");
        doNothing().when(service).updateOrganization(organizationView);

        this.mockMvc.perform(post("/organization/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(organizationView)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void whenUpdateOrganizationWithoutInnThenCheckError() throws Exception {
        String inputJson = new StringBuilder()
                .append("{\"id\":0,")
                .append("\"fullName\":\"Общество с ограниченной ответственностью «Пример»\",")
                .append("\"name\":\"ООО New\",")
                .append("\"kpp\":\"123456789\",")
                .append("\"address\":\"124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1\"}")
                .toString();

        this.mockMvc.perform(post("/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.error", is("wrong validation of required fields: id, name, fullName, inn, kpp, address")));
    }

    @Test
    public void whenUpdateOrganizationWithoutAnyFieldsThenCheck4xx() throws Exception {
        this.mockMvc.perform(post("/organization/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

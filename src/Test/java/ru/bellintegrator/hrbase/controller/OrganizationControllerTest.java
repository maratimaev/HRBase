package ru.bellintegrator.hrbase.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.core.Is.is;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.bellintegrator.hrbase.exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.service.OrganizationService;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.Wrapper;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService service;

    @Test
    public void whenGetOrganizationByExistingIdThenCheckJsonData() throws Exception {
        OrganizationView organizationView = new OrganizationView(0,
                "ООО «Пример»",
                "Общество с ограниченной ответственностью «Пример»",
                "1234567891",
                "123456789",
                "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1"
        );
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

        verify(service, times(1)).findOrganizationById("1");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenGetOrganizationByFakeIdThenCheck500() throws Exception {
        when(service.findOrganizationById("10")).thenThrow(ThereIsNoSuchOrganization.class);
        this.mockMvc.perform(get("/organization/{id}",10))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void whenGetOrganizationListThenCheckJsonData() throws Exception {
        OrganizationView organizationView = new OrganizationView(0,
                "ООО «Пример»",
                "Общество с ограниченной ответственностью «Пример»",
                "1234567891",
                "123456789",
                "124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1"
        );
//        OrganizationView organizationView2 = new OrganizationView(1,
//                "ООО «Дельта»",
//                "Общество с ограниченной ответственностью «Дельта»",
//                "6634545891",
//                "883459989",
//                "344365 г.Москва, ул. Цюрупы, д.5, офис 66"
//        );
        Wrapper<OrganizationView> wrapper = new Wrapper<>(organizationView);
        when(service.getOrganizations("ООО «Пример»", "1234567891", "false")).thenReturn(wrapper);

        this.mockMvc.perform(post("/organization/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(organizationView)))
                .andExpect(status().isOk());
//
//                .andExpect(jsonPath("$.data[0].id", is(0)))
//                .andExpect(jsonPath("$.data[0].name", is("ООО «Пример»")))
//                .andExpect(jsonPath("$.data[0].isActive", is("false"))));

        verify(service, times(1)).getOrganizations("ООО «Пример»", "1234567891", "false");
        verifyNoMoreInteractions(service);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

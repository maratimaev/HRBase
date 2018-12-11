package ru.bellintegrator.hrbase.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.service.OrganizationService;
import ru.bellintegrator.hrbase.service.OrganizationServiceImpl;
import ru.bellintegrator.hrbase.view.OrganizationView;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)

public class OrganizationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OrganizationService organizationServiceMock;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void controller() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/organization/{id}";
        URI uri = new URI(baseUrl);
        ResponseEntity<OrganizationView> result = restTemplate.getForEntity(uri, OrganizationView.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        //Assert.assertEquals(true, result.getBody().contains(OrganizationView.class));

    }
}

package com.myonlineshopping.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myonlineshopping.model.Account;
import com.myonlineshopping.repository.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerPersistenceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ICustomerRepository customerRepo;

    @Autowired
    ObjectMapper objMapper;




    @Test
    void givenGetCuentasDeUnUsuario_whenValidOwnerId_thenGetCuentas () throws Exception {

        mvc.perform(get("/account/owner/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(4)));
    }

    @Test
    void givenGetCuentasDeUnUsuario_whenInValidOwnerId_then404 () throws Exception {

        mvc.perform(get("/account/owner/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenPostCuenta_whenValidOwnerId_thenCreated () throws Exception {

        Account acc = new Account (null, "Personal", 1000, "2024-03-08", customerRepo.findById(1L).get());

        String accountJson = objMapper.writeValueAsString(acc);

        mvc.perform(post("/account/owner/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenPostCuenta_whenInvalidOwnerId_thenBadRequest () throws Exception {

        Account acc = new Account (null, "Personal", 1000, "2024-03-08", customerRepo.findById(8L).get());

        String accountJson = objMapper.writeValueAsString(acc);

        mvc.perform(post("/account/owner/8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }




}

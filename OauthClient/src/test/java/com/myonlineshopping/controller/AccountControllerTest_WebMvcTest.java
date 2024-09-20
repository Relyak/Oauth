package com.myonlineshopping.controller;
import com.myonlineshopping.exceptions.AccountNotfoundException;
import com.myonlineshopping.services.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest_WebMvcTest {

    @MockBean
    private IAccountService iAccountService;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }


    @Test
    public void givenAccountWhenDeleteByCustomerThenIsOK() throws Exception {
        Long accountId = 1L;

        doNothing().when(iAccountService).deleteAccountById(accountId);

        mockMvc.perform(delete("/account/{accountId}", accountId))
                .andExpect(status().isNoContent())
                .andExpect(header().doesNotExist(HttpHeaders.LOCATION));
    }

    @Test
    public void givenAccountWhenDeleteByCustomerThenIdIsNotAllowed() throws Exception {
        Long notAllowedId = 9L;

        Mockito.doThrow(new AccountNotfoundException(notAllowedId)).when(iAccountService).deleteAccountById(notAllowedId);

        mockMvc.perform(delete("/account/{accountId}", notAllowedId))
                .andExpect(status().isNotFound());

    }


    @Test
    public void givenValidOwnerIdAndAmount_whenCheckPrestamo_thenHigherNotAllowed() throws Exception {
        Long ownerId = 1L;
        Integer cantidad = 277000;

        when(iAccountService.checkPrestamo(cantidad, 80000)).thenReturn(false);

        mockMvc.perform(get("/account/owner/{ownerId}/prestamo/{cantidad}", ownerId, cantidad))
                .andExpect(status().isOk())
                .andExpect(content().string("No valido"));

    }
}
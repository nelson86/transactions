package com.narabel.transactions.controller.v1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/db/seeds/transactions_insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetSumOfTransactionsByIdControllerTest {

    private static final String URL_BASE = "/transactions/sum/%s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSumOfTransactionsWithChildren() throws Exception {
        String url = String.format(URL_BASE, 1L);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.sum", Matchers.is(90d))
                );
    }

    @Test
    void getSumOfTransactionsWithoutChildren() throws Exception {
        String url = String.format(URL_BASE, 4L);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.sum", Matchers.is(50d))
                );
    }

    @Test
    void shouldExitByNotFoundException(){
        Long idInvalid = 100L;
        String url = String.format(URL_BASE, idInvalid);

        Exception exception = Assertions.assertThrows( Exception.class, () -> this.mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        ));

        String expectedMessage = String.format("Not found Transaction for ID: %s", idInvalid);
        String actualMessage = exception.getCause().getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
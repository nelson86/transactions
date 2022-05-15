package com.narabel.transactions.controller.v1;

import org.hamcrest.Matchers;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetTransactionByTypeControllerTest {

    private static final String URL_BASE = "/transactions/types/%s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnEmptyListByType() throws Exception {
        String url = String.format(URL_BASE, "test0");

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$").isEmpty()
                );
    }

    @Test
    @Sql(scripts = "/db/seeds/transactions_insert.sql")
    void shouldListIdsByType() throws Exception {
        String url = String.format(URL_BASE, "test");

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$").isNotEmpty(),
                        MockMvcResultMatchers.jsonPath("$", Matchers.is(List.of(1,3,4)))
                );
    }

}
package com.narabel.transactions.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.narabel.transactions.dto.TransactionRequest;
import com.narabel.transactions.entity.Transaction;
import com.narabel.transactions.service.v1.FindTransactionByIdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateTransactionControllerTest {

    private final String URL_BASE = "/transactions/%s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Faker faker;

    @Autowired
    private FindTransactionByIdService findTransactionByIdService;

    @BeforeEach
    void setUp() {
        this.faker = new Faker();
    }

    private TransactionRequest buildFaker() {
        return TransactionRequest.builder()
                .amount(faker.number().randomDouble(2,1,100))
                .type("test")
                .parentId(faker.number().randomNumber(2, true))
                .build();
    }

    @Test
    void shouldBySaveForTransactionWithoutParent() throws Exception {
        Long transactionId = faker.number().randomNumber();
        String url = String.format(URL_BASE, transactionId);
        TransactionRequest request = buildFaker();
        request.setParentId(null);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        Transaction transactionBD = findTransactionByIdService.findById(transactionId);
        Assertions.assertEquals(transactionId, transactionBD.getId());
        Assertions.assertEquals(request.getAmount(), transactionBD.getAmount());
        Assertions.assertEquals(request.getType(), transactionBD.getType());
        Assertions.assertNull(transactionBD.getParent());
    }

    @Test
    @Sql(scripts = "/db/seeds/transactions_insert.sql")
    void shouldBySaveForTransactionWithParent() throws Exception {
        Long transactionId = faker.number().randomNumber(2, true);
        String url = String.format(URL_BASE, transactionId);
        TransactionRequest request = buildFaker();
        request.setParentId(1L);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        Transaction transactionBD = findTransactionByIdService.findById(transactionId);
        Assertions.assertEquals(transactionId, transactionBD.getId());
        Assertions.assertEquals(request.getAmount(), transactionBD.getAmount());
        Assertions.assertEquals(request.getType(), transactionBD.getType());
        Assertions.assertEquals(request.getParentId(), transactionBD.getParent().getId());
    }

    @Test
    void shouldExitTransactionNotFoundException(){
        Long transactionId = faker.number().randomNumber();
        Long parentId = faker.number().randomNumber();
        String url = String.format(URL_BASE, transactionId);
        TransactionRequest request = buildFaker();
        request.setParentId(parentId);

        Exception exception = Assertions.assertThrows( Exception.class, () -> this.mockMvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(objectMapper.writeValueAsString(request))
                ));

        String expectedMessage = String.format("Not found Transaction for ID: %s", parentId);
        String actualMessage = exception.getCause().getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
package com.narabel.transactions.controller.v1;

import com.narabel.transactions.service.v1.GetTransactionByTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/transactions")
public class GetTransactionByTypeController {

    @Autowired
    private GetTransactionByTypeService service;

    @GetMapping("/types/{type}")
    public List<Long> createTransaction(
            @PathVariable String type
    ) {
        log.debug("Filter By Type: {}", type);
        return service.findByType(type);
    }
}

package com.narabel.transactions.service.v1;

import java.util.List;

public interface GetTransactionByTypeService {

    List<Long> findByType(String type);
}

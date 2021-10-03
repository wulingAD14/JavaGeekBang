package org.example.bank1;

import org.dromara.hmily.annotation.Hmily;

public interface AccountService {

    //转账
    @Hmily
    public void swap (String accountNo, String currency, Double amount);


}

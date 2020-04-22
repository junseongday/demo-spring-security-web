package com.junseong.demospringsecurityweb.form;

import com.junseong.demospringsecurityweb.account.Account;
import com.junseong.demospringsecurityweb.account.AccountContext;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    public void dashboard() {
        Account account = AccountContext.getAccout();
        System.out.println("==========================");
        System.out.println(account.getUsername());
    }

}

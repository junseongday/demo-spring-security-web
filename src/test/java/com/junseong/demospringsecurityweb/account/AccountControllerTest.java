package com.junseong.demospringsecurityweb.account;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;


    @Test
    @WithAnonymousUser
    public void ant_index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "junseong", roles = "user")
    public void ant_index_user() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser // coustom anotation
    public void index_user() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void index_admin() throws Exception {
        mockMvc.perform(get("/")
                .with(user("admin").roles("ADMIN"))// 이러한 유저가 있다고 mocking(가정)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin")
                .with(user("junseong").roles("USER"))// 이러한 유저가 있다고 mocking(가정)
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin")
                .with(user("junseong").roles("ADMIN"))// 이러한 유저가 있다고 mocking(가정)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void login_success() throws Exception {
        String username = "junseong";
        String password = "123";
        Account account = createUser(username, password);

        mockMvc.perform(formLogin().user(username).password(password))
                .andExpect(authenticated());

    }

    @Test
    @Transactional
    public void login_fail() throws Exception {
        String username = "junseong";
        String password = "123";
        Account account = createUser(username, password);

        mockMvc.perform(formLogin().user(username).password("12345"))
                .andExpect(unauthenticated());

    }

    private Account createUser(String username, String password) {
        final Account account = Account.builder()
                .username(username)
                .password(password)
                .role("USER")
                .build();
        return accountService.createNew(account);
    }

    @Test
    @WithUser
    public void dashboard_user() throws Exception{
        mockMvc.perform(get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
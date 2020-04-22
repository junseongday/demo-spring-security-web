package com.junseong.demospringsecurityweb.account;

import lombok.*;

import javax.persistence.*;

@Entity @NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;


    public void encodePasswod() {
        this.password = "{noop}" + this.password;
    }
}

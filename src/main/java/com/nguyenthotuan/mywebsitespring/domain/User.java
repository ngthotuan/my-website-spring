package com.nguyenthotuan.mywebsitespring.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String name;
    private String role = "USER";
    private boolean isEnabled = true;

}

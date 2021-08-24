package com.nguyenthotuan.mywebsitespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    @Length(min = 10, max = 50, message = "Tên từ 10-50 ký tự")
    private String name;
    @Email(message = "Email không hợp lệ")
    private String email;
    @Length(min = 8, max = 20, message = "Mật khẩu từ 8-20 ký tự")
    private String password;
}

package com.nguyenthotuan.mywebsitespring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private String template;
    private Map<String, Object> props;
}

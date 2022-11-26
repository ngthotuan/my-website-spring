package com.nguyenthotuan.mywebsitespring.service;


import com.nguyenthotuan.mywebsitespring.model.MailDto;

import javax.mail.MessagingException;

public interface EmailSenderService {
    void sendEmail(MailDto mailDto) throws MessagingException;
}

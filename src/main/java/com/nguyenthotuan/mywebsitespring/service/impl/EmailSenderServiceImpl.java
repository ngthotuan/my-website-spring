package com.nguyenthotuan.mywebsitespring.service.impl;


import com.nguyenthotuan.mywebsitespring.model.MailDto;
import com.nguyenthotuan.mywebsitespring.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(MailDto mailDto) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        String html = getHtmlContent(mailDto);

        helper.setTo(mailDto.getTo());
        helper.setFrom(mailDto.getFrom());
        helper.setSubject(mailDto.getSubject());
        helper.setText(html, true);
        log.info("Sending email to {} with subject {}", mailDto.getTo(), mailDto.getSubject());
        emailSender.send(message);
    }

    private String getHtmlContent(MailDto mailDto) {
        Context context = new Context();
        context.setVariables(mailDto.getProps());
        return templateEngine.process(mailDto.getTemplate(), context);
    }
}

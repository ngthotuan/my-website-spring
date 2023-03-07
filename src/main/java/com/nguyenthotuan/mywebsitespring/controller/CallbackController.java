package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.config.AppProperties;
import com.nguyenthotuan.mywebsitespring.model.MailDto;
import com.nguyenthotuan.mywebsitespring.model.VNGCardCallbackDataDto;
import com.nguyenthotuan.mywebsitespring.service.EmailSenderService;
import com.nguyenthotuan.mywebsitespring.util.AES;
import com.nguyenthotuan.mywebsitespring.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tuannt7 on 07/03/2023
 */
@Controller
@RequestMapping("callback")
@RequiredArgsConstructor
@Slf4j
public class CallbackController {

    private final EmailSenderService emailSenderService;
    private final AppProperties appProperties;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${app.cb.vng.client-key}")
    private String clientKey;
    @Value("${app.cb.vng.encrypt-data-key}")
    private String encryptDataKey;
    @Value("${app.cb.vng.encrypt-card-key}")
    private String encryptCardKey;

    @Value("${app.cb.vng.send-card-to-email}")
    private String sendCardToEmail;

    @PostMapping("/vng/evoucher")
    public ResponseEntity<?> callbackVNGCard(@RequestParam String data) {
        Map<String, String> resp = new HashMap<>();
        resp.put("returnCode", "1");
        resp.put("returnMessage", "Success");
        try {
            log.debug("vng card callback raw: {}", data);
            String decryptData = AES.decrypt(data, encryptDataKey);
            VNGCardCallbackDataDto dataCallback = CommonUtil.jsonStringToObject(decryptData, VNGCardCallbackDataDto.class);
            // TODO: check sig, ts
            log.info("vng card callback: " + dataCallback);
            for (VNGCardCallbackDataDto.CardCallbackInfo card : dataCallback.getListCards()) {
                card.setSerialNumber(AES.decrypt(card.getSerialNumber(), encryptCardKey));
                card.setPassword(AES.decrypt(card.getPassword(), encryptCardKey));
            }
            Map<String, Object> properties = new HashMap<>();
            properties.put("appProps", appProperties);
            properties.put("cbData", dataCallback);
            MailDto mailDto = MailDto.builder()
                    .from(String.format("%s <%s>", appProperties.getEmailSenderName(), from))
                    .to(sendCardToEmail)
                    .subject(String.format("EMS OrderNumber - %s", dataCallback.getOrderNumber()))
                    .template("email/card")
                    .props(properties)
                    .build();
            emailSenderService.sendEmail(mailDto);
        } catch (Exception e) {
            log.error("vng card callback error: " + e.getMessage(), e);
            resp.put("returnCode", "500");
            resp.put("returnMessage", e.getMessage());
        }

        return ResponseEntity.ok(resp);
    }
}

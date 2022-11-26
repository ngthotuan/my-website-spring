package com.nguyenthotuan.mywebsitespring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "CONTACT_MESSAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    private String name;
    private String email;
    @Column(columnDefinition = "TEXT")
    private String message;
    private String ip;
    private LocalDateTime time;

    public String getTime() {
        if (time != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return time.format(formatter);
        }
        return "";
    }
}

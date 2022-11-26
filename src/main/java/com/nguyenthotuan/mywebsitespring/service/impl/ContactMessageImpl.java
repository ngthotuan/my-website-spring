package com.nguyenthotuan.mywebsitespring.service.impl;

import com.nguyenthotuan.mywebsitespring.domain.ContactMessage;
import com.nguyenthotuan.mywebsitespring.repository.ContactMessageRepository;
import com.nguyenthotuan.mywebsitespring.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageImpl implements ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    @Override
    public void saveContactMessage(ContactMessage contactMessage) {
        contactMessageRepository.save(contactMessage);
    }
}

package com.nguyenthotuan.mywebsitespring.service.impl;

import com.nguyenthotuan.mywebsitespring.domain.Subscriber;
import com.nguyenthotuan.mywebsitespring.repository.SubscriberRepository;
import com.nguyenthotuan.mywebsitespring.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberRepository subscriberRepository;
    @Override
    public Subscriber findByEmail(String email) {
        return subscriberRepository.findByEmail(email);
    }

    @Override
    public void saveSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }
}

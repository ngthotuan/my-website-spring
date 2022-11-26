package com.nguyenthotuan.mywebsitespring.service;


import com.nguyenthotuan.mywebsitespring.domain.Subscriber;

public interface SubscriberService {
    Subscriber findByEmail(String email);

    void saveSubscriber(Subscriber subscriber);
}

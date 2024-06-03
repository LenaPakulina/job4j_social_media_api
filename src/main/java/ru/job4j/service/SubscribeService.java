package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.model.Post;
import ru.job4j.model.Subscriber;
import ru.job4j.model.User;
import ru.job4j.repository.FileRepository;
import ru.job4j.repository.SubscriberRepository;

@Service
public class SubscribeService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public void deleteSubscription(User from, User to) {
        subscriberRepository.deleteSubscription(from.getId(), to.getId());
    }

    public Subscriber createSubscription(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }
}

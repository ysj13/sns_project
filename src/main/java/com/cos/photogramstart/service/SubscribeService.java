package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(Long fromUserId, Long toUserId) {
        try {
            subscribeRepository.nSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 팔로우 하였습니다");
        }
    }

    @Transactional
    public void unSubscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.nUnSubscribe(fromUserId, toUserId);
    }
}

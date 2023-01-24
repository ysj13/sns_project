package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void saveLikes(int imageId, int principalId) {
        likesRepository.nLikes(imageId, principalId);
    }

    @Transactional
    public void deleteLikes(int imageId, int principalId) {
        likesRepository.nUnLikes(imageId, principalId);
    }

}

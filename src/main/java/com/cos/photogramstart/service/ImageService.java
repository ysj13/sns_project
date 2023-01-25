package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public Page<Image> imageStory(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.nStory(principalId, pageable);

        // image 에 좋아요 정보 담기
        images.forEach((image -> {
            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like -> {
                // 해당 이미지에 좋아요를 누른 사람들을 찾아서 현재 로그인한 사람이 좋아요 누른것인지 비교
                if(like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            }));
        }));

        return images;
    }

    @Transactional(readOnly = true)
    public List<Image> popularImage() {
        return imageRepository.nPopular();
    }
}

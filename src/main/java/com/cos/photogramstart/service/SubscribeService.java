package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager entityManager;

    @Transactional
    public void subscribe(int fromUserId, int toUserId) {
        try {
            subscribeRepository.nSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 팔로우 하였습니다");
        }
    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId) {
        subscribeRepository.nUnSubscribe(fromUserId, toUserId);
    }

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select u.id, u.username, u.profileImageUrl, ");
        sb.append("if((select 1 from subscribe where fromUserId = ? and toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if((? = u.id), 1, 0) equalUserState ");
        sb.append("from user u join subscribe s ");
        sb.append("on u.id = s.toUserId ");
        sb.append("where s.fromUserId = ?");

        // 물음표 매핑
        Query query = entityManager.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 결과물 dto로 리턴
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = resultMapper.list(query, SubscribeDto.class);

        return subscribeDtos;
    }
}

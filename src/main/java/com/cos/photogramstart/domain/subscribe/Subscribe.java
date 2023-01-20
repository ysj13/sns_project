package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "subscribe_uk",
            columnNames = {"fromUserId", "toUserId"}
            )
        }
    )
@Entity
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;  // 팔로우하는 유저

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;    // 팔로우받는 유저

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}

package com.github.accountmanagementproject.repository.account.socialIds;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_ids")
public class SocialId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser myUser;

    @Column(unique = true, nullable = false)
    private String socialId;

    private boolean activeStatus;

    private LocalDateTime connectAt;

}

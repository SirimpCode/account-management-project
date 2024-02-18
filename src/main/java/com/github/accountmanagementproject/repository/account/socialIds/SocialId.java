package com.github.accountmanagementproject.repository.account.socialIds;

import com.github.accountmanagementproject.repository.account.users.User;
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
    private User user;

    @Column(unique = true, nullable = false)
    private String socialId;

    private boolean activeStatus;

    private LocalDateTime connectAt;

}

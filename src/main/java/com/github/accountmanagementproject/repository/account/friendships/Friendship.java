package com.github.accountmanagementproject.repository.account.friendships;


import com.github.accountmanagementproject.repository.account.users.MyUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private MyUser myUser1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private MyUser myUser2;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}

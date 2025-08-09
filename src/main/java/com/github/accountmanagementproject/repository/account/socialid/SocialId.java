package com.github.accountmanagementproject.repository.account.socialid;

import com.github.accountmanagementproject.repository.account.user.MyUser;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_ids")
@DynamicInsert
@Getter
@EqualsAndHashCode(of = "socialIdPk")
public class SocialId {

   @EmbeddedId
   private SocialIdPk socialIdPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser myUser;
    @Column(name = "active_status")
    private boolean activeStatus;

    private LocalDateTime connectAt;

    public static SocialId ofSocialIdPkAndMyUser(SocialIdPk socialIdPk, MyUser myUser){
        SocialId socialId = new SocialId();
        socialId.socialIdPk = socialIdPk;
        socialId.myUser = myUser;

        socialId.connectAt = LocalDateTime.now();
        return socialId;
    }
    public void socialConnectSetting(MyUser myUser){
        this.connectAt = LocalDateTime.now();
        this.myUser = myUser;
    }
    public void socialConnectSetting(){
        this.connectAt = LocalDateTime.now();
        this.activeStatus = true;
    }

}

package com.github.accountmanagementproject.repository.account.socialIds;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_ids")
@DynamicInsert
@NoArgsConstructor
@Getter
public class SocialId {

   @EmbeddedId
   private SocialIdPk socialIdPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser myUser;

    private LocalDateTime connectAt;


    public SocialId(String socialId, OAuthProvider provider, MyUser myUser) {
        this.socialIdPk = new SocialIdPk(socialId, provider);
        this.myUser = myUser;
    }
    public void socialConnectSetting(){
        this.connectAt = LocalDateTime.now();
    }

}

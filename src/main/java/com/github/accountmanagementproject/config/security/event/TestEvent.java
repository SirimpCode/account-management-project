package com.github.accountmanagementproject.config.security.event;

import com.github.accountmanagementproject.repository.account.users.MyUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class TestEvent extends ApplicationEvent {
    private final MyUser myUser;
    public TestEvent(Object source, MyUser myUser) {
        super(source);
        this.myUser = myUser;
    }

}

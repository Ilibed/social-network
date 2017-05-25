package com.ilibed.user;

import org.springframework.beans.factory.annotation.Value;

public interface UserActivity {

    @Value("#{target.activityName}")
    String getActivityName();

    @Value("#{target.activityValue}")
    String getActivityValue();
}

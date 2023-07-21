package com.woottipong.leavemanagementdemo.model;

import java.util.Date;

public record LeaveRecordModel (
        String id,
        String userId,
        Date startDate,
        Double duration,
        Date createTime,
        Date updateTime
) { }
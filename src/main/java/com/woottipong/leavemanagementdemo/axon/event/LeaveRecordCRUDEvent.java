package com.woottipong.leavemanagementdemo.axon.event;

import com.woottipong.leavemanagementdemo.model.CRUD;

import java.util.Date;

public record LeaveRecordCRUDEvent(
        String eventId,
        String id,
        String userId,
        Date startDate,
        Double duration,
        Date createTime,
        Date updateTime,
        CRUD crud
){}

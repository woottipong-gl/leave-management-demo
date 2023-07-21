package com.woottipong.leavemanagementdemo.axon.command;

import com.woottipong.leavemanagementdemo.model.CRUD;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

public record LeaveRecordCRUDCommand (
    @TargetAggregateIdentifier
    String eventId,
    String id,
    String userId,
    Date startDate,
    Double duration,
    Date createTime,
    Date updateTime,
    CRUD crud
){}

package com.woottipong.leavemanagementdemo.axon.command;

public record LeaveRecordQueryCommand(
        String id,
        String userId
) {}

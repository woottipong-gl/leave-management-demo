package com.woottipong.leavemanagementdemo.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRecordRepository extends JpaRepository<LeaveRecordEntity, String> {

    List<LeaveRecordEntity> findByUserId(String userId);
}

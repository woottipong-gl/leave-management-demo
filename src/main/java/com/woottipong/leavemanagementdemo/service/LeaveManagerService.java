package com.woottipong.leavemanagementdemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woottipong.leavemanagementdemo.AppUtils;
import com.woottipong.leavemanagementdemo.entity.LeaveRecordEntity;
import com.woottipong.leavemanagementdemo.entity.LeaveRecordRepository;
import com.woottipong.leavemanagementdemo.exception.DataNotFoundException;
import com.woottipong.leavemanagementdemo.exception.UserInputException;
import com.woottipong.leavemanagementdemo.model.LeaveRecordModel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LeaveManagerService {

    private final ObjectMapper mapper = AppUtils.getObjectMapper();

    private final LeaveRecordRepository leaveRecordRepository;
    private final UserManagementService userManagementService;

    public LeaveManagerService(LeaveRecordRepository leaveRecordRepository, UserManagementService userManagementService) {
        this.leaveRecordRepository = leaveRecordRepository;
        this.userManagementService = userManagementService;
    }

    public List<LeaveRecordModel> getLeaveRecords(String userId) {
        List<LeaveRecordEntity> list;
        if(StringUtils.hasText(userId)) {
            list = leaveRecordRepository.findByUserId(userId);
        } else {
            list = leaveRecordRepository.findAll();
        }
        List<LeaveRecordModel> result = list.stream()
                .map(o -> mapper.convertValue(o, LeaveRecordModel.class))
                .sorted(Comparator.comparing(o -> o.startDate()))
                .toList();
        return result;
    }

    @Transactional
    public LeaveRecordModel addLeaveRecord(LeaveRecordModel record) {
        return addLeaveRecord(null, record);
    }

    @Transactional
    public LeaveRecordModel addLeaveRecord(String id, LeaveRecordModel record) {
        Date now = new Date();
        LeaveRecordEntity entity = mapper.convertValue(record, LeaveRecordEntity.class);
        entity.setId(id != null ? id : UUID.randomUUID().toString());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        leaveRecordRepository.save(entity);
        LeaveRecordModel result = mapper.convertValue(entity, LeaveRecordModel.class);
        return result;
    }

    @Transactional
    public LeaveRecordModel updateLeaveRecord(String id, LeaveRecordModel record) {
        //validation
        if(id == null || record == null) {
            throw new UserInputException("Parameters should not be null");
        }
        LeaveRecordEntity entity = leaveRecordRepository.findById(id).orElse(null);
        if(entity == null) {
            throw new DataNotFoundException("Leave Record not found!");
        }
        entity.setStartDate(record.startDate());
        entity.setDuration(record.duration());
        entity.setUpdateTime(new Date());

        leaveRecordRepository.save(entity);

        LeaveRecordModel result = mapper.convertValue(entity, LeaveRecordModel.class);
        return result;
    }

    public LeaveRecordModel deleteLeaveRecord(String id) {
        LeaveRecordEntity entity = leaveRecordRepository.findById(id).orElse(null);
        if(entity == null) {
            throw new DataNotFoundException("Leave Record not found!");
        }
        LeaveRecordModel result = mapper.convertValue(entity, LeaveRecordModel.class);
        leaveRecordRepository.delete(entity);
        return result;
    }

    public LeaveRecordModel getLeaveRecord(String id) {
        LeaveRecordEntity entity = leaveRecordRepository.findById(id).orElse(null);
        if(entity == null) {
            throw new DataNotFoundException("Leave Record not found!");
        }
        LeaveRecordModel result = mapper.convertValue(entity, LeaveRecordModel.class);
        return result;
    }
}

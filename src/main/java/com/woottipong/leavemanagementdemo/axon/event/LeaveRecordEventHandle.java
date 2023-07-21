package com.woottipong.leavemanagementdemo.axon.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woottipong.leavemanagementdemo.AppUtils;
import com.woottipong.leavemanagementdemo.axon.command.LeaveRecordQueryCommand;
import com.woottipong.leavemanagementdemo.model.CRUD;
import com.woottipong.leavemanagementdemo.model.LeaveRecordModel;
import com.woottipong.leavemanagementdemo.service.LeaveManagerService;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LeaveRecordEventHandle {

    private final ObjectMapper mapper = AppUtils.getObjectMapper();

    private final LeaveManagerService leaveManagerService;

    public LeaveRecordEventHandle(LeaveManagerService leaveManagerService) {
        this.leaveManagerService = leaveManagerService;
    }

    @QueryHandler
    public List<LeaveRecordModel> readHandle(LeaveRecordQueryCommand command) {
        List<LeaveRecordModel> result = null;
        if(command.id() == null) {
            result = leaveManagerService.getLeaveRecords(command.userId());
        } else {
            result = Arrays.asList(leaveManagerService.getLeaveRecord(command.id()));
        }
        return result;
    }

    @EventHandler
    public void on(LeaveRecordCRUDEvent event) {
        LeaveRecordModel model = mapper.convertValue(event, LeaveRecordModel.class);
        if(event.crud() == CRUD.CREATE) {
            leaveManagerService.addLeaveRecord(model.id(), model);
        } else if(event.crud() == CRUD.UPDATE) {
            leaveManagerService.updateLeaveRecord(model.id(), model);
        } else if(event.crud() == CRUD.DELETE) {
            leaveManagerService.deleteLeaveRecord(model.id());
        }
    }

    @ExceptionHandler
    public void errorHandle(Exception exception) throws Exception {
        throw exception;
    }
}

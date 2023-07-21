package com.woottipong.leavemanagementdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woottipong.leavemanagementdemo.AppUtils;
import com.woottipong.leavemanagementdemo.axon.command.LeaveRecordCRUDCommand;
import com.woottipong.leavemanagementdemo.axon.command.LeaveRecordQueryCommand;
import com.woottipong.leavemanagementdemo.model.CRUD;
import com.woottipong.leavemanagementdemo.model.LeaveRecordModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api-axon/leave-records")
public class LeaveRecordAxonController {

    private final ObjectMapper mapper = AppUtils.getObjectMapper();

    private final QueryGateway queryGateway;

    private final CommandGateway commandGateway;

    public LeaveRecordAxonController(QueryGateway queryGateway, CommandGateway commandGateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }

    @GetMapping
    public List<LeaveRecordModel> getRecords(@RequestParam(value = "userId", required = false) String userId) {
        LeaveRecordQueryCommand queryCommand = new LeaveRecordQueryCommand(null, userId);
        List<LeaveRecordModel> result = queryGateway
                .query(queryCommand, ResponseTypes.multipleInstancesOf(LeaveRecordModel.class))
                .join();
        return result;
    }

    @GetMapping("/{id}")
    public LeaveRecordModel getRecord(@PathVariable("id") String id) {
        LeaveRecordQueryCommand queryCommand = new LeaveRecordQueryCommand(id, null);
        LeaveRecordModel result = queryGateway
                .query(queryCommand, ResponseTypes.multipleInstancesOf(LeaveRecordModel.class))
                .join()
                .get(0);
        return result;
    }

    @PostMapping
    public String postRecord(@RequestBody LeaveRecordModel record) throws Exception {
        Date now = new Date();
        String newId = UUID.randomUUID().toString();
        LeaveRecordCRUDCommand command = new LeaveRecordCRUDCommand(
                newId,
                newId,
                record.userId(),
                record.startDate(),
                record.duration(),
                now,
                now,
                CRUD.CREATE
        );
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{id}")
    public String putRecord(@PathVariable("id") String id, @RequestBody LeaveRecordModel record) throws Exception {
        Date now = new Date();
        LeaveRecordCRUDCommand command = new LeaveRecordCRUDCommand(
                UUID.randomUUID().toString(),
                id,
                record.userId(),
                record.startDate(),
                record.duration(),
                null,
                now,
                CRUD.UPDATE
        );
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable("id") String id) throws Exception {
        Date now = new Date();
        LeaveRecordCRUDCommand command = new LeaveRecordCRUDCommand(
                UUID.randomUUID().toString(),
                id,
                null,
                null,
                null,
                null,
                now,
                CRUD.DELETE
        );
        return commandGateway.sendAndWait(command);
    }
}

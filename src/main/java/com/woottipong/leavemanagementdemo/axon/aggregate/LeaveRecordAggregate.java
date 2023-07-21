package com.woottipong.leavemanagementdemo.axon.aggregate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woottipong.leavemanagementdemo.AppUtils;
import com.woottipong.leavemanagementdemo.axon.event.LeaveRecordCRUDEvent;
import com.woottipong.leavemanagementdemo.model.CRUD;
import com.woottipong.leavemanagementdemo.axon.command.LeaveRecordCRUDCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class LeaveRecordAggregate {

    private static final ObjectMapper mapper = AppUtils.getObjectMapper();

    @AggregateIdentifier
    private String eventId;
    private String id;
    private String userId;
    private Date startDate;
    private Double duration;
    private Date createTime;
    private Date updateTime;
    private CRUD crud;

    @CommandHandler
    public LeaveRecordAggregate(LeaveRecordCRUDCommand command) {
        LeaveRecordCRUDEvent event = mapper.convertValue(command, LeaveRecordCRUDEvent.class);
        AggregateLifecycle.apply(event);
    }

    public LeaveRecordAggregate() {

    }

    @EventSourcingHandler
    public void on(LeaveRecordCRUDEvent event) {
        this.eventId = event.eventId();
        this.id = event.id();
        this.userId = event.userId();
        this.startDate = event.startDate();
        this.duration = event.duration();
        this.createTime = event.createTime();
        this.updateTime = event.updateTime();
        this.crud = event.crud();
    }
}

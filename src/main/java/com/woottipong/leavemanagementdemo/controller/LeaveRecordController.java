package com.woottipong.leavemanagementdemo.controller;

import com.woottipong.leavemanagementdemo.model.LeaveRecordModel;
import com.woottipong.leavemanagementdemo.service.LeaveManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-records")
public class LeaveRecordController {

    private final LeaveManagerService leaveManagerService;

    public LeaveRecordController(LeaveManagerService leaveManagerService) {
        this.leaveManagerService = leaveManagerService;
    }

    @GetMapping
    public List<LeaveRecordModel> getRecords(@RequestParam(value = "userId", required = false) String userId) {
        return leaveManagerService.getLeaveRecords(userId);
    }

    @GetMapping("/{id}")
    public LeaveRecordModel getRecord(@PathVariable("id") String id) {
        return leaveManagerService.getLeaveRecord(id);
    }

    @PostMapping
    public LeaveRecordModel postRecord(@RequestBody LeaveRecordModel record) {
        return leaveManagerService.addLeaveRecord(record);
    }

    @PutMapping("/{id}")
    public LeaveRecordModel putRecord(@PathVariable("id") String id, @RequestBody LeaveRecordModel record) {
        return leaveManagerService.updateLeaveRecord(id, record);
    }

    @DeleteMapping("/{id}")
    public LeaveRecordModel putRecord(@PathVariable("id") String id) throws Exception {
        return leaveManagerService.deleteLeaveRecord(id);
    }
}

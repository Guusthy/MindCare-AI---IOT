package com.mindcare.mindcareapi.controller;

import com.mindcare.mindcareapi.model.Checkin;
import com.mindcare.mindcareapi.service.CheckinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
@CrossOrigin(origins = "*") 
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    
    @PostMapping
    public Checkin createCheckin(@RequestParam Long userId, @RequestBody String text) {
        return checkinService.processCheckin(userId, text);
    }

    
    @GetMapping
    public List<Checkin> listCheckins(@RequestParam Long userId) {
        return checkinService.findByUser(userId);
    }

    
    @GetMapping("/{id}")
    public Checkin findOne(@PathVariable Long id) {
        return checkinService.findById(id);
    }

    
    @GetMapping("/stats")
    public Object stats() {
        return checkinService.getStats();
    }
}

package com._DSF.je.Controller;

import com._DSF.je.Service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeUserToCourse(@RequestParam Long userId, @RequestParam Long courseId) {
        subscriptionService.subscribeUserToCourse(userId, courseId);
        return ResponseEntity.ok("User subscribed to course successfully");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeUserFromCourse(@RequestParam Long userId, @RequestParam Long courseId) {
        subscriptionService.unsubscribeUserFromCourse(userId, courseId);
        return ResponseEntity.ok("User unsubscribed from course successfully");
    }
}

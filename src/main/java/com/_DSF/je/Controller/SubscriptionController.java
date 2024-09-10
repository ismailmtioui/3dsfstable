package com._DSF.je.Controller;

import com._DSF.je.Entity.User;
import com._DSF.je.Service.SubscriptionService;
import com._DSF.je.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;

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
    @GetMapping("/students/{courseId}")
    public ResponseEntity<List<User>> getStudentsSubscribedToCourse(@PathVariable Long courseId) {
        List<User> students = userService.findStudentsByCourseId(courseId); // Using the service class method
        return ResponseEntity.ok(students);
    }
}

package com._DSF.je.Service;

import com._DSF.je.Entity.User;
import com._DSF.je.Entity.Course;
import com._DSF.je.Repository.UserRepository;
import com._DSF.je.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void subscribeUserToCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        user.getCourses().add(course);
        course.getStudents().add(user);

        userRepository.save(user);
        courseRepository.save(course);
    }

    public void unsubscribeUserFromCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        user.getCourses().remove(course);
        course.getStudents().remove(user);

        userRepository.save(user);
        courseRepository.save(course);
    }
}

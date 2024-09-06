package com._DSF.je.Service;

import com._DSF.je.Entity.Order;
import com._DSF.je.Entity.CarteItem;
import com._DSF.je.Entity.Course;
import com._DSF.je.Entity.User;
import com._DSF.je.Repository.OrderRepository;
import com._DSF.je.Repository.CarteItemRepository;
import com._DSF.je.Repository.CourseRepository;
import com._DSF.je.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarteItemRepository carteItemRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order createOrder(Long userId, List<Long> courseIds) {
        // Fetch the user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);  // Set the fetched user entity
        order.setDateCreation(new Date());

        double totalAmount = 0;

        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            // Check if the course is already in the order
            if (carteItemRepository.findByOrderIdAndCourseId(order.getId(), courseId).isEmpty()) {
                CarteItem carteItem = new CarteItem();
                carteItem.setCourse(course);
                carteItem.setPrice(course.getPrice());
                carteItem.setOrder(order);
                carteItemRepository.save(carteItem);
                totalAmount += course.getPrice();
            } else {
                throw new RuntimeException("Course already in order");
            }
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}

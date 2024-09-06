package com._DSF.je.Service;

import com._DSF.je.Entity.Course;
import com._DSF.je.Entity.Favoris;
import com._DSF.je.Entity.User;
import com._DSF.je.Repository.CourseRepository;
import com._DSF.je.Repository.FavorisRepository;
import com._DSF.je.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavorisService {

    @Autowired
    private FavorisRepository favorisRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Favoris with courseId and userId
    public Favoris createFavoris(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Favoris favoris = new Favoris();
        favoris.setCourse(course);
        favoris.setUser(user);

        return favorisRepository.save(favoris);
    }

    // Get all Favoris
    public List<Favoris> getAllFavoris() {
        return favorisRepository.findAll();
    }

    // Get Favoris by ID
    public Favoris getFavorisById(Long id) {
        return favorisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favoris not found with id: " + id));
    }

    // Update Favoris
    public Favoris updateFavoris(Long id, Long courseId, Long userId) {
        Favoris favoris = getFavorisById(id);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        favoris.setCourse(course);
        favoris.setUser(user);

        return favorisRepository.save(favoris);
    }

    // Delete Favoris
    public void deleteFavoris(Long id) {
        favorisRepository.deleteById(id);
    }
    public List<Favoris> getFavorisByUserId(Long userId) {
        return favorisRepository.findByUserId(userId);
    }
}

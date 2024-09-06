package com._DSF.je.Controller;

import com._DSF.je.Entity.Favoris;
import com._DSF.je.Service.FavorisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/favoris")
public class FavorisController {

    @Autowired
    private FavorisService favorisService;

    // Create a new Favoris
    @PostMapping
    public ResponseEntity<Favoris> createFavoris(@RequestParam Long courseId, @RequestParam Long userId) {
        Favoris favoris = favorisService.createFavoris(courseId, userId);
        return new ResponseEntity<>(favoris, HttpStatus.CREATED);
    }

    // Get all Favoris
    @GetMapping
    public ResponseEntity<List<Favoris>> getAllFavoris() {
        return ResponseEntity.ok(favorisService.getAllFavoris());
    }

    // Get Favoris by ID
    @GetMapping("/{id}")
    public ResponseEntity<Favoris> getFavorisById(@PathVariable Long id) {
        return ResponseEntity.ok(favorisService.getFavorisById(id));
    }

    // Update Favoris
    @PutMapping("/{id}")
    public ResponseEntity<Favoris> updateFavoris(@PathVariable Long id, @RequestParam Long courseId, @RequestParam Long userId) {
        Favoris updatedFavoris = favorisService.updateFavoris(id, courseId, userId);
        return ResponseEntity.ok(updatedFavoris);
    }

    // Delete Favoris
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoris(@PathVariable Long id) {
        favorisService.deleteFavoris(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favoris>> getFavorisByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(favorisService.getFavorisByUserId(userId));
    }
}

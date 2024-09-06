package com._DSF.je.Repository;

import com._DSF.je.Entity.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavorisRepository extends JpaRepository<Favoris, Long> {
    List<Favoris> findByUserId(Long userId);
    List<Favoris> findByCourseId(Long courseId);
}

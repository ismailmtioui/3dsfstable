package com._DSF.je.Repository;

import com._DSF.je.Entity.CarteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarteItemRepository extends JpaRepository<CarteItem, Long> {
    List<CarteItem> findByOrderId(Long orderId);
    @Query("SELECT ci FROM CarteItem ci WHERE ci.order.id = :orderId AND ci.course.id = :courseId")
    Optional<CarteItem> findByOrderIdAndCourseId(@Param("orderId") Long orderId, @Param("courseId") Long courseId);
}

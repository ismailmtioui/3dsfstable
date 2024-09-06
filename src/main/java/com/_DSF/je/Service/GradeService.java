package com._DSF.je.Service;

import com._DSF.je.Entity.Grade;
import com._DSF.je.Repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Optional<Grade> getGradeById(Long id) {
        return gradeRepository.findById(id);
    }

    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Long id, Grade updatedGrade) {
        if (gradeRepository.existsById(id)) {
            updatedGrade.setId(id);
            return gradeRepository.save(updatedGrade);
        }
        return null;
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}

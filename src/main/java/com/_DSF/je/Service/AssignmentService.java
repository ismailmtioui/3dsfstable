package com._DSF.je.Service;

import com._DSF.je.Entity.Assignment;
import com._DSF.je.Repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Long id, Assignment updatedAssignment) {
        if (assignmentRepository.existsById(id)) {
            updatedAssignment.setId(id);
            return assignmentRepository.save(updatedAssignment);
        }
        return null;
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}

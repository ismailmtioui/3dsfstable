package com._DSF.je.Service;

import com._DSF.je.Entity.FAQ;
import com._DSF.je.Repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FAQService {

    @Autowired
    private FAQRepository faqRepository;

    // Retrieve all FAQs for a specific course
    public List<FAQ> getAllFAQsByCourseId(Long courseId) {
        return faqRepository.findAll()
                .stream()
                .filter(faq -> faq.getCourse().getId().equals(courseId))
                .toList();
    }

    // Create a new FAQ
    public FAQ createFAQ(FAQ faq) {
        return faqRepository.save(faq);
    }

    // Update an existing FAQ
    public FAQ updateFAQ(Long id, FAQ faqDetails) {
        Optional<FAQ> optionalFAQ = faqRepository.findById(id);
        if (optionalFAQ.isPresent()) {
            FAQ existingFAQ = optionalFAQ.get();
            existingFAQ.setQuestion(faqDetails.getQuestion());
            existingFAQ.setAnswer(faqDetails.getAnswer());
            return faqRepository.save(existingFAQ);
        } else {
            // Handle FAQ not found, you can throw an exception or return null
            return null;
        }
    }

    // Delete an FAQ by its ID
    public void deleteFAQ(Long id) {
        faqRepository.deleteById(id);
    }
}

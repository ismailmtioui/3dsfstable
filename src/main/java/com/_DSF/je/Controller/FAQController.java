package com._DSF.je.Controller;

import com._DSF.je.Entity.FAQ;
import com._DSF.je.Service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {

    @Autowired
    private FAQService faqService;

    // Retrieve all FAQs for a specific course
    @GetMapping("/course/{courseId}")
    public List<FAQ> getFAQsByCourse(@PathVariable Long courseId) {
        return faqService.getAllFAQsByCourseId(courseId);
    }

    // Create a new FAQ
    @PostMapping
    public FAQ createFAQ(@RequestBody FAQ faq) {
        return faqService.createFAQ(faq);
    }

    // Update an existing FAQ
    @PutMapping("/{id}")
    public FAQ updateFAQ(@PathVariable Long id, @RequestBody FAQ faqDetails) {
        return faqService.updateFAQ(id, faqDetails);
    }

    // Delete an FAQ by its ID
    @DeleteMapping("/{id}")
    public void deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
    }
}

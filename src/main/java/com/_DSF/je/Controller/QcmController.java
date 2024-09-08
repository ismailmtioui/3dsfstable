package com._DSF.je.Controller;

import com._DSF.je.Entity.Qcm;
import com._DSF.je.Service.QcmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/qcm")
public class QcmController {

    private final QcmService qcmService;

    public QcmController(QcmService qcmService) {
        this.qcmService = qcmService;
    }

    @PostMapping("/check/{qcmId}")
    public ResponseEntity<String> checkAnswer(@PathVariable Long qcmId, @RequestBody String answer) {
        try {
            boolean isCorrect = qcmService.checkAnswer(qcmId, answer);
            if (isCorrect) {
                return ResponseEntity.ok("Correct answer! Grade increased.");
            } else {
                return ResponseEntity.ok("Incorrect answer.");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }


    @PostMapping
    public ResponseEntity<Qcm> createQCM(@RequestBody Qcm qcm) {
        Qcm createdQCM = qcmService.createQCM(qcm);
        return ResponseEntity.ok(createdQCM);
    }
}


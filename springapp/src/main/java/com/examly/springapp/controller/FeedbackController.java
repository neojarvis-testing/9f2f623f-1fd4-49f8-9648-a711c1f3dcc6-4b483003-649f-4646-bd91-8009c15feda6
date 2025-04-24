package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.service.FeedbackService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback createFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.status(201).body(createFeedback);
    }

    @GetMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int id) {
        Feedback getFeedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.status(200).body(getFeedback);
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.status(200).body(feedbacks);
    }

    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable int userId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return ResponseEntity.status(200).body(feedbacks);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Boolean> deleteFeedback(@PathVariable int id) {
        boolean isDeleted = feedbackService.deleteFeedback(id);
        return ResponseEntity.status(200).body(isDeleted);
    }
}
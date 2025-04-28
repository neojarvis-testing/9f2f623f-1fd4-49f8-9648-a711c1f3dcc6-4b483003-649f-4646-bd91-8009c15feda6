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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/feedback")
@Tag(name = "Feedback", description = "Endpoints for managing feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Operation(summary = "Create a new feedback", description = "Allows a user to create a new feedback entry.")
    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback createFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.status(201).body(createFeedback);
    }
    @Operation(summary = "Get feedback by ID", description = "Allows an admin to retrieve feedback details by its ID.")
    @GetMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int id) {
        Feedback getFeedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.status(200).body(getFeedback);
    }

    @Operation(summary = "Get all feedbacks", description = "Allows an admin to retrieve all feedback entries.")
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.status(200).body(feedbacks);
    }

    @Operation(summary = "Get feedbacks by user ID", description = "Allows a user to retrieve all feedback entries they have created.")
    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable int userId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return ResponseEntity.status(200).body(feedbacks);
    }

    @Operation(summary = "Delete feedback by ID", description = "Allows an admin or user to delete a feedback entry by its ID.")
    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Boolean> deleteFeedback(@PathVariable int id) {
        boolean isDeleted = feedbackService.deleteFeedback(id);
        return ResponseEntity.status(200).body(isDeleted);
    }
}

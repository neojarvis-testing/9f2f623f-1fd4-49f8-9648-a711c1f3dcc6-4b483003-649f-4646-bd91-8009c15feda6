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
import com.examly.springapp.model.Food;
import com.examly.springapp.service.FeedbackService;
import com.examly.springapp.service.FeedbackServiceImpl;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<Feedback>createFeedback(@RequestBody Feedback feedback){
        Feedback createFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.status(201).body(createFeedback);
    }

    @GetMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Feedback>getFeedbackById(@PathVariable int id){
        Feedback getFeedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.status(200).body(getFeedback);
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<?>getAllFeedbacks(){
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        if(feedbacks.isEmpty()){
            return ResponseEntity.status(400).body(null);
        }
        else{
            return ResponseEntity.status(200).body(feedbacks);
        }
    }

    @GetMapping("/user/{userId}")
    @RolesAllowed("USER")
    public ResponseEntity<?>getFeedbacksByUserId(@PathVariable int userId){
        List<Feedback> list = feedbackService.getFeedbacksByUserId(userId);
        if(list.isEmpty()){
            return ResponseEntity.status(404).body(null);
        }
        else{
            return ResponseEntity.status(200).body(list);
        }
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Boolean> deleteFeedback(@PathVariable int id){
        boolean isDeleted = feedbackService.deleteFeedback(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(isDeleted);
        }
        else{
            return ResponseEntity.status(404).body(null);
        }
    }
}

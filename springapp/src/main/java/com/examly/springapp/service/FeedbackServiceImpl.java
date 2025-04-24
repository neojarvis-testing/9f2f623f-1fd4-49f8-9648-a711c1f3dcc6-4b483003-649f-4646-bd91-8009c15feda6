package com.examly.springapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.repository.FeedbackRepo;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepo feedbackRepo;

    @Override
    public Feedback createFeedback(Feedback feedback) {
        if (feedback == null || feedback.getFeedbackText() == null || feedback.getFeedbackText().isEmpty()) {
            throw new InvalidInputException("Feedback content cannot be null or empty.");
        }
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new InvalidInputException("Feedback rating must be between 1 and 5.");
        }
        if (feedback.getDate() != null && feedback.getDate().isAfter(LocalDate.now())) {
            throw new InvalidInputException("Feedback date cannot be in the future.");
        }
        if (feedback.getUser() == null) {
            throw new InvalidInputException("Feedback must have an associated user.");
        }
        if (feedback.getFood() == null) {
            throw new InvalidInputException("Feedback must be associated with a food item.");
        }
        return feedbackRepo.save(feedback);
    }

    @Override
    public Feedback getFeedbackById(int id) {
        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        if (feedback == null) {
            throw new ResourceNotFoundException("Feedback not found with ID: " + id);
        }
        return feedback;
    }
    
    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepo.findAll();
        if (feedbacks.isEmpty()) {
            throw new ResourceNotFoundException("No feedbacks available.");
        }
        return feedbacks;
    } 
    
    @Override
    public boolean deleteFeedback(int id) {
        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        if (feedback == null) {
            throw new ResourceNotFoundException("Feedback not found with ID: " + id);
        }
        feedbackRepo.delete(feedback);
        return true;
    }
    
    @Override
    public List<Feedback> getFeedbacksByUserId(int userId) {
        List<Feedback> feedbacksList = feedbackRepo.findByUserId(userId);
        if (feedbacksList == null || feedbacksList.isEmpty()) {
            throw new ResourceNotFoundException("No feedbacks found for User ID: " + userId);
        }
        return feedbacksList;
    }
}
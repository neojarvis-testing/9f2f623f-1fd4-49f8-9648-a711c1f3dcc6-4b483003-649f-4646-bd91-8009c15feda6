package com.examly.springapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.InvalidInputException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.repository.FeedbackRepo;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    FeedbackRepo feedbackRepo;

    @Override
    public Feedback createFeedback(Feedback feedback) {
        logger.info("Creating feedback: {}", feedback);
        if (feedback == null || feedback.getFeedbackText() == null || feedback.getFeedbackText().isEmpty()) {
            logger.error("Invalid feedback content: {}", feedback);
            throw new InvalidInputException("Feedback content cannot be null or empty.");
        }
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            logger.error("Invalid feedback rating: {}", feedback.getRating());
            throw new InvalidInputException("Feedback rating must be between 1 and 5.");
        }
        if (feedback.getDate() != null && feedback.getDate().isAfter(LocalDate.now())) {
            logger.error("Invalid feedback date: {}", feedback.getDate());
            throw new InvalidInputException("Feedback date cannot be in the future.");
        }
        if (feedback.getUser() == null) {
            logger.error("Feedback without user: {}", feedback);
            throw new InvalidInputException("Feedback must have an associated user.");
        }
        if (feedback.getFood() == null) {
            logger.error("Feedback without food item: {}", feedback);
            throw new InvalidInputException("Feedback must be associated with a food item.");
        }
        Feedback savedFeedback = feedbackRepo.save(feedback);
        logger.info("Feedback created successfully: {}", savedFeedback);
        return savedFeedback;
    }

    @Override
    public Feedback getFeedbackById(int id) {
        logger.info("Fetching feedback by ID: {}", id);
        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        if (feedback == null) {
            logger.error("Feedback not found with ID: {}", id);
            throw new ResourceNotFoundException("Feedback not found with ID: " + id);
        }
        logger.info("Feedback found: {}", feedback);
        return feedback;
    }
    
    @Override
    public List<Feedback> getAllFeedbacks() {
        logger.info("Fetching all feedbacks");
        List<Feedback> feedbacks = feedbackRepo.findAll();
        if (feedbacks.isEmpty()) {
            logger.error("No feedbacks available");
            throw new ResourceNotFoundException("No feedbacks available.");
        }
        logger.info("Feedbacks found: {}", feedbacks);
        return feedbacks;
    } 
    
    @Override
    public boolean deleteFeedback(int id) {
        logger.info("Deleting feedback with ID: {}", id);
        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        if (feedback == null) {
            logger.error("Feedback not found with ID: {}", id);
            throw new ResourceNotFoundException("Feedback not found with ID: " + id);
        }
        feedbackRepo.delete(feedback);
        logger.info("Feedback deleted successfully with ID: {}", id);
        return true;
    }
    
    @Override
    public List<Feedback> getFeedbacksByUserId(int userId) {
        logger.info("Fetching feedbacks by User ID: {}", userId);
        List<Feedback> feedbacksList = feedbackRepo.findByUserId(userId);
        if (feedbacksList == null || feedbacksList.isEmpty()) {
            logger.error("No feedbacks found for User ID: {}", userId);
            throw new ResourceNotFoundException("No feedbacks found for User ID: " + userId);
        }
        logger.info("Feedbacks found for User ID {}: {}", userId, feedbacksList);
        return feedbacksList;
    }
}

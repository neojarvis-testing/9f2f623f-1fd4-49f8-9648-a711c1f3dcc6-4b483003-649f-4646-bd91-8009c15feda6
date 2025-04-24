package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.repository.FeedbackRepo;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackRepo feedbackRepo;
    @Override
    public Feedback createFeedback(Feedback feedback) {
        feedback = feedbackRepo.save(feedback);
        return feedback;
    }

    @Override
    public Feedback getFeedbackById(int id) {
        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        if(feedback==null){
            return null;
        }
        return feedbackRepo.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback>list = feedbackRepo.findAll();
        return list;
    }

    @Override
    public boolean deleteFeedback(int id) {
        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        if(feedback==null){
            return false;
        }
        feedbackRepo.delete(feedback);
        return true;
    }

    @Override
    public Feedback getFeedbacksByUserId(int userId) {
        Feedback feedback = feedbackRepo.findByUserId(userId);
        if(feedback==null){
            return null;
        }
        return feedbackRepo.save(feedback);
    }
    
}

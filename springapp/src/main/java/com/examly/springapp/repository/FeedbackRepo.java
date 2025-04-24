package com.examly.springapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examly.springapp.model.Feedback;


public interface FeedbackRepo extends JpaRepository<Feedback,Integer>{
    @Query("select f from Feedback f where f.user.userId=?1")
    public Feedback findByUserId(int userId);

}

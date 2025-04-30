import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model'; // Assume a Feedback model exists
import { FeedbackService } from 'src/app/services/feedback.service'; // Service to fetch feedback

@Component({
  selector: 'app-userviewfeedback',
  templateUrl: './userviewfeedback.component.html',
  styleUrls: ['./userviewfeedback.component.css']
})
export class UserviewfeedbackComponent implements OnInit {
  feedbackList: Feedback[] = [];
  errorMessage: string = '';

  constructor(private feedbackService: FeedbackService) { }

  ngOnInit(): void {
    this.loadFeedback();
  }

  loadFeedback(): void {
    this.feedbackService.getALllFeedbacksByUserId().subscribe({
      next: (data) => {
        this.feedbackList = data;
      },
      error: () => {
        this.errorMessage = 'Failed to load feedback';
      }
    });
  }
}
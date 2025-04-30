import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model'; // Assume a Feedback model exists
import { FeedbackService } from 'src/app/services/feedback.service'; // Service to fetch feedback

@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {
  feedbackList: Feedback[] = [];
  errorMessage: string = '';

  constructor(private feedbackService: FeedbackService) { }

  ngOnInit(): void {
    this.loadFeedback();
  }

  loadFeedback(): void {
    this.feedbackService.getFeedbacks().subscribe({
      next: (data) => {
        this.feedbackList = data;
      },
      error: () => {
        this.errorMessage = 'Failed to load feedback';
      }
    });
  }
}
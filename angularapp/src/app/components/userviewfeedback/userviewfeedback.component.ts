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
  userId: number = 0; // Define userId property to hold the ID
  isLoading = true;
  constructor(private feedbackService: FeedbackService) { }

  ngOnInit(): void {
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      this.userId = parseInt(storedUserId, 10);
      this.loadFeedback(this.userId);
    } else {
      this.errorMessage = 'User ID not found. Cannot load feedback.';
      this.isLoading = false; // Hide spinner if no user ID
    }
  }

  loadFeedback(userId: number): void {
    this.isLoading = true; // Show spinner
    this.feedbackService.getAllFeedbacksByUserId(userId).subscribe({
      next: (data) => {
        this.feedbackList = data;
        this.isLoading = false; // Hide spinner after data is loaded
      },
      error: () => {
        this.errorMessage = 'Failed to load feedback';
        this.isLoading = false; // Hide spinner on error
      }
    });
  }
}
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

  constructor(private feedbackService: FeedbackService) { }

  ngOnInit(): void {
    const storedUserId = localStorage.getItem('userId'); // Fetch userId from localStorage or another source
    if (storedUserId) {
      this.userId = parseInt(storedUserId, 10); // Convert string to number
      this.loadFeedback(this.userId); // Pass userId to loadFeedback
    } else {
      this.errorMessage = 'User ID not found. Cannot load feedback.';
    }
  }

  loadFeedback(userId: number): void {
    this.feedbackService.getAllFeedbacksByUserId(userId).subscribe({
      next: (data) => {
        this.feedbackList = data;
      },
      error: () => {
        this.errorMessage = 'Failed to load feedback';
      }
    });
  }
}
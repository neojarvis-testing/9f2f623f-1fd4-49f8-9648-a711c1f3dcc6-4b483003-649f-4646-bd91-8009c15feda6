import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model'; // Feedback model
import { FeedbackService } from 'src/app/services/feedback.service'; // Service to fetch feedback

@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {
  feedbackList: Feedback[] = [];
  errorMessage: string = '';
  isLoading = false;

  showDialog = false; // ✅ For feedback deleted popup
  showDeleteConfirmation = false; // ✅ For delete confirmation popup
  confirmFeedbackId: number = 0; // Store the ID of the feedback to delete

  constructor(private feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.loadFeedback();
  }

  loadFeedback(): void {
    this.feedbackService.getFeedbacks().subscribe({
      next: (data) => {
        this.feedbackList = data;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load feedback';
        this.isLoading = false;
      }
    });
  }

  // Trigger delete confirmation dialog
  confirmDelete(feedbackId: number): void {
    this.confirmFeedbackId = feedbackId;
    this.showDeleteConfirmation = true; // Show confirmation dialog
  }

  // Cancel deletion and close the confirmation dialog
  cancelDelete(): void {
    this.showDeleteConfirmation = false;
  }

  // Perform deletion if confirmed
  deleteFeedback(feedbackId: number): void {
    this.isLoading = true;
    this.feedbackService.deleteFeedback(feedbackId).subscribe(
      () => {
        // Remove the feedback from the list without reloading the page
        this.feedbackList = this.feedbackList.filter(
          feedback => feedback.feedbackId !== feedbackId
        );
        this.isLoading = false;
        this.showDeleteConfirmation = false; // Close the confirmation dialog
        this.showDialog = true; // Show success popup
      },
      (error) => {
        console.error('Error deleting feedback', error);
        this.isLoading = false;
        this.errorMessage = 'Failed to delete feedback.';
        this.showDeleteConfirmation = false; // Close the confirmation dialog
      }
    );
  }

  // Close the success popup
  onDialogConfirm(): void {
    this.showDialog = false;
  }
}
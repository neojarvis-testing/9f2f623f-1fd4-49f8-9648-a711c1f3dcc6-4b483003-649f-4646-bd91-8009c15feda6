import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

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

  constructor(private readonly feedbackService: FeedbackService) {}

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

  deleteFeedback(feedbackId: number): void {
    this.isLoading = true;
    this.feedbackService.deleteFeedback(feedbackId).subscribe(
      () => {
        this.feedbackList = this.feedbackList.filter(
          feedback => feedback.feedbackId !== feedbackId
        );
        this.isLoading = false;
        this.showDialog = true; // ✅ Show custom success popup
      },
      (error) => {
        console.error('Error deleting feedback', error);
        this.isLoading = false;
        this.errorMessage = 'Failed to delete feedback.';
      }
    );
  }

  onDialogConfirm(): void {
    this.showDialog = false;
  }
}
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
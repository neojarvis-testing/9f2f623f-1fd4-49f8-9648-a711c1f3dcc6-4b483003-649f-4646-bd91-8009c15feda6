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
  paginatedFeedbacks: Feedback[] = [];
  errorMessage: string = '';
  isLoading = false;
  showDialog = false;

  // ✅ Pagination properties
  pageSize = 5; // Number of feedbacks per page
  currentPage = 1;
  totalPages: number[] = [];

  constructor(private readonly feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.loadFeedback();
  }

  loadFeedback(): void {
    this.feedbackService.getFeedbacks().subscribe({
      next: (data) => {
        this.feedbackList = data;
        this.setupPagination();
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load feedback';
        this.isLoading = false;
      }
    });
  }

  setupPagination(): void {
    this.totalPages = Array(Math.ceil(this.feedbackList.length / this.pageSize))
      .fill(0)
      .map((_, i) => i + 1);
    this.updatePaginatedFeedback();
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages.length) {
      this.currentPage = page;
      this.updatePaginatedFeedback();
    }
  }

  updatePaginatedFeedback(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedFeedbacks = this.feedbackList.slice(startIndex, startIndex + this.pageSize);
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
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Food } from 'src/app/models/food.model'; // Assume a food model exists
import { FoodService } from 'src/app/services/food.service'; // Service to fetch food list

@Component({
  selector: 'app-useraddfeedback',
  templateUrl: './useraddfeedback.component.html',
  styleUrls: ['./useraddfeedback.component.css']
})
export class UseraddfeedbackComponent implements OnInit {
  feedbackForm!: FormGroup;
  foodList: Food[] = [];
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private foodService: FoodService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.loadFoodList();
  }

  initializeForm(): void {
    this.feedbackForm = this.fb.group({
      food: ['', Validators.required],
      feedback: ['', [Validators.required, Validators.minLength(10)]],
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]]
    });
  }

  loadFoodList(): void {
    this.foodService.getAllFoods().subscribe({
      next: (data) => this.foodList = data,
      error: () => this.errorMessage = 'Failed to load food list'
    });
  }

  onSubmit(): void {
    if (this.feedbackForm.valid) {
      const feedbackData = this.feedbackForm.value;
      console.log('Feedback Submitted:', feedbackData);
      this.successMessage = 'Thank you for your feedback!';
      this.feedbackForm.reset();
    } else {
      this.errorMessage = 'Please fill out all required fields correctly!';
    }
  }
}
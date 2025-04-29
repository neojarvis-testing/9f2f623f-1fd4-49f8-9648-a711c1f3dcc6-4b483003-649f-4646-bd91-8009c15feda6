import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Feedback } from 'src/app/models/feedback.model';
import { Food } from 'src/app/models/food.model';
import { AuthService } from 'src/app/services/auth.service';
import { FeedbackService } from 'src/app/services/feedback.service';
import { FoodService } from 'src/app/services/food.service';

@Component({
  selector: 'app-useraddfeedback',
  templateUrl: './useraddfeedback.component.html',
  styleUrls: ['./useraddfeedback.component.css']
})
export class UseraddfeedbackComponent implements OnInit {
  feedbackForm:FormGroup
  foods:Food[]=[]
  getFoods(){
    this.foodService.getAllFoods().subscribe((data)=>{
      this.foods=data
    })  
  }
    constructor(private service:FeedbackService,private foodService:FoodService,private fb:FormBuilder) { 
      
this.feedbackForm = this.fb.group({
  foodItem: ['', Validators.required],
  feedbackText: ['', Validators.required],
  rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]]
  });
  
    }
  onSubmit(){
      this.service.sendFeedback(this.feedbackForm.value).subscribe((data)=>{
        alert('feedback added successfully');
        })
  }

  ngOnInit(): void {
  }

}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-useraddfeedback',
  templateUrl: './useraddfeedback.component.html',
  styleUrls: ['./useraddfeedback.component.css']
})
export class UseraddfeedbackComponent implements OnInit {
  feedbackForm:FormGroup
  foodName:string[]
  feedbacks:Feedback[]=[]
  constructor(private service:FeedbackService,private fb:FormBuilder){
    this.feedbackForm=this.fb.group({
      foodItem:['',[Validators.required]],
      feedbackText:['',[Validators.required]],
      rating:['',[Validators.required]]
    })
   }
  feedback:Feedback
  onSubmit(){
    if(this.feedbackForm.valid){
      this.service.sendFeedback(this.feedbackForm.value).subscribe((data)=>{
        this.feedbacks.push(data)
      })
    }
  }

  ngOnInit(): void {
  }

}

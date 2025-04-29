import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-useraddfeedback',
  templateUrl: './useraddfeedback.component.html',
  styleUrls: ['./useraddfeedback.component.css']
})
export class UseraddfeedbackComponent implements OnInit {
  feedbacks:Feedback[]=[]
  foodName:
  constructor(private service:FeedbackService) { }


  onSubmit(form:NgForm){
    if(form.valid){
      this.service.sendFeedback(this.feedback).subscribe((data)=>{
        this.feedbacks.push(data)
      })
    }
    form.reset()
  }

  ngOnInit(): void {
  }

}

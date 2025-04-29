import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {
  feedbacks:Feedback[]=[]

  constructor(private service:FeedbackService) { }

  ngOnInit(): void {
    this.getFeedbacks()
  }

  getFeedbacks(){
    this.service.getFeedbacks().subscribe((data)=>{
      this.feedbacks=data
    })
  }

  deleteFeedback(id:number){
    this.service.deleteFeedback(id).subscribe((data)=>{
      this.getFeedbacks()
    })
  }
  
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Feedback } from '../models/feedback.model';
import { Observable } from 'rxjs';
import { APIURL } from '../constant/api_url';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  constructor(private http:HttpClient) { }

  sendFeedback(feedback:Feedback):Observable<Feedback>{
    return this.http.post<Feedback>(`${APIURL.APIurl}/feedback`,feedback);
  }

  getAllFeedbacksByUserId(userId: number): Observable<Feedback[]>{
    return this.http.get<Feedback[]>(`${APIURL.APIurl}/feedback/user/${userId}`);
  }

  deleteFeedback(feedbackId: number): Observable<void>{
    return this.http.get<void>(`${APIURL.APIurl}/feedback/${feedbackId}`);
  }

  getFeedbacks(): Observable<Feedback[]>{
    return this.http.get<Feedback[]>(`${APIURL.APIurl}/feedback`);
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { orders } from '../models/orders.model';
import { Observable } from 'rxjs';
import { APIURL } from '../constant/api_url';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }


  placeOrder(order: orders): Observable<orders> {
    return this.http.post<orders>(`${APIURL.APIurl}/orders`, order);
  }

  getAllOrders(): Observable<orders[]> {
    return this.http.get<orders[]>(`${APIURL.APIurl}/orders`);
  }

  getOrderById(orderId: number): Observable<orders> {
    return this.http.get<orders>(`${APIURL.APIurl}/orders/${orderId}`);
  }

  getAllOrdersByUserId(userId: number): Observable<orders[]> {
    return this.http.get<orders[]>(`${APIURL.APIurl}/orders/user/${userId}`);
  }

  updateOrder(orderId: number, orders: orders): Observable<orders> {
    return this.http.put<orders>(`${APIURL.APIurl}/orders/${orderId}`, orders);
  }

  deleteOrder(orderId: number): Observable<void> {
    return this.http.delete<void>(`${APIURL.APIurl}/orders/${orderId}`);
  }
}

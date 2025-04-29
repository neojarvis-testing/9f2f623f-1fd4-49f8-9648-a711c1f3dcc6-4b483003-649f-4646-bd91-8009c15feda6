import { Component, OnInit } from '@angular/core';
import { orders } from 'src/app/models/orders.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-uservieworders',
  templateUrl: './uservieworders.component.html',
  styleUrls: ['./uservieworders.component.css']
})
export class UserviewordersComponent implements OnInit {
  orders: orders[] = [];
  userId = 1; // Replace with actual user ID retrieved from authentication service
  showConfirmation = false;
  orderToDelete: orders | null = null;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.orderService.getAllOrdersByUserId(this.userId).subscribe(
      (data) => {
        this.orders = data;
      },
      (error) => {
        console.error('Error fetching user orders', error);
      }
    );
  }

  confirmDelete(order: orders): void {
    this.orderToDelete = order;
    this.showConfirmation = true;
  }

  deleteOrder(): void {
    if (this.orderToDelete) {
      this.orderService.deleteOrder(this.orderToDelete.orderId).subscribe(
        () => {
          this.orders = this.orders.filter(order => order.orderId !== this.orderToDelete?.orderId);
          this.showConfirmation = false;
          this.orderToDelete = null;
        },
        (error) => {
          console.error('Error deleting order', error);
        }
      );
    }
  }

  cancelDelete(): void {
    this.showConfirmation = false;
    this.orderToDelete = null;
  }
}



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
  userId: number = 0; 
  errorMessage: string = '';
  showConfirmation = false;
  orderToDelete: orders | null = null;
  isLoading = true;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      this.userId = parseInt(storedUserId);
      this.loadOrders();
    } else {
      this.errorMessage = 'User ID not found. Cannot load orders.';
      this.isLoading = false; // Hide spinner if no user ID
    }
  }

  loadOrders(): void {
    this.isLoading = true; // Show spinner while loading
    this.orderService.getAllOrdersByUserId(this.userId).subscribe({
      next: (data) => {
        this.orders = data;
        this.isLoading = false; // Hide spinner once data is loaded
      },
      error: () => {
        this.errorMessage = 'Failed to load order history';
        this.isLoading = false; // Hide spinner on error
      },
    });
  }


  confirmDelete(order: orders): void {
    this.orderToDelete = order;
    this.showConfirmation = true;
  }

  deleteOrder(): void {
    if (this.orderToDelete) {
      this.orderService.deleteOrder(this.orderToDelete.orderId).subscribe({
        next: () => {
          this.orders = this.orders.filter(order => order.orderId !== this.orderToDelete?.orderId);
          this.showConfirmation = false;
          this.orderToDelete = null;
        },
        error: () => {
          this.errorMessage = 'Failed to delete order';
        },
      });
    }
  }

  cancelDelete(): void {
    this.showConfirmation = false;
    this.orderToDelete = null;
  }
}
import { Component, OnInit } from '@angular/core';
import { Orders } from 'src/app/models/orders.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-uservieworders',
  templateUrl: './uservieworders.component.html',
  styleUrls: ['./uservieworders.component.css']
})
export class UserviewordersComponent implements OnInit {
  orders: Orders[] = [];
  userId: number = 0; 
  errorMessage: string = '';
  showConfirmation = false;
  orderToDelete: Orders | null = null;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      this.userId = parseInt(storedUserId);
      this.loadOrders();
    }
  }

  loadOrders(): void {
    this.orderService.getAllOrdersByUserId(this.userId).subscribe({
      next: (data) => {
        this.orders = data;
      },
      error: () => {
        this.errorMessage = 'Failed to load order history';
      },
    });
  }

  confirmDelete(order: Orders): void {
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
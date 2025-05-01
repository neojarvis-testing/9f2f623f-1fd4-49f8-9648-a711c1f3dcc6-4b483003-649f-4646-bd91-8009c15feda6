import { Component, OnInit } from '@angular/core';
import { Orders } from 'src/app/models/orders.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-adminvieworders',
  templateUrl: './adminvieworders.component.html',
  styleUrls: ['./adminvieworders.component.css']
})
export class AdminviewordersComponent implements OnInit {


  orders: Orders[] = [];
  selectedUser: any = null; // To store the selected user's profile
  isLoading = false;

  showSuccessPopup = false; // ✅ Used for order deleted success dialog

  constructor(private readonly orderService: OrderService) {}

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.isLoading = true; // Show spinner
    this.orderService.getAllOrders().subscribe(
      (data) => {
        this.orders = data.sort((a, b) => new Date(a.orderDate).getTime() - new Date(b.orderDate).getTime());
        this.isLoading = false; // Hide spinner
      },
      (error) => {
        this.isLoading = false; // Hide spinner on error
        console.error('Error fetching orders', error);
      }
    );
  }

  updateOrderStatus(orderId: number, status: string): void {
    const order = this.orders.find(o => o.orderId === orderId);

    if (order) {
      const updatedOrder: Orders = { ...order, orderStatus: status };
      this.isLoading = true; // Show spinner
      this.orderService.updateOrder(orderId, updatedOrder).subscribe(
        (updatedData) => {
          order.orderStatus = updatedData.orderStatus;
          this.isLoading = false; // Hide spinner
        },
        (error) => {
          this.isLoading = false; // Hide spinner
          console.error('Error updating order status', error);
        }
      );
    }
  }

  deleteOrder(orderId: number): void {
    this.isLoading = true; // Show spinner
    this.orderService.deleteOrder(orderId).subscribe(
      () => {
        this.orders = this.orders.filter(order => order.orderId !== orderId);
        this.isLoading = false; // Hide spinner
        this.showSuccessPopup = true; // ✅ Show smooth success dialog
      },
      (error) => {
        this.isLoading = false; // Hide spinner
        console.error('Error deleting order', error);
        alert('Failed to delete order.'); // This alert is for error only
      }
    );
  }

  closeSuccessPopup(): void {
    this.showSuccessPopup = false;
  }

  showUserProfile(userId: number): void {
    const order = this.orders.find(o => o.user.userId === userId);
    if (order) {
      this.selectedUser = order.user;
      console.log('Selected User:', this.selectedUser); // Debugging log
    } else {
      console.log('User not found for userId:', userId); // Debugging log
    }
  }

  closeUserProfile(): void {
    this.selectedUser = null;
  }
}

import { Component, OnInit } from '@angular/core';
import { orders } from 'src/app/models/orders.model';
import { OrderService } from 'src/app/services/order.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-adminvieworders',
  templateUrl: './adminvieworders.component.html',
  styleUrls: ['./adminvieworders.component.css']
})
export class AdminviewordersComponent implements OnInit {

  orders: orders[] = [];

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.orderService.getAllOrders().subscribe(
      (data) => {
        this.orders = data;
      },
      (error) => {
        console.error('Error fetching orders', error);
      }
    );
  }

  updateOrderStatus(orderId: number, status: string): void {
    const order = this.orders.find(o => o.orderId === orderId);
    
    if (order) {
      const updatedOrder: orders = { ...order, orderStatus: status }; // Ensure full order object is sent
      
      this.orderService.updateOrder(orderId, updatedOrder).subscribe(
        (updatedData) => {
          order.orderStatus = updatedData.orderStatus; // Update status in UI
        },
        (error) => {
          console.error('Error updating order status', error);
        }
      );
    }
  }

  deleteOrder(orderId: number): void {
    this.orderService.deleteOrder(orderId).subscribe(
      () => {
        this.orders = this.orders.filter(order => order.orderId !== orderId); // Remove the deleted order from the list
        alert('Order deleted successfully');
      },
      (error) => {
        console.error('Error deleting order', error);
        alert('Failed to delete order.');
      }
    );
  }
}

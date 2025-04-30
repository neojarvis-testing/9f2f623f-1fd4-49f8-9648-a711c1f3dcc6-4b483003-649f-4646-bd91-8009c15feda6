import { Component, OnInit } from '@angular/core';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';
import { OrderService } from 'src/app/services/order.service';
import { orders } from 'src/app/models/orders.model';
import { User } from 'src/app/models/user.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-userviewfood',
  templateUrl: './userviewfood.component.html',
  styleUrls: ['./userviewfood.component.css']
})
export class UserviewfoodComponent implements OnInit {

  foods: Food[] = []; // Array to store food items
  selectedFood: Food | null = null; // Store the food item to be ordered
  showOrderPopup: boolean = false; // Control order popup visibility
  orderQuantity: number = 1; // Default order quantity
  order: orders = {
    orderStatus: 'Pending',
    totalAmount: 0,
    quantity: 0,
    foodId: 0,
    orderDate: '',
    userId: 0, // Assuming a static user ID for now, replace with actual user ID if needed
    user: { userId: 0 } as User ,// Initialize the User object
    food: {foodId:0} as Food
  };

  constructor(
    private foodService: FoodService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllFoods();
  }

  getAllFoods(): void {
    this.foodService.getAllFoods().subscribe({
      next: (data) => {
        this.foods = data; // Assign fetched food items to the array
      },
      error: (err) => {
        console.error('Error fetching food items:', err);
        alert('Failed to fetch food items.');
      }
    });
  }

  openOrderPopup(food: Food): void {
    this.selectedFood = { ...food }; // Create a copy of the food object
    this.orderQuantity = 1; // Reset order quantity
    this.showOrderPopup = true; // Show the order popup
  }

  closeOrderPopup(): void {
    this.showOrderPopup = false;
    this.selectedFood = null; // Reset the selected food
  }

  confirmOrder(): void {
    if (this.selectedFood) {
      const userId = Number(localStorage.getItem('userId')); // Get the user ID from local storage
      this.order.foodId = this.selectedFood.foodId;
      this.order.quantity = this.orderQuantity;
      this.order.totalAmount = this.selectedFood.price * this.orderQuantity;
      this.order.orderDate = new Date().toISOString(); // System-generated order date
      this.order.userId = userId; // Set the user ID
      this.order.user.userId = userId; // Set the user ID in the User object
      this.order.food.foodId = this.selectedFood.foodId;

      this.orderService.placeOrder(this.order).subscribe(
        () => {
          alert('Order placed successfully');
          this.closeOrderPopup();
          this.router.navigate(['/userViewOrders']); // Navigate to order history or any other page
        },
        (error) => {
          console.error('Error placing order:', error);
          alert('Failed to place order.');
        }
      );
    }
  }
}

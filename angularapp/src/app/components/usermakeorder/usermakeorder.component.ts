import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-usermakeorder',
  templateUrl: './usermakeorder.component.html',
  styleUrls: ['./usermakeorder.component.css']
})
export class UsermakeorderComponent implements OnInit {
  orderForm: FormGroup;
  selectedFood: Food;
  totalAmount: number;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private foodService: FoodService,
    private ordersService: OrderService
  ) {}

  ngOnInit(): void {
    const foodId = 1; // Replace with actual food ID
    const userId = 1; // Replace with actual user ID

    this.foodService.getFoodById(foodId).subscribe(food => {
      this.selectedFood = food;
      this.totalAmount = food.price;

      this.orderForm = this.fb.group({
        foodId: [food.foodId],
        userId: [userId],
        quantity: [1],
        totalAmount: [this.totalAmount],
        orderStatus: ['Pending'],
        orderDate: [new Date().toISOString()]
      });

      this.orderForm.get('quantity').valueChanges.subscribe(quantity => {
        this.totalAmount = this.selectedFood.price * quantity;
        this.orderForm.get('totalAmount').setValue(this.totalAmount);
      });
    });
  }

  placeOrder(): void {
    if (this.orderForm.valid) {
      this.ordersService.placeOrder(this.orderForm.value).subscribe(() => {
        this.router.navigate(['/userviewfood']);
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/userviewfood']);
  }
}

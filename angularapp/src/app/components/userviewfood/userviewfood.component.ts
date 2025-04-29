import { Component, OnInit } from '@angular/core';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';

@Component({
  selector: 'app-userviewfood',
  templateUrl: './userviewfood.component.html',
  styleUrls: ['./userviewfood.component.css']
})
export class UserviewfoodComponent implements OnInit {

  foods: Food[] = []; // Array to store food items

  constructor(private foodService: FoodService) {}

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

}

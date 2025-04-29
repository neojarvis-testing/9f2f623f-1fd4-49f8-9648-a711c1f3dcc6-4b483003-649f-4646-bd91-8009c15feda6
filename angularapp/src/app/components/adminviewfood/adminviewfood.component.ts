import { Component, OnInit } from '@angular/core';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';

@Component({
  selector: 'app-adminviewfood',
  templateUrl: './adminviewfood.component.html',
  styleUrls: ['./adminviewfood.component.css']
})
export class AdminviewfoodComponent implements OnInit {
  foods: Food[] = []; // Array to store food items

  constructor(private foodService: FoodService) {}

  ngOnInit(): void {
    this.getAllFoodsWithPromise();
  }
  
  getAllFoodsWithPromise(): void {
    this.foodService.getAllFoods().toPromise()
      .then((data) => {
        this.foods = data; // Assign fetched food items to the array
        console.log('Food items fetched successfully:', this.foods);
      })
      .catch((err) => {
        console.error('Error fetching food items:', err);
        alert('Failed to fetch food items.');
      });
  }
}
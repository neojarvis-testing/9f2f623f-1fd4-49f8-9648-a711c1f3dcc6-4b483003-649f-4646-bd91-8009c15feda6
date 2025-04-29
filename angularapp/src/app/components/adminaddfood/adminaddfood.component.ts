import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';


@Component({
  selector: 'app-adminaddfood',
  templateUrl: './adminaddfood.component.html',
  styleUrls: ['./adminaddfood.component.css']
})
export class AdminaddfoodComponent implements OnInit {
  foodNames: string[] = [
    'Margherita Pizza',
    'Caesar Salad',
    'Chicken Biryani',
    'Spaghetti Carbonara',
    'Grilled Salmon',
    'Veggie Burger',
    'Tuna Sushi',
    'Beef Steak',
    'Paneer Tikka',
    'Chocolate Brownie'
  ];

  food: Food = {} as Food;
  editId: any;

  constructor(
    private foodService: FoodService,
    public router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.editId = this.activatedRoute.snapshot.params['id'];
    if (this.editId) {
      this.foodService.getFoodById(this.editId).subscribe((data) => {
        this.food = data;
      });
    }
  }

  addFood(): void {
    if (!this.editId) {
      this.foodService.addFood(this.food).subscribe({
        next: () => {
          alert('Food added successfully');
          this.router.navigate(['/adminViewFood']);
        },
        error: (err) => {
          console.error(err);
          alert('Failed to add food');
        }
      });
    }
  }

  updateFood(): void {
    if (this.editId) {
      this.foodService.updateFood(this.food.foodId, this.food).subscribe({
        next: () => {
          this.router.navigate(['/adminViewFood']);
          alert('Updated food successfully');
        }
      });
    }
  }
}
import { Component, OnInit } from '@angular/core';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';

@Component({
  selector: 'app-adminviewfood',
  templateUrl: './adminviewfood.component.html',
  styleUrls: ['./adminviewfood.component.css']
})
export class AdminviewfoodComponent implements OnInit {
  foods: Food[] = []; 
  paginatedFoods: Food[] = []; 
  selectedFood: Food | null = null;
  showPopup: boolean = false;
  showDialog = false;
  foodToDelete: number | null = null; 
  isLoading = false;

  // Pagination properties
  pageSize = 4;
  currentPage = 1;
  totalPages: number[] = [];

  constructor(private readonly foodService: FoodService) {}

  ngOnInit(): void {
    this.getAllFoods();
  }

  getAllFoods(): void {
    this.isLoading = true;
    this.foodService.getAllFoods().subscribe({
      next: (data) => {
        this.foods = data;
        this.setupPagination();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching food items:', err);
        alert('Failed to fetch food items.');
        this.isLoading = false;
      }
    });
  }

  setupPagination(): void {
    this.totalPages = Array(Math.ceil(this.foods.length / this.pageSize))
      .fill(0)
      .map((_, i) => i + 1);
    this.updatePaginatedFoods();
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages.length) {
      this.currentPage = page;
      this.updatePaginatedFoods();
    }
  }

  updatePaginatedFoods(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedFoods = this.foods.slice(startIndex, startIndex + this.pageSize);
  }
}
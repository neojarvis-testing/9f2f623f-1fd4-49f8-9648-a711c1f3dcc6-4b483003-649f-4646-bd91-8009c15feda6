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
  selectedFood: Food | null = null; // Store the food item to be edited
  showPopup: boolean = false; // Control popup visibility
  showDialog = false;
  foodToDelete: number | null = null; 
  constructor(private foodService: FoodService) {}
  isLoading=false;

  ngOnInit(): void {
    this.getAllFoods(); // Fetch food items on initialization
  }

  getAllFoods(): void {
    this.isLoading=true; //show spinner
    this.foodService.getAllFoods().subscribe(
      (data) => {
        this.foods = data; // Assign fetched food items to the array
        this.isLoading=false; // Hide spinner
        console.log('Food items fetched successfully:', this.foods);
      },
      (err) => {
        this.isLoading=false; // Hide Spinner
        console.error('Error fetching food items:', err);
        alert('Failed to fetch food items.');
      }
    );
  }

  openEditPopup(food: Food): void {
    console.log('Edit button clicked:', food); // Debugging output
    this.selectedFood = { ...food }; // Create a copy of the food object
    this.showPopup = true; // Show the popup
  }

  updateFood(): void {
    if (this.selectedFood) {
      this.foodService.updateFood(this.selectedFood.foodId, this.selectedFood).subscribe(
        (updatedFood) => {
          const index = this.foods.findIndex(f => f.foodId === updatedFood.foodId);
          if (index !== -1) {
            this.foods[index] = updatedFood;
            console.log('Food updated successfully:', updatedFood);
          }
          this.closePopup();
        },
        (err) => {
          console.error('Error updating food item:', err);
          alert('Failed to update food item.');
        }
      );
    }
  }
  

  closePopup(): void {
    this.showPopup = false;
    this.selectedFood = null; // Reset the selected food
  }

   confirmDeleteFood(foodId: number): void {
     this.foodToDelete = foodId;
     this.showDialog = true; // Show the confirmation dialog
  }
    
  deleteFood(): void {
    if (this.foodToDelete !== null) {
      this.foodService.deleteFood(this.foodToDelete).subscribe(() => {
        this.foods = this.foods.filter(food => food.foodId !== this.foodToDelete);
        this.closeDialog();
      }, (err) => {
        console.error('Error deleting food item:', err);
        if (err.status === 500) {
          alert('Internal server error. Please try again later.');
        } else {
          alert('Failed to delete food item. Error: ' + err.message);
        }
      });
    }
  }
  
closeDialog(): void {
  this.showDialog = false;
  this.foodToDelete = null; // Reset the food to delete
}
  
onDialogConfirm(confirm: boolean): void {
  if (confirm) {
    this.deleteFood();
  } else {
    this.closeDialog();
  }
}   
}
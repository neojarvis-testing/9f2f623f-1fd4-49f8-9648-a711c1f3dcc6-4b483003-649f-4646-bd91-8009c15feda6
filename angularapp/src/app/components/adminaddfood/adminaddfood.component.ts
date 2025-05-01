import { Component, OnInit } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { Router, ActivatedRoute } from '@angular/router';
import { Food } from 'src/app/models/food.model';
import { FoodService } from 'src/app/services/food.service';


@Component({
  selector: 'app-adminaddfood',
  templateUrl: './adminaddfood.component.html',
  styleUrls: ['./adminaddfood.component.css']
})
export class AdminaddfoodComponent implements OnInit {
  food: Food = {} as Food;
  editId: any;
  showDialog = false;
  isLoading=false;
  dialogMessage: string = '';


  constructor(
    private readonly foodService: FoodService,
    public router: Router,
  ) { }

  ngOnInit(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.isLoading = false;
    }, 800);
  }

  addFood(): void {
    this.isLoading = true;
    this.foodService.addFood(this.food).subscribe(() => {
      this.isLoading = false;
      this.dialogMessage = 'Food added successfully!';
      this.showDialog = true;
      this.editId = null; // Reset form state if needed
    }, (err) => {
      this.isLoading = false;
      this.dialogMessage = 'Failed to add food. Please try again.';
      this.showDialog = true;
      console.log(JSON.stringify(err));
    });
  }
    
  onDialogConfirm(): void {
    this.showDialog = false;
    // Redirect only if it was a success message
    if (this.dialogMessage === 'Food added successfully!') {
      this.router.navigate(['/adminViewFood']);
    }
  }

 

  onFileChange(event: Event, fileType: string): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0]; 
      const reader = new FileReader();
  
      reader.onload = () => {
        if (fileType === 'photoFile') { 
          console.log('Photo file added');
          this.food.photo = reader.result as string; 
        }
      };
      console.log(this.food)
      reader.readAsDataURL(file);  
    }
  }
}
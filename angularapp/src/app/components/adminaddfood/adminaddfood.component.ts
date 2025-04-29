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
  food: Food = {} as Food;
  editId: any;

  constructor(
    private foodService: FoodService,
    public router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  addFood(): void {
      this.foodService.addFood(this.food).subscribe({
        next: () => {
          alert('Food added successfully');
          this.router.navigate(['/adminViewFood']);
        },
        error: (err) => {
          console.log(JSON.stringify(err));
          alert('Failed to add food');
        }
      });
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
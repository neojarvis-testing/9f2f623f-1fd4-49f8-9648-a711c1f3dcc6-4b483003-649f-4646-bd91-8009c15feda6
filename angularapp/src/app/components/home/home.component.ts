import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() { }

  isLoading = true;
  email:string ='foodmart@gmail.com';
  ngOnInit(): void {
    setTimeout(() => {
      this.isLoading = false;
    }, 1500); // 1.5 seconds loading delay
  }
}
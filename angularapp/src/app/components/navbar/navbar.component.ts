import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  constructor(public authService:AuthService,private router:Router) { }
  showDialog = false;
  ngOnInit(): void {
  }
  
  logout(): void {
    this.showDialog = true;
  }
    
  onDialogConfirm(result: boolean): void {
    this.showDialog = false;
    if (result) {
    this.authService.logout();
    this.router.navigate(['/login'])k
    }
  }
}

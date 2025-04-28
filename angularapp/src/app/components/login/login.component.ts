import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Login } from 'src/app/models/login.model';
import { AuthService } from 'src/app/services/auth.service';
 
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  loginUser(login:NgForm) {
    this.authService.login(login.value).subscribe(response => {
      // Handle successful login
      this.router.navigate(["/"]);
    }, error => {
      // Handle login error
      console.error(error);
    });
    // Reset login after submission
  }
}

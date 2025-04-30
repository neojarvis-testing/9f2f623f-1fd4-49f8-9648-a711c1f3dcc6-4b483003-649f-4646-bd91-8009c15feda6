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
  
  loginData={
    email:'',
     password:''
  }
  errorMessage=''
  
  constructor(private authService:AuthService,private router:Router) { }
  login(){
    this.authService.login(this.loginData).subscribe(
      (response) =>{
        localStorage.setItem('userId', response.userId);
        localStorage.setItem('userRole',response.userRole);
        localStorage.setItem('token',response.token);
        localStorage.setItem('username',response.username);
        // if(response.userRole=='ADMIN'){
        //   this.router.navigate(['/adminAddFood']);
        // } else{
        //   this.router.navigate(['/'])
        // }
        this.router.navigate(['/'])
      },
      (error) =>{
        this.errorMessage = 'Invalid username or password';
      }
    )
  }

  ngOnInit(): void {
  }

}


import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent{

  user: User = {
    email: '',
    password: '',
    username: '',
    mobileNumber: '',
    userRole: 'USER'
  };
  showDialog=false
  constructor(private readonly authService: AuthService, private readonly router: Router) {}

  onSubmit(form: NgForm): void {
    if (form.valid) {
      this.authService.register(this.user).subscribe(() => {
        this.showDialog = true;
      }, (error) => {
        alert('Registration failed. Try again.');
        console.error(error);
      });
    }
  }
    
  onDialogConfirm(): void {
    this.showDialog = false;
    this.router.navigate(['/login'])
  }
}


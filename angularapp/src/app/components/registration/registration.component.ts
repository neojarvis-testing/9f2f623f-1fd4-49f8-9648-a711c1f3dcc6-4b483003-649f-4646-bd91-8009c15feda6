import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  form: FormGroup;
  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) { }
 
  ngOnInit(): void {
    this.form = this.fb.group({
      email: [''],
      password: [''],
      username: [''],
      mobileNumber: [''],
      userRole:['USER']
    })
  }
  register(){
    if(this.form.valid){
      this.authService.register(this.form.value).subscribe((data)=>{
        this.router.navigate(["/"]);
      })
      
    }
  }

}

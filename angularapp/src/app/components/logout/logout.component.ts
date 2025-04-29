import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private service:AuthService,private router:Router) { }


  ngOnInit(): void {
   if(confirm("Do you want to logout")){
    this.service.logout()
    this.router.navigate(["/login"])
  }
  else{
    this.router.navigate(["/"])
  }

}


}

import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-usermakeorder',
  templateUrl: './usermakeorder.component.html',
  styleUrls: ['./usermakeorder.component.css']
})
export class UsermakeorderComponent implements OnInit {

  constructor(private orderService:OrderService) { }

  ngOnInit(): void {
  }

}

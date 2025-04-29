import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { AdminaddfoodComponent } from './components/adminaddfood/adminaddfood.component';
//import { AdminorderschartComponent } from './components/adminorderschart/adminorderschart.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { AdminviewfoodComponent } from './components/adminviewfood/adminviewfood.component';
import { AdminviewordersComponent } from './components/adminvieworders/adminvieworders.component';
import { ErrorComponent } from './components/error/error.component';
import { UseraddfeedbackComponent } from './components/useraddfeedback/useraddfeedback.component';
import { UsermakeorderComponent } from './components/usermakeorder/usermakeorder.component';
import { UserviewfeedbackComponent } from './components/userviewfeedback/userviewfeedback.component';
import { UserviewfoodComponent } from './components/userviewfood/userviewfood.component';
import { UserviewordersComponent } from './components/uservieworders/uservieworders.component';

const routes: Routes = [
  {path:"register",component:RegistrationComponent},
  {path:"login",component:LoginComponent},
  {path:"",component:HomeComponent},
  {path:"adminAddFood",component:AdminaddfoodComponent},
  {path:"adminViewFood",component:AdminviewfoodComponent},
  {path:"userViewFood",component:UserviewfoodComponent},
  {path:"userAddOrder",component:UsermakeorderComponent},
  {path:"userViewOrders",component:UserviewordersComponent},
  //{path:"adminOrdersChart",component:AdminorderschartComponent},
  {path:"adminViewOrders",component:AdminviewordersComponent},
  {path:"userAddFeedback",component:UseraddfeedbackComponent},
  {path:"userViewFeedback",component:UserviewfeedbackComponent},
  {path:"adminViewFeedback",component:AdminviewfeedbackComponent},
 
  {path:'error',component:ErrorComponent},
  {path:"**",redirectTo:"error",pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

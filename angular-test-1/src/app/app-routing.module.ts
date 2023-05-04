import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DataComponent} from "./data/data.component";
import {OrderComponent} from "./order/order.component";


//mapping addresses and components
const routes: Routes = [
  {path: 'data', component: DataComponent},
  {path: '', component: OrderComponent},
  {path: 'logout', component: OrderComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }

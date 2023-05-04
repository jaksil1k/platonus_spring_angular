import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormControl, FormGroup, Validators} from "@angular/forms";

interface Order{
  id?: number;
  name: string;
  quantity: number;
  price: number;
}

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit{

  nameField: string | undefined
  quantityField: number | undefined
  totalPriceField: number | undefined
  price: number | undefined
  isConfirm = false;
  id: number | undefined;
  angForm: FormGroup | undefined;
  isTotalPriceValid = true;
  isQuantityValid = true;
  isValidName = true;



  constructor(private router: Router,
              private http: HttpClient) {
  }

  ngOnInit(): void {
    this.angForm = new FormGroup({
      name: new FormControl(this.nameField, [
        Validators.required,
        Validators.maxLength(50)
      ]),
      quantity: new FormControl(this.quantityField, [Validators.required, Validators.min(1)]),
      totalPrice: new FormControl(this.totalPriceField, [Validators.required, Validators.min(1)])

    })

  }

  get name() {
    return this.angForm?.get('name');
  }

  get quantity() {
    return this.angForm?.get('quantity'); }

  get totalPrice() {

    return this.angForm?.get('totalPrice'); }

  openDataPage() {
    this.router.navigate(['/data'], {queryParams: {id: this.id}})
  }

  confirmData() {

    if (this.nameField == undefined || this.nameField == '' || this.nameField.length >  50) {
      this.isValidName = false;
      return
    }

    this.isValidName = true;
    if (this.quantityField == undefined || this.quantityField < 1) {
      console.log(this.quantityField)
      this.isQuantityValid = false;
      return;
    }

    this.isQuantityValid = true;
    if (this.totalPriceField == undefined || this.totalPriceField < 0.01) {
      this.isTotalPriceValid = false;
      return;
    }

    this.isTotalPriceValid = true;
    this.price = this.totalPriceField / this.quantityField
    this.isConfirm = !this.isConfirm;
    const data: Order = {id: this.id, name: this.nameField, quantity: this.quantityField, price: this.price}
    const body = JSON.stringify(data)

    this.http.post<Order>("http://localhost:8080/api/v1/orders", body, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }).subscribe(
      {

        next: ((res: Order) => {
          this.id = res.id
          console.log(res)
        }),

        error: (error => {
          console.log(error)
        })

      }
    )
  }

  unConfirmData() {
    this.isConfirm = !this.isConfirm
  }
}

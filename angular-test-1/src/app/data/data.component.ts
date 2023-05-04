import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

interface Order{
  id: number | undefined;
  name: string | undefined;
  quantity: number | undefined;
  price: number | undefined;
}

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.css']
})
export class DataComponent implements OnInit{



  totalPrice: number | undefined
  disabled = false;
  data: Order |  undefined;

  constructor(private http: HttpClient,
              private router: Router,
              private activatedRouter: ActivatedRoute) {
  }

  ngOnInit(): void {

    this.activatedRouter.queryParams.subscribe(params => {
      if (this.data == undefined) {
        this.data = new class implements Order {
          id: number | undefined;
          name: string | undefined;
          price: number | undefined;
          quantity: number | undefined;
        }
      }

      this.data.id = params['id'];
    })

    this.http.get<Order>( "http://localhost:8080/api/v1/orders/" + this.data?.id).subscribe(
      {

        next: ((response: Order) => {
          this.data = response
        }),

        error: (error => {
          console.log(error)
        })
      }
    )
  }

  changeDisable() {
    this.disabled = !this.disabled;
  }

  logout() {
    this.router.navigate(['/logout'])
  }
}

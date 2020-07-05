import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'imooc microservice security ';
  authenticated = false;
  credentials = {username:'jojo',password:'123'} ;
  order = {id:'', productId:''}

  constructor(private http: HttpClient){

  }

  login(){
    console.info('this.credentials ' ,this.credentials)
    this.http.post('login', this.credentials).subscribe(()=>{
       this.authenticated = true ;
    },()=>{
        alert('login fail')
    }) ;
  }
  getOrder(){
    this.http.get('api/order/orders/1').subscribe( (data:any)  =>{
      this.order = data ;
    },()=>{
       alert('get order fail') ;
    }) ;
  }
}

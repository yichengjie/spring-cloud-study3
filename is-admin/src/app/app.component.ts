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
      this.http.get('api/user/me').subscribe(data => {
          if (data) {
              this.authenticated = true;
          }
          if (!this.authenticated) {
              window.location.href = 'http://auth.imooc.com:9090/oauth/authorize?' +
                  'client_id=admin&' +
                  'redirect_uri=http://admin.imooc.com:8080/oauth/callback&' +
                  'response_type=code';
          }
      });
  }

  login(){
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
  logout(){
    this.http.get("logout").subscribe(()=>{
       //this.authenticated = false ;
       window.location.href = 'http://auth.imooc.com:9090/logout?redirect_uri=http://admin.imooc.com:8080' ;
    },()=>{
       alert('logout fail')
    })
  }
}

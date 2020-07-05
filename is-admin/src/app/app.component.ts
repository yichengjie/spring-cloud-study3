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
      this.http.get('me').subscribe((data:any)=>{
          if (data){
              this.authenticated = true ;
          }
          if (!this.authenticated){//authorization_code
              window.location.href = 'http://auth.imooc.com:9090/oauth/authorize?' +
                  'client_id=admin&' +
                  'redirect_uri=http://admin.imooc.com:8080/oauth/callback&' +
                  'response_type=code&' +
                  'state=abc';
          }
      }) ;
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
       this.authenticated = false ;
    },()=>{
       alert('logout fail')
    })
  }
}

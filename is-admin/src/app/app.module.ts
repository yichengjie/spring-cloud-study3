import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {RefreshInterceptor} from './app.interceptor';
import {HTTP_INTERCEPTORS} from '@angular/common/http';

import {AppComponent} from './app.component';


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: RefreshInterceptor, multi: true},
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

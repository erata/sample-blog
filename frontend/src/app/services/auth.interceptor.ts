
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from "rxjs/internal/Observable";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let user = sessionStorage.getItem('token')

    if(!!user) {
      request = request.clone({
        setHeaders: {
          'Content-Type': 'application/json; charset=utf-8',
          'Accept': 'application/json',
          'Authorization': `Bearer ${sessionStorage.getItem('token')}`,
        },
      });
    }

    return next.handle(request);
  }
}

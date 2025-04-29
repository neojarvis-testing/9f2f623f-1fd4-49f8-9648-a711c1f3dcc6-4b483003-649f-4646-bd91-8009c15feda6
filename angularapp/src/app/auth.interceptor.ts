import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(request.url.includes('/login') || request.url.includes('/register')){
      return next.handle(request);
    }
    const token = localStorage.getItem('token')
    if(token){
      const authreq = request.clone({
        setHeaders:{
          Authorization: `Bearer ${token}`
        }
      })
      return next.handle(authreq)
    }
    return next.handle(request);
  }

}

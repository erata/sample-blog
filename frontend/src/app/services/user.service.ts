import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {map} from "rxjs/operators";
import {User} from "../shared/models/user";

const baseUrl = 'http://localhost:8080/users';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  register(user: User) {
    return this.http.post(baseUrl + "/register", user);
  }

  update(user: User) {
    let userId: number = JSON.parse(sessionStorage.getItem('user')).id;
    return this.http.put(`${baseUrl}/${userId}`, user);
  }

  authenticate(username, password) {
    console.log(username, password)
    return this.http.post<string>(baseUrl + '/authenticate', {username: username, password: password}).pipe(
      map(
        (response:any) => {
          sessionStorage.setItem('token', response.token);
          sessionStorage.setItem('user', JSON.stringify(response.user));

          return response;
        }
      )
    );
  }

  get(userId: number){
    return this.http.get(`${baseUrl}/${userId}`).pipe(
      map(
        (response:any) => {
          console.log('token', response);
          return response;
        }
      )
    );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('token');
    return !(user === null)
  }

 logOut() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user')
  }

  getAuthUser(): any {
    let user:any = sessionStorage.getItem('user');
    return JSON.parse(user);
  }

}

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

const baseUrl = 'http://localhost:8080/categories';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(baseUrl);
  }

}

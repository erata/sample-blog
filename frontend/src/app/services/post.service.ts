import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserService} from "./user.service";


const baseUrl = 'http://localhost:8080/posts';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient,
              private userService: UserService) { }

  getAll(category?:string, search?:string, page?: number, size?: number) {

    //var paramsObj = new PagingFilter();
    const paramsObj = {
      category: category || '',
      search: search || '',
      page: String(page) || '',
      size: String(size) || ''
    };

    const params = new HttpParams({ fromObject: paramsObj });
    console.log(JSON.stringify(paramsObj));

    return this.http.get(baseUrl, {params});
  }

  getAllUserPosts(page?: number, size?: number) {
    //var paramsObj = new PagingFilter();
    const paramsObj = {
      page: String(page) || '',
      size: String(size) || ''
    };

    const params = new HttpParams({ fromObject: paramsObj });
    console.log(JSON.stringify(paramsObj));

    let userId:number = this.userService.getAuthUser().id;
    console.log(userId);

    return this.http.get(`${baseUrl}/user/${userId}`, {params});
  }

  get(id) {
    return this.http.get(`${baseUrl}/${id}`);
  }

  create(data) {
    return this.http.post(baseUrl, data);
  }

  createComment(id, data) {
    console.log("comment: ", data)
    return this.http.post(`${baseUrl}/${id}/comments`, data);
  }

  update(id, data) {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id) {
    return this.http.delete(`${baseUrl}/${id}`);
  }

  bulkPublish(ids: Array<number>) {
    return this.http.put(`${baseUrl}/status`, {ids: ids, isPublished: true});
  }

  bulkUnpublish(ids: Array<number>) {
    return this.http.put(`${baseUrl}/status`, {ids: ids, isPublished: false});
  }

  bulkDelete(ids: Array<number>) {
    return this.http.delete(`${baseUrl}?ids=${ids}`);
    //return this.http.request('delete', `${baseUrl}`, { body: { ids: ids } });
  }

  deleteAll() {
    return this.http.delete(baseUrl);
  }

  findByTitle(title) {
    return this.http.get(`${baseUrl}?title=${title}`);
  }

  getHeader(): any {
    /*let header = {
      headers: new HttpHeaders()
        .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
    }

    return header;*/
    return new HttpHeaders({ Authorization: 'Bearer ' + sessionStorage.getItem('token') });
  }
}

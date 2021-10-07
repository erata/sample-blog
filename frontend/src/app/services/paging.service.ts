import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PagingService {
  public pageEvent: EventEmitter<any> = new EventEmitter();
}

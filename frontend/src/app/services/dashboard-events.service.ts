import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardEventsService {

  public postSelectionEvent: EventEmitter<any> = new EventEmitter();

  public postBulkActionEvent: EventEmitter<any> = new EventEmitter();

  constructor() { }

}

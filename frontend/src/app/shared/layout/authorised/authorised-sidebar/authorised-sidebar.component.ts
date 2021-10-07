import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {DashboardEventsService} from "../../../../services/dashboard-events.service";

@Component({
  selector: 'app-authorised-sidebar',
  templateUrl: './authorised-sidebar.component.html',
  styleUrls: ['./authorised-sidebar.component.css']
})
export class AuthorisedSidebarComponent implements OnInit {
  isDisabled:boolean = true;
  color: string;

  constructor(public userService: UserService,
              private pashboardEventsService: DashboardEventsService) { }

  ngOnInit(): void {
    this.pashboardEventsService.postSelectionEvent.subscribe((value) => {
      this.isDisabled = value == 0;
    });
  }

  postBulkAction(action: string) {
    this.pashboardEventsService.postBulkActionEvent.emit(action);
  }
}

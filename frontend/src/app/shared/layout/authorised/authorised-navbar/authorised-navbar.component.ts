import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-authorised-navbar',
  templateUrl: './authorised-navbar.component.html',
  styleUrls: ['./authorised-navbar.component.css']
})
export class AuthorisedNavbarComponent implements OnInit {
  isCollapsed: boolean = true;
  //userService: UserService

  constructor(public userService: UserService) {
    //this.userService = _userService;
  }

  ngOnInit(): void { }

}

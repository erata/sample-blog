import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'site-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isCollapsed: boolean = true;
  //userService: UserService

  constructor(public userService: UserService) {
    //this.userService = _userService;
  }

  ngOnInit(): void { }

}

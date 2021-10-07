import { Component, OnInit } from '@angular/core';
import {User} from "../../../shared/models/user";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  user: User;
  message:string;

  constructor( private userService: UserService) { }

  ngOnInit(): void {

    let userId: number = JSON.parse(sessionStorage.getItem('user')).id;
    this.userService.get(userId).subscribe(
      response => {
        console.log("user: ", response);
        this.user = response;
      },
      error => {
        console.log(error);
      });

  }

  updateUser(){
    this.userService.update(this.user)
      .subscribe(
        response => {
          console.log(response);
          this.message = 'The user was updated successfully!';
        },
        error => {
          console.log(error);
        });
  }
}

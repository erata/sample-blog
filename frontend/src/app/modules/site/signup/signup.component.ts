import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../services/user.service";
import {User} from "../../../shared/models/user";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  user: User;
  confirmPassword: string;
  submitted = false;
  emailAdress:string;

  constructor(private userService: UserService) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  createUser(){
    this.emailAdress = this.user.email;

      this.userService.register(this.user)
      .subscribe(
        (data: any) => {
          console.log("User created ", data);
          this.submitted = true;
          //this.emailAdress = this.user.email;
        },
        error => {
          console.log(error);
        });
  }

}

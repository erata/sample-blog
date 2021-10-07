import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserLogin} from "../../../shared/models/user-login";
import {UserService} from "../../../services/user.service";
import {User} from "../../../shared/models/user";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 user:UserLogin;

  constructor(private router: Router,
              private userService: UserService,
             ) {
    this.user = new User();

  }

  ngOnInit(): void {

  }

  login(){
    console.log(this.user);

    this.userService.authenticate(this.user.username, this.user.password)
      .subscribe(
        (response: any) => {
          window.open(window.location.origin + '/dashboard', '_blank');
          //this.router.navigate(['/dashboard'])
        },
        error => {
          console.log(error);
        });
  }

}

import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { UserService } from './user.service';
  @Injectable({
  providedIn: 'root'
})
export class AuthGaurdService implements CanActivate {
  constructor(private router: Router,
              private userService: UserService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.userService.isUserLoggedIn())
      return true;

    this.router.navigate(['login']);
    return false;
  }
}

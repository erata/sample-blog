import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';

import {SignupComponent} from "./signup/signup.component";
import {LoginComponent} from "./login/login.component";
import {PostsListComponent} from "./posts-list/posts-list.component";
import {PostDetailsComponent} from "./post-details/post-details.component";
import {DefaultLayoutComponent} from "../../shared/layout/guest/layout/default-layout.component";
import {LandingPageLayoutComponent} from "../../shared/layout/guest/core-layout/landing-page-layout.component";


const routes: Routes = [
  {
    path: '',
    component: DefaultLayoutComponent,
    children: [
      { path: '', redirectTo: 'posts', pathMatch: 'full' },
      { path: 'posts', component: PostsListComponent },
      { path: 'post/:id', component: PostDetailsComponent },
    ]
  },
  {
    path: '',
    component: LandingPageLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'signup', component: SignupComponent }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SiteRoutingModule { }

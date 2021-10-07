import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {QuillModule} from "ngx-quill";
import {PaginationModule} from "ngx-bootstrap/pagination";
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {PostsListComponent} from "./posts-list/posts-list.component";
import {PostDetailsComponent} from "./post-details/post-details.component";
import {NgPipesModule} from "ngx-pipes";
import {SiteRoutingModule} from "./site-routing.module";


@NgModule({
  imports: [
    CommonModule,
    SiteRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    PaginationModule,
    NgPipesModule,
    QuillModule
  ],
  declarations: [LoginComponent, SignupComponent, PostsListComponent, PostDetailsComponent]
})
export class SiteModule { }

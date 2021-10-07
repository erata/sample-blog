import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AddPostComponent} from "./add-post/add-post.component";
import {EditPostComponent} from "./edit-post/edit-post.component";
import {ListComponent} from "./list/list.component";
import {DashboardRoutingModule} from "./dashboard-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {QuillModule} from "ngx-quill";
import {PaginationModule} from "ngx-bootstrap/pagination";
import { EditUserComponent } from './edit-user/edit-user.component';


@NgModule({
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    PaginationModule,
    QuillModule
  ],
  declarations: [AddPostComponent, EditPostComponent, ListComponent, EditUserComponent]
})
export class DashboardModule { }

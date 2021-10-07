import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';
import {AddPostComponent} from "./add-post/add-post.component";
import {EditPostComponent} from "./edit-post/edit-post.component";
import {ListComponent} from "./list/list.component";
import {EditUserComponent} from "./edit-user/edit-user.component";
import {AuthorisedLayoutComponent} from "../../shared/layout/authorised/authorised-layout/authorised-layout.component";
import {AuthorisedCoreLayoutComponent} from "../../shared/layout/authorised/authorised-core-layout/authorised-core-layout.component";

const routes: Routes = [
  {
    path: '',
    component: AuthorisedLayoutComponent,
    children: [
      { path: '',  component: ListComponent },
      { path: 'user/edit', component: EditUserComponent },
    ]
  },
  {
    path: '',
    component: AuthorisedCoreLayoutComponent,
    children: [
      { path: 'post/add', component: AddPostComponent },
      { path: 'post/edit/:id', component: EditPostComponent },

    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }

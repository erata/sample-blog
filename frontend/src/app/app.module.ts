import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {QuillModule} from "ngx-quill";

import { SlugifyPipe } from './shared/pipes/slugify.pipe';

import {DashboardModule} from "./modules/dashboard/dashboard.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {PaginationModule} from "ngx-bootstrap/pagination";
import {AuthInterceptor} from "./services/auth.interceptor";
import {TypeaheadModule} from "ngx-bootstrap/typeahead";
import {NgPipesModule} from "ngx-pipes";
import {SiteModule} from "./modules/site/site.module";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {SidebarComponent} from "./shared/layout/guest/sidebar/sidebar.component";
import {NavbarComponent} from "./shared/layout/guest/navbar/navbar.component";
import {FooterComponent} from "./shared/layout/guest/footer/footer.component";
import {DefaultLayoutComponent} from "./shared/layout/guest/layout/default-layout.component";
import {LandingPageLayoutComponent} from "./shared/layout/guest/core-layout/landing-page-layout.component";
import {AuthorisedNavbarComponent} from "./shared/layout/authorised/authorised-navbar/authorised-navbar.component";
import {AuthorisedSidebarComponent} from "./shared/layout/authorised/authorised-sidebar/authorised-sidebar.component";
import {AuthorisedLayoutComponent} from "./shared/layout/authorised/authorised-layout/authorised-layout.component";
import {AuthorisedCoreLayoutComponent} from "./shared/layout/authorised/authorised-core-layout/authorised-core-layout.component";

@NgModule({
  declarations: [
    AppComponent,
    SlugifyPipe,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    DefaultLayoutComponent,
    LandingPageLayoutComponent,
    AuthorisedNavbarComponent,
    AuthorisedSidebarComponent,
    AuthorisedLayoutComponent,
    AuthorisedCoreLayoutComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    //NgPipesModule,
    SiteModule,
    DashboardModule,
    PaginationModule.forRoot(),
    QuillModule.forRoot(),
    TypeaheadModule.forRoot(),
    BrowserAnimationsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }

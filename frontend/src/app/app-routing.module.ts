import { NgModule } from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AuthGaurdService} from "./services/auth-guard.service";
import {LandingPageLayoutComponent} from "./shared/layout/guest/core-layout/landing-page-layout.component";

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/site/site.module').then(m => m.SiteModule),
    data: { preload: true }
  },
  {
    path: 'dashboard',
    loadChildren: () => import(`./modules/dashboard/dashboard.module`).then(m => m.DashboardModule),
    canActivate:[AuthGaurdService],
    data: { preload: true }
  },
  {
    path: '',
    component: LandingPageLayoutComponent,
    children: [
      { path: '**', component: PageNotFoundComponent }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: false, onSameUrlNavigation: 'reload', preloadingStrategy: PreloadAllModules})],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }

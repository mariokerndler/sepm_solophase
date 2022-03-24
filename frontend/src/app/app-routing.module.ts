import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HorseListComponent } from './component/horse/horse-list/horse-list.component';
import {HorseCreationComponent} from './component/horse/horse-creation/horse-creation.component';
import {HorseEditComponent} from './component/horse/horse-edit/horse-edit.component';
import {HorseDetailComponent} from './component/horse/horse-detail/horse-detail.component';
import {OwnerListComponent} from './component/owner/owner-list/owner-list.component';
import {OwnerCreationComponent} from './component/owner/owner-creation/owner-creation.component';

const routes: Routes = [
  {path: '', redirectTo: 'horses', pathMatch: 'full'},
  {path: 'horses', component: HorseListComponent},
  {path: 'horses/create', component: HorseCreationComponent},
  {path: 'horses/:id/edit', component: HorseEditComponent},
  {path: 'horses/:id/detail', component: HorseDetailComponent},
  {path: 'owners', component: OwnerListComponent},
  {path: 'owners/create', component: OwnerCreationComponent},
  {path: '**', redirectTo: 'horses'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

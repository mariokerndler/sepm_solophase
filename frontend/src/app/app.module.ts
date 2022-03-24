import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/common/header/header.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HorseListComponent} from './component/horse/horse-list/horse-list.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { HorseFormComponent } from './component/horse/horse-form/horse-form.component';
import { HorseCreationComponent } from './component/horse/horse-creation/horse-creation.component';
import { HorseEditComponent } from './component/horse/horse-edit/horse-edit.component';
import {NgSelectModule} from '@ng-select/ng-select';
import { HorseDetailComponent } from './component/horse/horse-detail/horse-detail.component';
import { OwnerCreationComponent } from './component/owner/owner-creation/owner-creation.component';
import { OwnerListComponent } from './component/owner/owner-list/owner-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HorseListComponent,
    HorseFormComponent,
    HorseCreationComponent,
    HorseEditComponent,
    HorseDetailComponent,
    OwnerCreationComponent,
    OwnerListComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        NgbModule,
        ReactiveFormsModule,
        NgSelectModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

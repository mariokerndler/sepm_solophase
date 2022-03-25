import { Component } from '@angular/core';
import {OwnerService} from '../../../service/owner.service';
import {NotificationService} from '../../../service/notification.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {AddOwnerDto} from '../../../dto/owner/addOwnerDto';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-owner-creation',
  templateUrl: './owner-creation.component.html',
  styleUrls: ['./owner-creation.component.scss']
})
export class OwnerCreationComponent {

  model = new AddOwnerDto();
  createAnother = false;

  constructor(
    private ownerService: OwnerService,
    private notificationService: NotificationService,
    private router: Router,
    private location: Location
  ) { }

  onSubmit(form: NgForm) {
    this.ownerService.create(this.model)
      .subscribe( (_) => {
        if(this.createAnother) {
          form.resetForm();
        }
        else {
          this.navigateToOwnerList();
        }
      });
  }

  goBack() {
    this.location.back();
  }

  private navigateToOwnerList() {
    this.router.navigate(['/owners'])
      .catch((reason) => {
        this.notificationService.notifyError(reason.toString());
      });
  }
}

import { Component, OnInit } from '@angular/core';
import { HorseDto } from '../../../dto/horseDto';
import { HorseService } from 'src/app/service/horse.service';
import { Router} from '@angular/router';
import {NotificationService} from "../../../service/notification.service";

@Component({
  selector: 'app-horse',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit {
  search = false;
  horses: HorseDto[];
  error: string = null;

  constructor(
    private service: HorseService,
    private router: Router,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
    this.reloadHorses();
  }

  reloadHorses() {
    this.service.getAll().subscribe({
      next: data => {
        this.horses = data;
      },
      error: error => {
        this.notificationService.notifyFailedOperation('Fetching horses')(error);
      }
    });
  }

  createHorse() {
    this.router
      .navigate(['horses/create'])
      .catch((err) => this.notificationService.notifyError(err.toString()));
  }

  editHorse(id: number) {
    this.router
      .navigate(['/horses', id, 'edit'])
      .catch((err) => this.notificationService.notifyError(err.toString()));
  }

  deleteHorse(id: number) {
    this.service.delete(id).subscribe({
      next: _ => {
        this.reloadHorses();
      },
      error: (err) => {
        this.notificationService.notifyError(err.toString());
      }
    });
  }

  viewHorse(id: number) {
    this.router
      .navigate(['/horses', id, 'detail'])
      .catch((err) =>  this.notificationService.notifyError(err.toString()));
  }
}

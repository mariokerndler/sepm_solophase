import {Component, OnDestroy, OnInit} from '@angular/core';
import {HorseDto} from '../../../dto/horse/horseDto';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseService} from '../../../service/horse.service';
import {Location} from '@angular/common';
import {NotificationService} from '../../../service/notification.service';

@Component({
  selector: 'app-horse-detail',
  templateUrl: './horse-detail.component.html',
  styleUrls: ['./horse-detail.component.scss']
})
export class HorseDetailComponent implements OnInit, OnDestroy {

  horse: HorseDto;

  private routeSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private horseService: HorseService,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(
      (params) => this.horseService.getHorse(params.id, null, () => this.navigateToHorseList())
        .subscribe((horse) => {
          this.horse = horse;
        })
    );
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
  }

  deleteHorse() {
    this.horseService.delete(this.horse.id)
      .subscribe({
        next: _ => {
          this.goBack();
        },
        error: (err) => {
          this.notificationService.notifyFailedOperation('Deleting horse')(err);
        }
      });
  }

  editHorse() {
    this.router
      .navigate(['/horses', this.horse.id, 'edit'])
      .catch((err) => {
        this.notificationService.notifyError(err.toString());
      });
  }

  goBack() {
    this.location.back();
  }

  private navigateToHorseList() {
    this.router.navigate(['/horses'])
      .catch((reason) => {
        this.notificationService.notifyError(reason.toString());
      });
  }
}

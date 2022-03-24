import {Component, OnDestroy, OnInit} from '@angular/core';
import {HorseService} from '../../../service/horse.service';
import {NotificationService} from '../../../service/notification.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseDto} from '../../../dto/horse/horseDto';
import {debounceTime, distinctUntilChanged, Subscription} from 'rxjs';

@Component({
  selector: 'app-horse-tree',
  templateUrl: './horse-tree.component.html',
  styleUrls: ['./horse-tree.component.scss']
})
export class HorseTreeComponent implements OnInit, OnDestroy {

  horse: HorseDto;
  numberOfGenerations = 0;

  private routeIdSubscription: Subscription;
  private routeGenerationSubscription: Subscription;

  constructor(
    private horseService: HorseService,
    private notificationService: NotificationService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.routeIdSubscription = this.route.params
      .subscribe( (params) => {
        const id = params.id;
        this.reload(id);
      });

    this.routeGenerationSubscription = this.route.queryParams
      .pipe(
        distinctUntilChanged(),
        debounceTime(100)
      ).subscribe( (params) => {
        const generations = params.generations;
        if(!generations) {
          this.setQueryParameter();
        }
        else {
          this.numberOfGenerations = generations;
          if(this.horse?.id) {
            this.reload(this.horse.id);
          }
        }
      });
  }

  ngOnDestroy(): void {
    this.routeIdSubscription.unsubscribe();
    this.routeGenerationSubscription.unsubscribe();
  }

  reload(id: number) {
    this.horseService.getHorse(id, this.numberOfGenerations, () => this.navigateToHorseList())
      .subscribe(horse => this.horse = horse);
  }

  setQueryParameter() {
    if(!Number.isInteger(this.numberOfGenerations) || this.numberOfGenerations < 0) {
      return;
    }

    this.router.navigate([], {queryParams: {generations: this.numberOfGenerations}})
      .catch( (reason) => {
        this.notificationService.notifyError(reason.toString());
      });
  }

  private navigateToHorseList() {
    this.router.navigate(['/horses'])
      .catch((reason) => {
        this.notificationService.notifyError(reason.toString());
      });
  }
}

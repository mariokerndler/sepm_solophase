import {Component, OnDestroy, OnInit} from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit, OnDestroy {

  id: number;
  private routeSubscription: Subscription;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => this.id = params.id);
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
  }

}

import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { HorseDto } from '../../../dto/horse/horseDto';
import { HorseService } from 'src/app/service/horse.service';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationService} from '../../../service/notification.service';
import {HorseSearchDto} from '../../../dto/horse/horseSearchDto';
import {debounceTime, distinctUntilChanged, fromEvent, merge, Observable, Subscription, tap} from 'rxjs';
import {SearchService} from '../../../service/search.service';
import {NgSelectComponent} from '@ng-select/ng-select';
import {Gender} from '../../../dto/horse/gender';
import {OwnerDto} from "../../../dto/owner/ownerDto";
import {OwnerService} from "../../../service/owner.service";

@Component({
  selector: 'app-horse',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('nameSearch') nameSearch: ElementRef;
  @ViewChild('descriptionSearch') descriptionSearch: ElementRef;
  @ViewChild('bornAfterSearch') bornAfterSearch: ElementRef;
  @ViewChild('genderSearch') genderSearch: NgSelectComponent;
  @ViewChild('ownerSearch') ownerSearch: NgSelectComponent;

  horses: HorseDto[];
  genders = Object.values(Gender);
  isLoading: boolean;

  searchParameter: HorseSearchDto = new HorseSearchDto();

  owners$: Observable<OwnerDto[]>;

  private routeSearchQuerySubscription: Subscription;
  private searchSubscription: Subscription;

  constructor(
    private service: HorseService,
    private router: Router,
    private route: ActivatedRoute,
    private horseService: HorseService,
    private searchService: SearchService,
    private ownerService: OwnerService,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
    this.reloadHorses();
    this.owners$ = this.ownerService.getAll();

    this.routeSearchQuerySubscription = this.route.queryParams
      .subscribe( (params) => {
        this.searchParameter.name = params.name;
        this.searchParameter.description = params.description;
        this.searchParameter.gender = params.gender;
        this.searchParameter.ownerId = params.ownerId;
        this.searchWithCurrentFilter();
      });
  }

  ngAfterViewInit() {
    this.registerSearchQueries();
  }

  ngOnDestroy() {
    this.searchSubscription.unsubscribe();
    this.routeSearchQuerySubscription.unsubscribe();
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

  resetSearchQuery() {
    this.searchParameter = new HorseSearchDto();
    this.setSearchQueryParams();
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

  private searchWithCurrentFilter() {
    this.isLoading = true;
    this.horseService.search(this.searchParameter)
      .pipe(
        tap(_ => this.isLoading = false))
      .subscribe(horses => this.horses = horses);
  }

  private setSearchQueryParams() {
    this.router.navigate([], { queryParams: {
        name: this.searchParameter.name?.length ? this.searchParameter.name : null,
        description:  this.searchParameter.description?.length ? this.searchParameter.description : null,
        bornAfter: this.searchParameter.bornAfter?.length ? this.searchParameter.bornAfter : null,
        gender: this.searchParameter.gender,
        ownerId: this.searchParameter.ownerId
      }
    }).catch( (reason) => this.notificationService.notifyError(reason.toString()));
  }

  private registerSearchQueries() {
    this.searchSubscription = merge(
      fromEvent(this.nameSearch.nativeElement, 'input')
        .pipe(debounceTime(500)),
      fromEvent(this.descriptionSearch.nativeElement, 'input')
        .pipe(debounceTime(500)),
      fromEvent(this.bornAfterSearch.nativeElement, 'input')
        .pipe(debounceTime(500)),
      this.genderSearch.changeEvent.asObservable(),
      this.ownerSearch.changeEvent.asObservable()
    ).pipe(
      distinctUntilChanged(),
      tap( _ => this.setSearchQueryParams())
    ).subscribe();
  }
}

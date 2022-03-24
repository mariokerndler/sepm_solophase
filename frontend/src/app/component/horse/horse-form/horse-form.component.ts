import {Component, Input, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {HorseService} from 'src/app/service/horse.service';
import {HorseDto} from '../../../dto/horse/horseDto';
import {Gender} from '../../../dto/horse/gender';
import {map, Observable, of, Subject} from 'rxjs';
import {AddUpdateHorseDto} from '../../../dto/horse/addUpdateHorseDto';
import {NotificationService} from '../../../service/notification.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {HorseSearchDto} from '../../../dto/horse/horseSearchDto';
import {OwnerDto} from '../../../dto/owner/ownerDto';
import {OwnerService} from '../../../service/owner.service';
import {SearchService} from '../../../service/search.service';

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss']
})
export class HorseFormComponent implements OnInit {

  @Input() horseId: number = null;
  formReady = false;
  model: AddUpdateHorseDto;
  genders = Object.values(Gender);

  formSubmitButtonText: string;

  owners$: Observable<OwnerDto[]>;

  dams$: Observable<HorseDto[]>;
  isLoadingDam = false;
  damSearchTerms$ = new Subject<string>();

  sires$: Observable<HorseDto[]>;
  isLoadingSire = false;
  sireSearchTerms$ = new Subject<string>();

  createAnother = false;

  constructor(
    private horseService: HorseService,
    private ownerService: OwnerService,
    private searchService: SearchService,
    private notificationService: NotificationService,
    private router: Router,
    private location: Location
  ) {}

  private static createModelFromHorseDto(horse: HorseDto): AddUpdateHorseDto {
    return {
      name: horse.name,
      description: horse?.description,
      birthdate: horse.birthdate,
      gender: horse.gender,
      ownerId: horse.owner?.id,
      damId: horse.dam?.id,
      sireId: horse.sire?.id
    };
  }

  private static getFilter(gender: Gender, searchTerm?: string): HorseSearchDto {
    const filter = new HorseSearchDto();
    filter.limit = 5;
    filter.gender = gender;
    filter.name = searchTerm;
    return filter;
  }

  ngOnInit(): void {
    this.formSubmitButtonText = this.isUpdateForm() ? 'Save' : 'Create';

    if(this.isUpdateForm()) {
      this.horseService
        .getHorse(this.horseId, null, () => this.navigateToHorseList())
        .subscribe(horse => this.onModelLoaded(HorseFormComponent.createModelFromHorseDto(horse)));
    }
    else {
      this.onModelLoaded(new AddUpdateHorseDto());
    }
  }

  onSubmit(form: NgForm) {
    (this.isUpdateForm()
      ? this.horseService.update(this.horseId, this.model)
      : this.horseService.create(this.model)
    ).subscribe( (_) => {
      if(this.createAnother) {
        form.resetForm();
      }
      else {
        this.navigateToHorseList();
      }
    });
  }

  goBack() {
    this.location.back();
  }

  private isUpdateForm(): boolean {
    return !!this.horseId;
  }

  private navigateToHorseList() {
    this.router.navigate(['/horses'])
      .catch((reason) => {
        this.notificationService.notifyError(reason.toString());
      });
  }

  private onModelLoaded(model: AddUpdateHorseDto) {
    this.model = model;
    this.loadDamAndSearch();
    this.loadSiresAndSearch();
    this.loadOwnersAndSearch();
    this.formReady = true;
  }

  private loadDamAndSearch() {
    this.dams$ = this.searchService
      .registeredSearch(
        this.damSearchTerms$,
        loading => this.isLoadingDam = loading,
        searchTerm => this.horseService
          .search(HorseFormComponent.getFilter(Gender.female, searchTerm)),
        this.loadDefaultHorses(Gender.female)
      );
  }

  private loadSiresAndSearch() {
    this.sires$ = this.searchService
      .registeredSearch(
        this.sireSearchTerms$,
        loading => this.isLoadingSire = loading,
        searchTerm => this.horseService
          .search(HorseFormComponent.getFilter(Gender.male, searchTerm)),
        this.loadDefaultHorses(Gender.male)
      );
  }

  private loadOwnersAndSearch() {
    this.owners$ = this.ownerService.getAll();
  }

  private loadDefaultHorses(gender: Gender): Observable<HorseDto[]> {
    const id = gender === Gender.female ? this.model.damId : this.model.sireId;

    if(id) {
      return this.horseService.getHorse(id)
        .pipe(map((horse: HorseDto) => Array.of(horse)));
    }
    else {
      return this.horseService.search(HorseFormComponent.getFilter(gender));
    }
  }
}

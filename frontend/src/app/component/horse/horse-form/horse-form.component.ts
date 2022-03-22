import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from '@angular/forms';
import {HorseService} from 'src/app/service/horse.service';
import {HorseDto} from '../../../dto/horseDto';
import {Gender} from '../../../dto/gender';
import {Observable, Subject} from 'rxjs';
import {AddUpdateHorseDto} from '../../../dto/addUpdateHorseDto';
import {NotificationService} from '../../../service/notification.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {HorseSearchDto} from '../../../dto/horseSearchDto';

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

  dams$: Observable<HorseDto[]>;
  isLoadingDam = false;
  damSearchTerms$ = new Subject<string>();

  sires$: Observable<HorseDto[]>;
  isLoadingSire = false;
  sireSearchTerms$ = new Subject<string>();

  createAnother = false;

  constructor(
    private fb: FormBuilder,
    private horseService: HorseService,
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
      damId: horse.dam?.id,
      sireId: horse.sire?.id
    };
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
    this.formReady = true;
  }

  private loadDamAndSearch() {
    const filter = new HorseSearchDto();
    filter.limit = 5;
    filter.gender = Gender.female;
    this.dams$ = this.horseService.search(filter);
  }

  private loadSiresAndSearch() {
    const filter = new HorseSearchDto();
    filter.limit = 5;
    filter.gender = Gender.male;
    this.sires$ = this.horseService.search(filter);
  }
}

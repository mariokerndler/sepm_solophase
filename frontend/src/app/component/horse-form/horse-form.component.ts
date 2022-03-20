import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, NgForm } from '@angular/forms';
import { HorseService } from 'src/app/service/horse.service';
import {HorseDto} from '../../dto/horseDto';
import {Gender} from '../../dto/gender';
import {Observable, Subject} from 'rxjs';
import {AddUpdateHorseDto} from '../../dto/addUpdateHorseDto';
import {HttpErrorResponse} from '@angular/common/http';

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
  sent: boolean;
  gotResponse: boolean;
  successful: boolean;
  responseMessage: string;

  dams$: Observable<HorseDto[]>;
  isLoadingDam = false;
  damSearchTerms$ = new Subject<string>();

  sires$: Observable<HorseDto[]>;
  isLoadingSire = false;
  sireSearchTerms$ = new Subject<string>();

  constructor(private fb: FormBuilder, private horseService: HorseService) {}

  private static createModelFromHorseDto(horse: HorseDto): AddUpdateHorseDto {
    console.log(horse);
    const addUpdateHorseModel: AddUpdateHorseDto = {
      name: horse.name,
      description: horse?.description,
      birthdate: horse.birthdate,
      gender: horse.gender,
      damId: horse.dam?.id,
      sireId: horse.sire?.id
    };
    console.log(addUpdateHorseModel);
    return addUpdateHorseModel;
  }

  ngOnInit(): void {
    this.formSubmitButtonText = this.isUpdateForm() ? 'Save' : 'Create';

    if(this.isUpdateForm()) {
      this.horseService
        .getHorse(this.horseId)
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
    )
      .subscribe({
        next: (_) => {
          this.gotResponse = true;
          this.successful = true;
          this.responseMessage = this.createResponseMessage();
        },
        error: (err) => {
          this.gotResponse = true;
          this.successful = false;
          this.responseMessage = this.createResponseMessage(err);
        }
    });

    this.sent = true;
    form.resetForm();
  }

  private isUpdateForm(): boolean {
    return !!this.horseId;
  }

  private createResponseMessage(err?: any): string {
    let msg: string;

    if(err) {
      const message = err.error.message || JSON.stringify(err.error).replace(/[{}]/g, '');

      if(this.isUpdateForm()) {
        msg = `Could not update horse with id="${this.horseId}": ${message}`;
      }
      else {
        msg = `Could not create horse: ${message}`;
      }
    }
    else {
      if(this.isUpdateForm()) {
        msg = 'Horse updated successfully!';
      }
      else {
        msg = 'Horse created successfully!';
      }
    }

    return msg;
  }
  private onModelLoaded(model: AddUpdateHorseDto) {
    this.model = model;
    this.loadDamAndSearch();
    this.loadSiresAndSearch();
    this.formReady = true;
  }

  private loadDamAndSearch() {
    this.dams$ = this.horseService.getAll();
  }

  private loadSiresAndSearch() {
    this.sires$ = this.horseService.getAll();
  }
}

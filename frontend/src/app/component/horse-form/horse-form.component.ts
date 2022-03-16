import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, NgForm } from '@angular/forms';
import { HorseService } from 'src/app/service/horse.service';
import {HorseDto} from '../../dto/horseDto';
import {Gender} from '../../dto/gender';

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss']
})
export class HorseFormComponent implements OnInit {

  @Input() horseId: number = null;
  model: HorseDto;
  genders = Object.values(Gender);

  formSubmitButtonText: string;

  sent: boolean;
  gotResponse: boolean;
  successful: boolean;
  responseMessage: string;

  constructor(private fb: FormBuilder, private horseService: HorseService) {}

  ngOnInit(): void {
    this.formSubmitButtonText = this.isUpdateForm() ? 'Save' : 'Create';

    if(this.isUpdateForm()) {
      this.horseService
        .getHorse(this.horseId)
        .subscribe(horse => this.model = horse);
    }
    else {
      this.model = { name: undefined, gender: undefined, id: undefined, birthdate: undefined, description: undefined};
    }
  }

  onSubmit(form: NgForm) {
    (this.isUpdateForm()
      ? this.horseService.updateHorse(this.horseId, this.model)
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
      if(this.isUpdateForm()) {
        msg = `Could not update horse with id="${this.horseId}": ${err.message}`;
      }
      else {
        msg = `Could not create horse ": ${err.message}`;
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
}

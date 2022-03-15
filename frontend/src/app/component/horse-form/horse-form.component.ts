import { Component, OnInit } from '@angular/core';
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

  model: HorseDto = { name: undefined, gender: undefined, id: undefined, birthdate: undefined, description: undefined};
  genders = Object.values(Gender);

  sent: boolean;
  gotResponse: boolean;
  successful: boolean;
  error: any;

  constructor(private fb: FormBuilder, private service: HorseService) {}

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    this.service.create(this.model).subscribe({
        next: (_) => {
          this.gotResponse = true;
          this.successful = true;
        },
        error: (err) => {
          this.gotResponse = true;
          this.successful = false;
          this.error = err;
        }
    });
    this.sent = true;
    form.resetForm();
  }
}

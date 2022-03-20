import {Gender} from './gender';

export class AddUpdateHorseDto {
  name: string;
  description?: string;
  birthdate: Date;
  gender: Gender;
  damId?: number;
  sireId?: number;

  constructor() {}
}

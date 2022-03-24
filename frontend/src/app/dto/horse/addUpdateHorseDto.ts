import {Gender} from './gender';

export class AddUpdateHorseDto {
  name: string;
  description?: string;
  birthdate: Date;
  gender: Gender;
  ownerId?: number;
  damId?: number;
  sireId?: number;
}

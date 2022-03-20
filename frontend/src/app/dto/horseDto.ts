import { Gender } from './gender';

export interface HorseDto {
  id?: number;
  name: string;
  description?: string;
  birthdate: Date;
  gender: Gender;
  dam?: HorseDto;
  sire?: HorseDto;
}

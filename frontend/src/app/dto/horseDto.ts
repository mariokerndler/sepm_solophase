import { Gender } from './gender';
import {OwnerDto} from './owner';

export interface HorseDto {
  id?: number;
  name: string;
  description?: string;
  birthdate: Date;
  gender: Gender;
  owner?: OwnerDto;
  dam?: HorseDto;
  sire?: HorseDto;
}

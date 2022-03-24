import { Gender } from './gender';
import { OwnerDto} from '../owner/ownerDto';

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

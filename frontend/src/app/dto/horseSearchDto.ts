import {Gender} from './gender';

export class HorseSearchDto {
  limit?: number;
  name?: string;
  description?: string;
  bornAfter?: string;
  gender?: Gender;

  isEmpty(): boolean {
    return !this.limit && !this.name && !this.description && !this.bornAfter && !this.gender;
  }
}

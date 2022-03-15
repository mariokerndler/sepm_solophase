import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HorseDto } from '../dto/horseDto';

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(
    private http: HttpClient,
  ) { }

  /**
   * Get all horses stored in the system
   *
   * @return observable list of found horses.
   */
  getAll(): Observable<HorseDto[]> {
    return this.http.get<HorseDto[]>(baseUri);
  }

  /**
   * Create a new horse and store it in the system
   *
   * @param horse The newly created horse.
   */
  create(horse: HorseDto): Observable<HorseDto> {
    return this.http.post<HorseDto>(baseUri, horse);
  }
}

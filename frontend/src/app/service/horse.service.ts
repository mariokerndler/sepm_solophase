import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable} from 'rxjs';
import { environment } from 'src/environments/environment';
import { HorseDto } from '../dto/horseDto';
import {AddUpdateHorseDto} from '../dto/addUpdateHorseDto';

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

  getHorse(id: number, generations?: number): Observable<HorseDto> {
    let queryParams = new HttpParams();
    if(generations != null) {
      queryParams = queryParams.set('generations', generations);
    }

    return this.http.get<HorseDto>(`${baseUri}/${id}`, {params: queryParams});
  }

  /**
   * Create a new horse and store it in the system
   *
   * @param horse The newly created horse.
   */
  create(horse: AddUpdateHorseDto): Observable<HorseDto> {
    return this.http.post<HorseDto>(baseUri, horse);
  }

  update(id: number, updateHorseDto: AddUpdateHorseDto): Observable<HorseDto> {
    return this.http.put<HorseDto>(`${baseUri}/${id}`, updateHorseDto);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${baseUri}/${id}`);
  }
}

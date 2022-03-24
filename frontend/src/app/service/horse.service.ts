import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, tap} from 'rxjs';
import { environment } from 'src/environments/environment';
import { HorseDto } from '../dto/horse/horseDto';
import {AddUpdateHorseDto} from '../dto/horse/addUpdateHorseDto';
import {NotificationService} from './notification.service';
import {HorseSearchDto} from '../dto/horse/horseSearchDto';

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(
    private http: HttpClient,
    private notificationService: NotificationService
  ) { }

  /**
   * Fetches all horses stored in the system
   *
   * @return observable list of found horses.
   */
  getAll(): Observable<HorseDto[]> {
    return this.http.get<HorseDto[]>(baseUri)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<HorseDto[]>('Fetching all horses'))
      );
  }

  /**
   * Fetches the {@link HorseDto horse} with the given {@link HorseDto#id id} from the system.
   *
   * @param id The {@link HorseDto#id id} of the {@link HorseDto horse} that will be fetched.
   * @param generations How many generations should be fetched.
   * @param errorNotificationAction Optional, will execute if the GET request fails.
   *
   * @return observable containing the fetched {@link HorseDto horse}.
   */
  getHorse(id: number, generations?: number, errorNotificationAction?: () => void): Observable<HorseDto> {
    let queryParams = new HttpParams();
    if(generations != null) {
      queryParams = queryParams.set('generations', generations);
    }

    return this.http.get<HorseDto>(baseUri + '/' + id, {params: queryParams})
      .pipe(
        catchError( (err) => {
          if(errorNotificationAction != null) {
            errorNotificationAction();
          }

          return this.notificationService.notifyFailedOperation<HorseDto>('Fetching horse')(err);
        }));
  }

  /**
   * Create a new {@link HorseDto horse} and store it in the system
   *
   * @param horse The newly created {@link HorseDto horse}.
   *
   * @return observable containing the newly created {@link HorseDto horse}.
   */
  create(horse: AddUpdateHorseDto): Observable<HorseDto> {
    return this.http.post<HorseDto>(baseUri, horse)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<HorseDto>('Creating new horse')),
        tap((successHorse) => {
          this.notificationService.notifySuccess(`Successfully created "${successHorse.name}"!`);
        }));
  }

  /**
   * Update an already existing {@link HorseDto horse}.
   *
   * @param id The {@link HorseDto#id id} of the horse that will be upgraded.
   * @param updateHorseDto The {@link AddUpdateHorseDto} containing the information to update the specified horse.
   *
   * @return observable containing the newly created {@link HorseDto horse}.
   */
  update(id: number, updateHorseDto: AddUpdateHorseDto): Observable<HorseDto> {
    return this.http.put<HorseDto>(baseUri + '/' + id, updateHorseDto)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<HorseDto>('Updating horse')),
        tap((successHorse) => {
          this.notificationService.notifySuccess(`Successfully updated "${successHorse.name}"!`);
        }));
  }

  /**
   * Delete an existing {@link HorseDto horse} from the system.
   *
   * @param id The {@link HorseDto#id id} of the horse that will be deleted.
   *
   * @return observable containing an empty response.
   */
  delete(id: number): Observable<any> {
    return this.http.delete(baseUri + '/' + id)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation('Deleting horse')),
        tap((_) => {
          this.notificationService.notifySuccess('Successfully deleted horse!');
        }));
  }

  /**
   * Search for all {@link HorseDto horses} that fit the given {@link HorseSearchDto}.
   *
   * @param filter A {@link HorseSearchDto} specifying which the filter parameter.
   *
   * @return observable containing all found horses.
   */
  search(filter: HorseSearchDto): Observable<HorseDto[]> {
    let queryParameters = new HttpParams();
    const filterObject = Object.entries(filter);

    for(const [key, value] of filterObject) {
      if (value == null) { continue; }
      queryParameters = queryParameters.set(key, value);
    }

    return this.http.get<HorseDto[]>(baseUri, { params: queryParameters })
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<HorseDto[]>('Searching horses'))
      );
  }
}

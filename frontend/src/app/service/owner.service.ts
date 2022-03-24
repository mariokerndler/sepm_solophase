import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {NotificationService} from './notification.service';
import {catchError, Observable, tap} from 'rxjs';
import {OwnerDto} from '../dto/owner/ownerDto';
import {AddOwnerDto} from '../dto/owner/addOwnerDto';

const baseUri = environment.backendUrl + '/owners';

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  constructor(
    private http: HttpClient,
    private notificationService: NotificationService
  ) { }

  /**
   * Fetches all {@link OwnerDto owners} stored in the system.
   *
   * @return observable list of found {@link OwnerDto owners}.
   */
  getAll(): Observable<OwnerDto[]> {
    return this.http.get<OwnerDto[]>(baseUri)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<OwnerDto[]>('Fetching all owners'))
      );
  }

  /**
   * Fetches the {@link OwnerDto owner} with the given {@link OwnerDto#id id} from the system.
   *
   * @param id The {@link OwnerDto#id id} of the {@link OwnerDto owner} that will be fetched.
   *
   * @return observable containing the fetched {@link OwnerDto owner}.
   */
  getOwner(id: number): Observable<OwnerDto> {
    return this.http.get<OwnerDto>(baseUri + '/' + id)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<OwnerDto>('Fetching owner'))
      );
  }

  /**
   * Create a new {@link OwnerDto owner} and store it in the system.
   *
   * @param owner The {@link AddOwnerDto} containing the information used to create a new {@link OwnerDto}.
   *
   * @return observable containing the newly created {@link OwnerDto owner}.
   */
  create(owner: AddOwnerDto): Observable<OwnerDto> {
    return this.http.post<OwnerDto>(baseUri, owner)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<OwnerDto>('Creating new owner')),
        tap((successOwner) => {
          this.notificationService.notifySuccess(`Successfully created "${successOwner.firstname}"!`);
        })
      );
  }
}

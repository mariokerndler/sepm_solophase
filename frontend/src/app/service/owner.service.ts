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

  getAll(): Observable<OwnerDto[]> {
    return this.http.get<OwnerDto[]>(baseUri)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<OwnerDto[]>('Fetching all owners'))
      );
  }

  getOwner(id: number): Observable<OwnerDto> {
    return this.http.get<OwnerDto>(baseUri + '/' + id)
      .pipe(
        catchError(this.notificationService.notifyFailedOperation<OwnerDto>('Fetching owner'))
      );
  }

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

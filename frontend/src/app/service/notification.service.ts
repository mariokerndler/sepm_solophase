import { Injectable } from '@angular/core';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import {HttpErrorResponse} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  /**
   * Notifies the user with a success message.
   *
   * @param message The message that will be displayed.
   */
  public notifySuccess(message: string) {
    Swal.fire({
      title: 'Success',
      text: message || '',
      icon: 'success'
    });
  }

  /**
   * Notifies the user with an error message.
   *
   * @param message The message that will be displayed.
   */
  public notifyError(message: string) {
    Swal.fire({
      title: 'Success',
      text: message || '',
      icon: 'error'
    });
  }

  /**
   * Notifies the user of a failed http operation and returns {@link EMPTY} to let the app continue.
   *
   * @param operation The name of the operation that failed.
   */
  public notifyFailedOperation<T>(operation = 'operation') {
    return (err: HttpErrorResponse): Observable<T> => {
      Swal.fire({
        title: `${operation} failed`,
        text: err.error.message || JSON.stringify(err.error).replace(/[{}]/g, ''),
        icon: 'error'
      });
      return EMPTY;
    };
  }
}

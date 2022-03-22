import { Injectable } from '@angular/core';
import {concat, debounceTime, distinctUntilChanged, filter, Observable, of, Subject, switchMap, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor() { }

  public registeredSearch<T>(searchTerms: Subject<string>,
                             loadingStateSetter: (loading: boolean) => void,
                             searchMethod: (term: string) => Observable<T[]>,
                             defaultValues: Observable<T[]> = of([])): Observable<T[]> {
    return concat(
      defaultValues,
      searchTerms.pipe(
        filter((searchTerm) => searchTerm !== null),
        distinctUntilChanged(),
        debounceTime(500),
        tap(_ => loadingStateSetter(true)),
        switchMap(searchTerm => searchMethod(searchTerm)
          .pipe(
            tap(_ => loadingStateSetter(false))))
      )
    );
  }
}

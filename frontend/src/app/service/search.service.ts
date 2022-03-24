import { Injectable } from '@angular/core';
import {concat, debounceTime, distinctUntilChanged, filter, Observable, of, Subject, switchMap, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor() { }

  // Based on this article: https://weblog.west-wind.com/posts/2019/Apr/08/Using-the-ngBootStrap-TypeAhead-Control-with-Dynamic-Data#sync
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

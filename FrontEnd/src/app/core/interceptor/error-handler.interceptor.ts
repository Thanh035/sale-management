import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpErrorResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap } from 'rxjs/operators';
import { EventManager, EventWithContent } from '../util/event-manager.service';
import { Router } from '@angular/router';


@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
  constructor(
    private eventManager: EventManager,
    private router: Router
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap({
        error: (err: HttpErrorResponse) => {
          if(err.status === 403) {
            this.router.navigate(['/admin/accessdenied']);
          }
          else if (!(err.status === 401 && (err.message === '' || err.url?.includes('api/v1.0/account')))) {
            this.eventManager.broadcast(new EventWithContent('springBootAngularTsApp.httpError', err));
          }
        }
      })
    );
  }
}

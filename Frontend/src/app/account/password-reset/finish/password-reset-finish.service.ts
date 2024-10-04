import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';

@Injectable({
  providedIn: 'root'
})
export class PasswordResetFinishService {

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(key: string, newPassword: string): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/v1.0/account/reset-password/finish'), { key, newPassword });
  }

}

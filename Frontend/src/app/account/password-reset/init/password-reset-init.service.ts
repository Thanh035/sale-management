import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { ResetPassword } from './password-reset-init.model';

@Injectable({
  providedIn: 'root'
})
export class PasswordResetInitService {

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(mail: string): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/v1.0/account/reset-password/init'), mail);
  }

}

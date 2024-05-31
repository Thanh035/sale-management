import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApplicationConfigService {
  private endpointPrefix = 'http://localhost:8096/';

  // setEndpointPrefix(endpointPrefix: string): void {
  //   this.endpointPrefix = endpointPrefix;
  // }

  getEndpointFor(api: string): string {
    return `${this.endpointPrefix}${api}`;
  }
}

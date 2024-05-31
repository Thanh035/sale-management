import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error-admin',
  templateUrl: './error.component.html'
})
export class ErrorOfAdminComponent implements OnInit  {

  errorMessage?: string;
  statusError?: string;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(routeData => {
      if (routeData['errorMessage']) {
        this.errorMessage = routeData['errorMessage'];
      }
      if (routeData['statusError']) {
        this.statusError = routeData['statusError'];
      }
    });
  }

}




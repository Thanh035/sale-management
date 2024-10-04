import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
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



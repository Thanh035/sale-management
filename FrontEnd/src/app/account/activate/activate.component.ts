import { Component, OnInit } from '@angular/core';
import { ActivateService } from './activate.service';
import { ActivatedRoute } from '@angular/router';
import { mergeMap } from 'rxjs';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
})
export class ActivateComponent implements OnInit {
  error = false;
  success = false;

  constructor(private activateService: ActivateService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.pipe(mergeMap(params => this.activateService.get(params['key']))).subscribe({
      next: () => (this.success = true),
      error: () => (this.error = true),
    });
  }
}

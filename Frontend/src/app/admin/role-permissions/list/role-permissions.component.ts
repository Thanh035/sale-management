import { Component } from '@angular/core';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';
import { combineLatest } from 'rxjs';
import { RoleDTO } from '../role-permission.model';
import { RolesService } from '../service/roles.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-role-permissions',
  templateUrl: './role-permissions.component.html',
})
export class RolePermissionsComponent {

  roles: RoleDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  // filter!: '';

  constructor(
    private roleService: RolesService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  loadChanged() {
    this.loadAll();
  }

  trackIdentity(_index: number, item: RoleDTO): number {
    return item.id!;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        // sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
        // filter: this.filter
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([
      this.activatedRoute.data,
      this.activatedRoute.queryParamMap,
    ]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      // const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      // this.predicate = sort[0];
      // this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  loadAll(): void {
    this.roleService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage
      })
      .subscribe({
        next: (res: HttpResponse<RoleDTO[]>) => {
          this.onSuccess(res.body, res.headers);
        },
      });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(roles: RoleDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.roles = roles;
  }

}


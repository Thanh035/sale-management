import { ActivatedRoute, Router } from '@angular/router';
import { User, UserDTO } from './../user-management.model';
import { Component, OnInit } from '@angular/core';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { UserManagementService } from '../service/user-management.service';
import { combineLatest } from 'rxjs';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';
import { UserManagementDeleteDialogComponent } from '../delete/user-management-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UAParser } from 'ua-parser-js';
import { UserManagementCreateDialogComponent } from '../create/user-management-create-dialog.component';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
})
export class UserManagementComponent implements OnInit{
  currentAccount: Account | null = null;
  users: UserDTO[] | null = null;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  filter = '';

  constructor(
    private userService: UserManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
  }

  setActive(userId:number, isActivated: boolean): void {
    this.userService.activate(isActivated,userId).subscribe(() => this.loadAll());
  }

  trackIdentity(_index: number, item: User): number {
    return item.id!;
  }

  deleteUser(user: User): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  loadAll(): void {
    this.userService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        filter: this.filter
      })
      .subscribe({
        next: (res: HttpResponse<UserDTO[]>) => {
          this.onSuccess(res.body, res.headers);
        }
      });
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
        filter: this.filter
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: UserDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
  }

  public displayBrowser(browserInfo: string) : string {
    const parser = new UAParser();
    const result = parser.setUA(browserInfo).getResult();
    return `${result.browser.name} ${result.browser.version} trên ${result.os.name} ${result.os.version}`;
  }

  public createUser() :void {
    const modalRef = this.modalService.open(
      UserManagementCreateDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    // modalRef.componentInstance.productName = this.productName;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'success') {
        this.loadAll();
      }
    });
  }
}

import { Routes } from "@angular/router";
import { SecurityComponent } from "./security.component";
import { UserRouteAccessService } from "src/app/core/auth/user-route-access.service";

export const SECURITY_ROUTE: Routes = [
  {
    path: '',
    component: SecurityComponent,
    canActivate: [UserRouteAccessService],
  },
]

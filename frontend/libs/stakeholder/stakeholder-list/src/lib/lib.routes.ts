import { Routes } from '@angular/router';
import { StakeholderListComponent } from './stakeholder-list/stakeholder-list.component';
import { authGuard } from '@realworld/auth/data-access/src';

export const STAKEHOLDER_LIST_ROUTES: Routes = [
    {
        path: '',
        component: StakeholderListComponent,
        children: [
            {
                path: '',
                pathMatch: 'full',
                component: StakeholderListComponent,
                canActivate: [authGuard],
            }
        ],
    },
];

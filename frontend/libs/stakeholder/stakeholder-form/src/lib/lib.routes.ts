import { Route, Routes } from '@angular/router';
import { StakeholderFormComponent } from './stakeholder-form/stakeholder-form.component';
import { authGuard } from '@realworld/auth/data-access/src';

export const STAKEHOLDER_FORM_ROUTES: Routes = [
    {
        path: '',
        component: StakeholderFormComponent,
        children: [
            {
                path: '',
                pathMatch: 'full',
                component: StakeholderFormComponent,
                canActivate: [authGuard],
            }
        ],
    },
];

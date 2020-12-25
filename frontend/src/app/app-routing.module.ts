import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserProfileComponent} from './_user/user-profile/user-profile.component';
import {ManagerWorkflowComponent} from './_manager/manager-workflow/manager-workflow.component';
import {LoginComponent} from './login/login.component';
import {NotFoundComponent} from './_static/not-found/not-found.component';
import {AuthGuard} from './_guards/auth.guard';
import {ArchiveDocumentsComponent} from "./archive-documents/archive-documents.component";
import { MyDeptBudgetComponent } from './my-dept-budget/my-dept-budget.component';
import { GroupManagmentComponent } from './group-managment/group-managment.component';
import { from } from 'rxjs';
import { RegistrationComponent } from './registration/registration.component';
const routes: Routes = [
    {
        path: 'lk/account',
        component: UserProfileComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'groups/all',
        component: ManagerWorkflowComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'my-budget/my-budget',
        component: MyDeptBudgetComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'group/management',
        component: GroupManagmentComponent,
        canActivate: [AuthGuard]
    },
   
    {
        path: 'groups/my-groups',
        component: ArchiveDocumentsComponent,
        canActivate: [AuthGuard]
    },
    
    {path: 'login', component: LoginComponent},
    {path: 'registration', component: RegistrationComponent},
    {path: '**', component: NotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

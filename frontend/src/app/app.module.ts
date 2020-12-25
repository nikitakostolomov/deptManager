import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MDBBootstrapModule} from 'angular-bootstrap-md';
import {LeftNavbarComponent} from './left-navbar/left-navbar.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UserProfileComponent} from './_user/user-profile/user-profile.component';
import {ManagerWorkflowComponent} from './_manager/manager-workflow/manager-workflow.component';
import {ManagerModalAnswerComponent} from './_manager/manager-modal-answer/manager-modal-answer.component';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {registerLocaleData} from '@angular/common';
import localeRu from '@angular/common/locales/ru';
import {NotifierModule} from 'angular-notifier';
import {OwlDateTimeModule, OwlNativeDateTimeModule, OwlDateTimeIntl} from 'ng-pick-datetime';
import {LoadingSpinerComponent} from './loading-spiner/loading-spiner.component';
import {LoginComponent} from './login/login.component';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers/error.interceptor';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import {NotFoundComponent} from './_static/not-found/not-found.component';
import {HeaderComponent} from './header/header.component';
import {ArchiveDocumentsComponent} from './archive-documents/archive-documents.component';
import {MyDeptBudgetComponent } from './my-dept-budget/my-dept-budget.component';
import { GroupManagmentComponent } from './group-managment/group-managment.component';
import { GroupManagementChildComponent } from './group-management-child/group-management-child.component';
import { RegistrationComponent } from './registration/registration.component';

registerLocaleData(localeRu, 'ru');


export class DefaultIntl extends OwlDateTimeIntl {
    cancelBtnLabel = 'Закрыть';
    setBtnLabel = 'Выбрать';
}

@NgModule({
    declarations: [
        AppComponent,
        LeftNavbarComponent,
        UserProfileComponent,
        ManagerWorkflowComponent,
        ManagerModalAnswerComponent,
        HeaderComponent,
        LoadingSpinerComponent,
        LoginComponent,
        NotFoundComponent,
        ArchiveDocumentsComponent,
        MyDeptBudgetComponent,
        GroupManagmentComponent,
        GroupManagementChildComponent,
        RegistrationComponent,
    ],
    imports: [
        BrowserModule,
        RouterModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MDBBootstrapModule.forRoot(),
        FormsModule,
        ReactiveFormsModule,
        InfiniteScrollModule,
        NotifierModule.withConfig({
            behaviour: {
                autoHide: 2000
            }
        }),
        OwlDateTimeModule,
        OwlNativeDateTimeModule,
    ],
    providers: [
        {provide: LocationStrategy, useClass: HashLocationStrategy},
        {provide: LOCALE_ID, useValue: 'ru'},
        {provide: OwlDateTimeIntl, useClass: DefaultIntl},
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    
    ],
    bootstrap: [AppComponent],
    schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule {
}

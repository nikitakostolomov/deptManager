import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../_services/authentication.service';
import {first} from 'rxjs/operators';
import {Title} from '@angular/platform-browser';
import {NotifierService} from 'angular-notifier';
import {throwError} from 'rxjs';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    resetPassword: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    currentInfo;
    isLoginForm = true;
    loadingReset = false;
    errorReset;
    submittedReset;
    alertSuccess = '';

    constructor(private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private router: Router,
                private authenticationService: AuthenticationService,
                private titleService: Title,
                private notifier: NotifierService) {
        this.titleService.setTitle('Dept Manager');
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required],
        });
    
        this.authenticationService.logout();
        this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/lk/account';
    }

    get f() {
        return this.loginForm.controls;
    }

    get r() {
        return this.resetPassword.controls;
    }

    onSubmit() {
        this.loading = true;
        this.error = null;
        this.authLogin();
        
    }

   

    authLogin() {
        this.submitted = true;
        if (this.loginForm.invalid) {
            this.loading = false;
            return;
        }
        this.authenticationService.login(this.f.username.value.trim(), this.f.password.value.trim())
            .pipe(first())
            .subscribe(
                data => {
                   
                        this.router.navigate(["lk/account"]);
                  
                },
                error => {
                    this.loading = false;
                    this.error = 'Войти не удалось';
                    if (error.indexOf('not found') !== -1) {
                        this.error = 'Пользователь не найден';
                    }
                });
    }
}

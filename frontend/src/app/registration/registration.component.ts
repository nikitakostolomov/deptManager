import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../_services/authentication.service';
import {first} from 'rxjs/operators';
import {Title} from '@angular/platform-browser';
import {NotifierService} from 'angular-notifier';
import {throwError} from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
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
            name: ['', Validators.required],
            surname: ['', Validators.required],
        });
    
        this.authenticationService.logout();
        this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/lk/account';
    }

    get f() {
        return this.loginForm.controls;
    }

    onSubmit() {
        this.loading = true;
        this.error = null;
        this.registration();
        
    }

   

    registration() {
        this.submitted = true;
        if (this.loginForm.invalid) {
            this.loading = false;
            return;
        }
        this.authenticationService.registration(this.f.username.value.trim(), this.f.password.value.trim(),
        this.f.name.value.trim(),this.f.surname.value.trim() )
            .pipe(first())
            .subscribe(
                data => {
                   
                        this.router.navigate(["lk/account"]);
                  
                },
                error => {
                    this.loading = false;
                    this.error = 'Пользователь с таким логином уже существует!';
                    if (error.indexOf('not found') !== -1) {
                        this.error = 'Пользователь не найден';
                    }
                });
    }
}


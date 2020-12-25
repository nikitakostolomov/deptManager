import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../_services/authentication.service';
interface IUser {
    id: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    userRole?: any;
    jwt?: string;
}

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})


export class HeaderComponent implements OnInit {
    currentUser: IUser;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    }

    ngOnInit() {
    }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }

    login() {
        if (this.currentUser) {
            this.router.navigate(['lk/account']);
        }
    }
}

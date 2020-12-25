import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthenticationService} from "../_services/authentication.service";
import {HttpService} from "../http.service";

interface INotificationNewApplication {
    workflow: number;
    financialDocument: number;
}

@Component({
    selector: 'app-left-navbar',
    templateUrl: './left-navbar.component.html',
    styleUrls: ['./left-navbar.component.css']
})
export class LeftNavbarComponent implements OnInit {
    linksAdmin = true;
    notificationNewApplication: INotificationNewApplication = {
        workflow: 0,
        financialDocument: 0
    };

    constructor(private authenticationService: AuthenticationService, private httpService: HttpService) {
        this.authenticationService.currentUser.subscribe(x => {
            if (!x) {
                return;
            }
                this.linksAdmin = true;
        });
    }

    ngOnInit() {
        this.updateNotification();
    }

    updateNotification() {
        // if (this.linksUser) {
        //     return;
        // } else {
        //     this.httpService.getNotificationForNavbar(this.linksUser).subscribe((res: INotificationNewApplication) => {
        //         this.notificationNewApplication = res;
        //     });
        // }
    }
}

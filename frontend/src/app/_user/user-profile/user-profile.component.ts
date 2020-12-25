import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {IPersonInfo} from '../../_interfaces/IPersonInfo';
import {HttpService} from '../../http.service';

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
    anket: IPersonInfo;
    form = this.fb.group({
        file: [null, Validators.required],
    });
    constructor(private fb: FormBuilder, private cd: ChangeDetectorRef, private http: HttpService) {
    }

    ngOnInit() {
        this.http.getUserData().subscribe((res: any) => {
            this.anket = res;
        });
    }
}
import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class HttpService {
    baseUrl = '/api';
    constructor(private http: HttpClient) {
    }

    getDateForSend(dateParse: string): string {
        Date.prototype.toJSON = function () {
            const hoursDiff = this.getHours() - this.getTimezoneOffset() / 60;
            this.setHours(hoursDiff);
            return this.toISOString();
        };
        const date = JSON.stringify(dateParse)
            .replace('T', ' ')
            .split('');
        date.splice(date.length - 9, 9).shift();
        const sendingDate = date.join('').replace('\"', '');
        return sendingDate;
    }

    getUserData() {
        const endpoint = `${this.baseUrl}/person/account`;
        return this.http.get(endpoint);
    }

}

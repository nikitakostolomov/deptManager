import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {User} from '../_interfaces/user';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;
    baseUrl = '/api';
    ttl = 28800000;
    constructor(private http: HttpClient) {
        const user = this.getCurrentUser();
        this.currentUserSubject = new BehaviorSubject<User>(user);
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }
    login(username: string, password: string) {
        const endpoint = `${this.baseUrl}/login`;
        return this.http.post<any>(endpoint, {login: username, password})
            .pipe(map(user => {
                if (user && user.jwt) {
                    const now = new Date();
                    user.expiry = now.getTime() + this.ttl;
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                }
                return user;
            }));
    }

    registration(username: string, password: string, name: string, surname: string) {
        const endpoint = `${this.baseUrl}/login/register`;
        return this.http.post<any>(endpoint, {login: username, password, name, surname})
            .pipe(map(user => {
                if (user && user.jwt) {
                    const now = new Date();
                    user.expiry = now.getTime() + this.ttl;
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                }
                return user;
            }));
    }

    logout() {
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }

    getCurrentUser() {
        const user = JSON.parse(localStorage.getItem('currentUser'));
        const now = new Date();
        if (!user) {
            return null;
        }
        if (now.getTime() > user.expiry) {
            localStorage.removeItem('currentUser');
            return null;
        }
        return user;
    }
}

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../_interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl: string;
  constructor(private http: HttpClient) {
    this.baseUrl = `src`;
  }

  getAll() {
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }

  getById(id: number) {
    return this.http.get<User>(`${this.baseUrl}/users/${id}`);
  }
}

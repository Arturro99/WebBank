import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './model/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  admin: any  = {
    login: 'admin',
    password: 'Proba666',
  };

  constructor(private http: HttpClient) { }

  getUsers(): any {
    return this.http.get('https://localhost:8181/WebBank/restapi/model.client');
  }

  addUser(user: User): any {
    return this.http.post('https://localhost:8181/WebBank/restapi/model.client', user);
  }

  updateUser(user: User): any {
    return this.http.put('https://localhost:8181/WebBank/restapi/model.client/forspa/' + user.login, user);
  }
}

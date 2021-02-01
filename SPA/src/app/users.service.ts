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
    // tslint:disable-next-line:max-line-length
    // const headers = new HttpHeaders().set('Authorization: ', 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJFTVBMT1lFRVMsQURNSU5TLENMSUVOVFMiLCJpc3MiOiJXZWJBcHBCYW5rIFJlc3QgQXBpIiwiZXhwIjoxNjEyMjA3MzI5fQ.YX3r3P4C8IksR5Sjgw7nncG1q7BV_xpxTTRJVQJlnpw');
    return this.http.get('https://localhost:8181/WebBank/restapi/model.client');
  }

  addUser(user: User): any {
    return this.http.post('https://localhost:8181/WebBank/restapi/model.client', user);
  }
}

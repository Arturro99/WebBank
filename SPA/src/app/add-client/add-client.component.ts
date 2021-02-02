import { Component, OnInit } from '@angular/core';
import {UsersService} from '../users.service';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.less']
})
export class AddClientComponent {
  userLogin = '';
  userPassword = '';
  userName = '';
  userSurname = '';
  userAge = 0;

  getUser(): any {
    return {
      login: this.userLogin,
      password: this.userPassword,
      name: this.userName,
      surname: this.userSurname,
      age: this.userAge,
    };
  }

  constructor(private usersService: UsersService) { }

  addUser(): void {
    this.usersService.addUser(this.getUser()).subscribe();
  }
}

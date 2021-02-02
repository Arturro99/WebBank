import {Component, Input, OnInit} from '@angular/core';
import { User } from '../model/user';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.less']
})
export class TableComponent {
  public user: User = {
    pid: '',
    name: '',
    surname: '',
    login: '',
    age: 0,
    blocked: false,
  };

  users: User[] = [];

  constructor(private usersService: UsersService) {  this.refreshUsers(); }

  refreshUsers(): void {
    this.usersService.getUsers().subscribe((response: User[]) => { this.users = response; }  );
  }

  setUser(user: User): void {
    this.user = {
      pid: user.pid,
      name: user.name,
      surname: user.surname,
      login: user.login,
      age: user.age,
      blocked: user.blocked,
    };
  }

  updateUser():void {
    this.usersService.updateUser(this.user).subscribe().pipe(
      setTimeout(
        () => this.refreshUsers(),
        500)
    );

  }

  switchBlock() {
    this.user.blocked = !this.user.blocked;
    this.updateUser();
  }
}

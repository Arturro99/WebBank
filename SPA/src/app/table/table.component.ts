import {Component, Input, OnInit} from '@angular/core';
import { User } from '../model/user';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.less']
})
export class TableComponent {
  user: User = {
    pid: 'string',
    name: 'aaaaaa',
    surname: 'aaa',
    login: 'Azerty',
    age: 12,
    blocked: false,
  };

  users: User[] = [];

  constructor(private usersService: UsersService) { }

  refreshUsers(): void {
    this.usersService.getUsers().subscribe((response: User[]) => { this.users = response; }  );
  }
}

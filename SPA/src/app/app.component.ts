import { Component } from '@angular/core';
import { User } from './model/user';
import { UsersService } from './users.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  users: User[] = [];
  token = 'a';
  user: any = {
    name: 'aaaaaa',
    surname: 'aaa',
    login: 'Azerty',
    password: 'Haslo123*',
    age: 12,
    blocked: false,
  };

  constructor(public usersService: UsersService) {
  }

  refreshUsers(): void {
    this.usersService.getUsers().subscribe((response: User[]) => { this.users = response; }  );
  }

  addUser(): void {
    this.usersService.addUser(this.user).subscribe();
  }
}

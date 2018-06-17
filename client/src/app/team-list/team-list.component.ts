import { Component, OnInit } from '@angular/core';
import { TEAMS } from '../models/mock-teams'

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})
export class TeamListComponent implements OnInit {

  teams = TEAMS;
  
  constructor() { }

  ngOnInit() {
  }

}

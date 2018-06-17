import { Component, OnInit } from '@angular/core';

import { Team } from '../models/team'
import { TEAMS } from '../models/mock-teams'

@Component({
  selector: 'app-team-input',
  templateUrl: './team-input.component.html',
  styleUrls: ['./team-input.component.css']
})
export class TeamInputComponent implements OnInit {

  selectedTeam = this.transfereService.getData();

  constructor(private transfereService:TransfereService) { }

  ngOnInit() {
  }

}

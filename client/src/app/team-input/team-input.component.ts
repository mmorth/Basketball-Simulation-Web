import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Team } from '../models/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team-input',
  templateUrl: './team-input.component.html',
  styleUrls: ['./team-input.component.css']
})
export class TeamInputComponent implements OnInit {

  team: Team;

  constructor(private route: ActivatedRoute, private teamService: TeamService, private location: Location) { }

  ngOnInit(): void {
  	this.getTeam();
  }

  getTeam(): void {
  	const id = +this.route.snapshot.paramMap.get('id');
  	this.teamService.getTeam(id)
    .subscribe(team => this.team = team);
  }

}

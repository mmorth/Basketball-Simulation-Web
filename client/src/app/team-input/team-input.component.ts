import { Component, OnInit, Input } from '@angular/core';
import { Team } from '../models/team';

@Component({
  selector: 'app-team-input',
  templateUrl: './team-input.component.html',
  styleUrls: ['./team-input.component.css']
})
export class TeamInputComponent implements OnInit {

  @Input() team: Team;

  constructor() { }

  ngOnInit() {
  }

  clearInputs() {
  	document.getElementById("teamName").value = "";
  }

}

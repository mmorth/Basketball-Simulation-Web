import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GameSimulationComponent } from './game-simulation/game-simulation.component';
import { TeamDetailsComponent } from './team-details/team-details.component';
import { TeamInputComponent } from './team-input/team-input.component';
import { PlayerInputComponent } from './player-input/player-input.component';

const routes: Routes = [
  { path: 'game-simulation', component: GameSimulationComponent },
  { path: 'team-details', component: TeamDetailsComponent },
  { path: 'team-details/:id', component: TeamInputComponent },
  { path: 'team-details/create', component: TeamInputComponent },
  { path: 'team-details/:teamID/:playerID/:playerCoachSelector', component: PlayerInputComponent },
  { path: 'team-details/:teamID/create/:playerCoachSelector', component: PlayerInputComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
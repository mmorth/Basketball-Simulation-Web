import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GameSimulationComponent } from './game-simulation/game-simulation.component';
import { TeamDetailsComponent } from './team-details/team-details.component';

const routes: Routes = [
  { path: 'game-simulation', component: GameSimulationComponent }
  { path: 'team-details', component: TeamDetailsComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
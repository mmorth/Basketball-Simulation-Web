import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; 
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';
import { TeamInputComponent } from './team-input/team-input.component';
import { GameSimulationComponent } from './game-simulation/game-simulation.component';
import { TeamDetailsComponent } from './team-details/team-details.component';
import { TeamListComponent } from './team-list/team-list.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { GameSimulationInputComponent } from './game-simulation-input/game-simulation-input.component';
import { AppRoutingModule } from './/app-routing.module';
import { PlayerInputComponent } from './player-input/player-input.component';

@NgModule({
  declarations: [
    AppComponent,
    ScoreboardComponent,
    TeamInputComponent,
    GameSimulationComponent,
    TeamDetailsComponent,
    TeamListComponent,
    NavigationBarComponent,
    GameSimulationInputComponent,
    PlayerInputComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

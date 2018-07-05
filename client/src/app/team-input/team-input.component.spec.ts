import { async, ComponentFixture, TestBed } from '@angular/core/testing';

// import { TeamInputComponent } from './team-input.component';

// describe('TeamInputComponent', () => {
//   let component: TeamInputComponent;
//   let fixture: ComponentFixture<TeamInputComponent>;

//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [ TeamInputComponent ]
//     })
//     .compileComponents();
//   }));

//   beforeEach(() => {
//     fixture = TestBed.createComponent(TeamInputComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });
// });

import { Observable, of } from 'rxjs';

import { Team } from '../models/team';
import { TeamService } from '../services/team.service';
import { TeamInputComponent } from './team-input.component';

const USER_OBJECT: Team = { id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 };
 
class MockTeam {

  public getTeam(): Observable<Team> {
    return of(USER_OBJECT);
  }
}

let component: TeamInputComponent;
let userService: TeamService;
 
beforeEach(() => {
  TestBed.configureTestingModule({
    declarations: [
      TeamInputComponent
    ],
    providers: [
      {provide: TeamService, useClass: MockTeam}
    ]
  });
  component = TestBed.createComponent(TeamInputComponent).componentInstance;
  userService = TestBed.get(TeamService);

  it('should not set team when no parameter id'), () => {
    component.ngOnInit();
    expect(component.team).toBe(null);
  }

});

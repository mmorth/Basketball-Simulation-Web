// import { TestBed, inject } from '@angular/core/testing';
// import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';

// import { Team } from '../models/team';
// import { TeamService } from './team.service';



// describe('TeamService Tests', () => {
//   let httpClient: HttpClient;
//   let httpTestingController: HttpTestingController;

//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [HttpClientTestingModule],
//       providers: [TeamService]
//     });

//     // Inject the http service and test controller for each test
//     httpClient = TestBed.get(HttpClient);
//     httpTestingController = TestBed.get(HttpTestingController);
//   });

//   /// Tests begin ///
//   it('can get a Team', () => {
//     const testData: Team = { id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 };

//     // Make an HTTP GET request
//     httpClient.get<Team>('http://localhost:8080/api/teams/1/')
//       .subscribe(data =>
//         // When observable resolves, result should match test data
//         expect(data).toEqual(testData)
//       );

//     // The following `expectOne()` will match the request's URL.
//     // If no requests or multiple requests matched that URL
//     // `expectOne()` would throw.
//     const req = httpTestingController.expectOne('http://localhost:8080/api/teams/1/');

//     // Assert that the request is a GET.
//     expect(req.request.method).toEqual('GET');

//     // Respond with mock data, causing Observable to resolve.
//     // Subscribe callback asserts that correct data was returned.
//     req.flush(testData);

//     // Finally, assert that there are no outstanding requests.
//     httpTestingController.verify();
//   });

//   it('can get multiple teams', () => {
//     const testData: Team[] = [ { id: 1, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 }, 
//       { id: 2, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 },
//       { id: 3, name: 'Test Team', offensiveRating: 90, defensiveRating: 90 } ];

//     // Make an HTTP GET request
//     httpClient.get<Team[]>('http://localhost:8080/api/teams/')
//       .subscribe(data =>
//         // When observable resolves, result should match test data
//         expect(data).toEqual(testData)
//       );

//     // The following `expectOne()` will match the request's URL.
//     // If no requests or multiple requests matched that URL
//     // `expectOne()` would throw.
//     const req = httpTestingController.expectOne('http://localhost:8080/api/teams/');

//     // Assert that the request is a GET.
//     expect(req.request.method).toEqual('GET');

//     // Respond with mock data, causing Observable to resolve.
//     // Subscribe callback asserts that correct data was returned.
//     req.flush(testData);

//     // Finally, assert that there are no outstanding requests.
//     httpTestingController.verify();
//   });

//   afterEach(() => {
//     // After every test, assert that there are no more pending requests.
//     httpTestingController.verify();
//   });

//   it('should be created', inject([TeamService], (service: TeamService) => {
//     expect(service).toBeTruthy();
//   }));
// });


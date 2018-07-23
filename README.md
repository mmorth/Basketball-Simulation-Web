# Basketball Simulation Web
A basketball simulation web application using an Angular front-end, a Java spring back-end, and a MySQL database. The current features of the project are shown below:
* User can create teams and assign players and coaches to those teams.
* User can simulate a basketball game between two teams
* Improved the simulation by adding player statistics, player substitutions, and a more accurate simulation algorithm.
The future goals for this project are listed below:
* Add players and a coach to each team
* Improve the simulation by adding player statistics, player substitutions, and a more accurate simulation algorithm.
* Create the ability for the user to simulate a season
* Create the ability for the user to simulate a career
* Allow multiple users to face each other in a game simulation, season, or career. (Add multiplayer) 


## Getting Started
These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites
* [Java 8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Installation Steps
* [Maven](https://maven.apache.org/install.html) - Installation Steps
* [Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html) - Installation Steps
* [Angular CLI](https://angular.io/guide/quickstart) - Installation Steps
* [MySQL](https://dev.mysql.com/doc/refman/8.0/en/installing.html) - Installation Steps

### Project Setup
Using Git Bash or the Linux Terminal, navigate to the folder where you want the project located

```
git clone https://github.com/mmorth/Basketball-Simulation-Web.git
```
Open two terminal windows and navigate to the client folder in one and the server folder in the other.

In the client folder terminal, run the following command
```
ng serve
```
In the server folder terminal, run the following command
```
mvn spring-boot:run
```
In a web browser, navigate to localhost:4200/#

You should now see the application navigation bar appear.

## Built With
* [Angular](https://angular.io/docs) - Front-end
* [Spring](https://spring.io/docs) - Back-end
* [MySQL](https://dev.mysql.com/doc/) - Database

## Authors
Matthew Orth

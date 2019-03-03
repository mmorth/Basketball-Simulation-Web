# Basketball Simulation Web
A basketball simulation web application using an Angular front-end, a Java spring back-end, and a MySQL database. The current features of the project are shown below:
* User can create teams and assign players and coaches to those teams.
* User can simulate a basketball game between two teams
* Improved the simulation by adding player statistics, player substitutions, and a more accurate simulation algorithm.

The future goals for this project are listed below:
* Create the ability for the user to simulate a season
* Create the ability for the user to simulate a career
* Allow multiple users to face each other in a game simulation, season, or career. (Add multiplayer) 


## Getting Started
These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites
* [Java 8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Installation Steps
* [Visual Studio Code](https://code.visualstudio.com/) - Download for Client code
* [Eclipse STS](https://spring.io/tools) - Download for Server code
* [Angular CLI](https://cli.angular.io/) - Installation Steps
* [MySQL](https://support.rackspace.com/how-to/installing-mysql-server-on-ubuntu/) - Installation Steps
* [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) - Download

### Project Setup

## Initial Setup:
From the terminal, clone the master repository in the desired directory location:
```	
git clone https://github.com/mmorth/Basketball-Simulation-Web
```

## Database:
MySQL Install Steps:
1. Follow all steps from webpage to configure mysql
	https://support.rackspace.com/how-to/installing-mysql-server-on-ubuntu/
2. Create db_basketball_simulation table
3. Create database user:
	username = bballuser
	password = bballsim
4. Grant all permissions to bballuser

## Client:
# Angular Install Steps:
1. Navigate to client folder in Terminal:
```
cd client
```
2. Install Angular:
```
npm install -g @angular/cli
```
3. Update Angular:
```
ng update @angular/cli @angular/core
```
4. Run Angular Client Code:
```
ng serve
```

## Server:
# Spring Install Steps:
1. Open server workspace from eclipse
2. Configure Run As:
	Project: server
	Main Type: com.mattheworth.server.ServerApplication
3. Press run button

## Built With
* [Angular](https://angular.io/docs) - Front-end
* [Spring](https://spring.io/docs) - Back-end
* [MySQL](https://dev.mysql.com/doc/) - Database

## Authors
Matthew Orth

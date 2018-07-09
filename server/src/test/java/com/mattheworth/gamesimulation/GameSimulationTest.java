package com.mattheworth.gamesimulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.mattheworth.server.GameSimulation;
import com.mattheworth.server.Team;

public class GameSimulationTest {
	
	Team awayTeam;
	Team homeTeam;
	GameSimulation gameSimulation;

   @Before
   public void initSetup() {
	   awayTeam = new Team();
	   awayTeam.setId(1);
	   awayTeam.setName("awayTeam");
	   awayTeam.setOffensiveRating(100);
	   awayTeam.setDefensiveRating(100);
	   
	   homeTeam = new Team();
	   homeTeam.setId(2);
	   homeTeam.setName("homeTeam");
	   homeTeam.setOffensiveRating(100);
	   homeTeam.setDefensiveRating(100);
	   
	   gameSimulation = new GameSimulation(awayTeam, homeTeam);
   }
   
   @Test
   public void testSimulatePossession() {	
	   gameSimulation.simulationPossession(100);
	   
	   assertEquals(gameSimulation.getPossessionsRemaining(), -1);
	   System.out.println(gameSimulation.getAwayTeamScore());
	   assertFalse(gameSimulation.getAwayTeamScore() == 0);
	   assertFalse(gameSimulation.getAwayTeamFirstQuarterScore() == 0);
	   assertFalse(gameSimulation.getAwayTeamSecondQuarterScore() == 0);
	   assertFalse(gameSimulation.getAwayTeamThirdQuarterScore() == 0);
	   assertFalse(gameSimulation.getAwayTeamFourthQuarterScore() == 0);
	   
	   assertFalse(gameSimulation.getHomeTeamScore() == 0);
	   assertFalse(gameSimulation.getHomeTeamFirstQuarterScore() == 0);
	   assertFalse(gameSimulation.getHomeTeamSecondQuarterScore() == 0);
	   assertFalse(gameSimulation.getHomeTeamThirdQuarterScore() == 0);
	   assertFalse(gameSimulation.getHomeTeamFourthQuarterScore() == 0);
	   
   }
}

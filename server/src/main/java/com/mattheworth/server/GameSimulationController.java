package com.mattheworth.server;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins="*")
@Controller
@RequestMapping(path="/api/game-simulation")
public class GameSimulationController {
	@Autowired
	private GameSimulationRepository gameSimulationRepository;
	
	@Autowired 
	private TeamRepository teamRepository;

	// Creates a new team
	@RequestMapping(path="/{awayTeamID}/{homeTeamID}", method = RequestMethod.POST)
	public @ResponseBody String createGameSimulation(@PathVariable long awayTeamID, @PathVariable long homeTeamID) {

		Team awayTeam = teamRepository.findById(awayTeamID).get();
		Team homeTeam = teamRepository.findById(homeTeamID).get();
		
		GameSimulation gameSimulation = new GameSimulation();
		
		gameSimulation.setAwayTeam(awayTeam);
		gameSimulation.setHomeTeam(homeTeam);
		gameSimulation.setPossessionsRemaining(99);
		gameSimulation.setOvertime(false);
		gameSimulation.setHomeTeamScore(0);
		gameSimulation.setAwayTeamScore(0);
		gameSimulation.setHomeTeamPreviousQuarterScore(0);
		gameSimulation.setAwayTeamPreviousQuarterScore(0);
		
		gameSimulationRepository.save(gameSimulation);
		
		return "GameSimulation Created";
	}
//	
//	// Deletes a team
//	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
//	public @ResponseBody String deleteTeam(@PathVariable long id) {
//		Team deleteTeam = teamRepository.findById(id).get();
//		teamRepository.delete(deleteTeam);
//		
//		return "Team Deleted";
//	}
//	
//	// Updates a team
//	@RequestMapping(path="/{id}", method = RequestMethod.PUT)
//	public @ResponseBody String updateTeam(@PathVariable long id, @RequestBody Team jsonTeam) {
//		Team updateTeam = teamRepository.findById(id).get();
//		
//		updateTeam.setOffensiveRating(jsonTeam.getOffensiveRating());
//		updateTeam.setDefensiveRating(jsonTeam.getDefensiveRating());
//		teamRepository.save(updateTeam);
//		
//		return "Team Updated";
//	}
//	
//	// Lists a specific team
//	@RequestMapping(path="", method = RequestMethod.GET)
//	public @ResponseBody Team getTeam(@PathVariable long id) {
//		return teamRepository.findById(id).get();
//	}
//	
//	// Lists all the teams
//	@RequestMapping(method = RequestMethod.GET)
//	public @ResponseBody Iterable<Team> getAllTeams() {
//		// This returns a JSON or XML with the users
//		return teamRepository.findAll();
//	}
}

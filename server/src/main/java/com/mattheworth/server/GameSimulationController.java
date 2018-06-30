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

	// Creates a new game simulation
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody long createGameSimulation(@RequestBody GameSimulation gameSimulation) {

		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation.getId();
	}
	
	// Deletes a game simulation
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody long deleteGameSimulation(@PathVariable long id) {
		GameSimulation deleteGameSimulation = gameSimulationRepository.findById(id).get();
		gameSimulationRepository.delete(deleteGameSimulation);
		
		return id;
	}
	
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
	// Lists a specific game simulation
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public @ResponseBody GameSimulation getGameSimulation(@PathVariable long id) {
		return gameSimulationRepository.findById(id).get();
	}
//	
//	// Lists all the teams
//	@RequestMapping(method = RequestMethod.GET)
//	public @ResponseBody Iterable<Team> getAllTeams() {
//		// This returns a JSON or XML with the users
//		return teamRepository.findAll();
//	}
}

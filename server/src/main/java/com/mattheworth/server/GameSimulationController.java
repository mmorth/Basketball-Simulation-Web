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
	public @ResponseBody GameSimulation createGameSimulation(@RequestBody GameSimulation gameSimulation) {

		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulationRepository.findById(gameSimulation.getId()).get();
	}
	
	// Deletes a game simulation
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody long deleteGameSimulation(@PathVariable long id) {
		GameSimulation deleteGameSimulation = gameSimulationRepository.findById(id).get();
		gameSimulationRepository.delete(deleteGameSimulation);
		
		return id;
	}
	
	// Updates the away team for the game simulation
	@RequestMapping(path="/{id}/away-team", method = RequestMethod.PUT)
	public @ResponseBody GameSimulation updateAwayTeam(@RequestBody Long awayTeamID, @PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		Team newAwayTeam = teamRepository.findById(awayTeamID).get();
		
		gameSimulation.setAwayTeam(newAwayTeam);
		gameSimulation.resetGame();
		
		System.out.println(newAwayTeam.getName());
		
		System.out.println(gameSimulation.getAwayTeam().getName() + " " + gameSimulation.getAwayTeamScore());
		System.out.println(gameSimulation.getHomeTeam().getName() + " " + gameSimulation.getHomeTeamScore());
		System.out.println("Possessions: " + gameSimulation.getPossessionsRemaining());
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	// Updates the home team for the game simulation
	@RequestMapping(path="/{id}/home-team", method = RequestMethod.PUT)
	public @ResponseBody GameSimulation updateHomeTeam(@RequestBody Long homeTeamID, @PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		Team newHomeTeam = teamRepository.findById(homeTeamID).get();
		
		gameSimulation.setHomeTeam(newHomeTeam);
		gameSimulation.resetGame();
		
		System.out.println(newHomeTeam.getName());
		
		System.out.println(gameSimulation.getAwayTeam().getName() + " " + gameSimulation.getAwayTeamScore());
		System.out.println(gameSimulation.getHomeTeam().getName() + " " + gameSimulation.getHomeTeamScore());
		System.out.println("Possessions: " + gameSimulation.getPossessionsRemaining());
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	// Simulates a possession
	@RequestMapping(path="/{id}/simulate-possession", method = RequestMethod.GET)
	public @ResponseBody GameSimulation simulatePossession(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.simulationPossession(1);
		
		System.out.println(gameSimulation.getAwayTeam().getName() + " " + gameSimulation.getAwayTeamScore());
		System.out.println(gameSimulation.getHomeTeam().getName() + " " + gameSimulation.getHomeTeamScore());
		System.out.println("Possessions: " + gameSimulation.getPossessionsRemaining());
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	// Simulates a quarter
	@RequestMapping(path="/{id}/simulate-quarter", method = RequestMethod.GET)
	public @ResponseBody GameSimulation simulateQuarter(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.simulationPossession((gameSimulation.getPossessionsRemaining()%25) + 1);
		
		System.out.println(gameSimulation.getAwayTeam().getName() + " " + gameSimulation.getAwayTeamScore());
		System.out.println(gameSimulation.getHomeTeam().getName() + " " + gameSimulation.getHomeTeamScore());
		System.out.println("Possessions: " + gameSimulation.getPossessionsRemaining());
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	// Simulates a game
	@RequestMapping(path="/{id}/simulate-game", method = RequestMethod.GET)
	public @ResponseBody GameSimulation simulateGame(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.simulationPossession(gameSimulation.getPossessionsRemaining() + 1);
		
		System.out.println(gameSimulation.getAwayTeam().getName() + " " + gameSimulation.getAwayTeamScore());
		System.out.println(gameSimulation.getHomeTeam().getName() + " " + gameSimulation.getHomeTeamScore());
		System.out.println("Possessions: " + gameSimulation.getPossessionsRemaining());
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	// Resets the simulation
	@RequestMapping(path="/{id}/reset-simulation", method = RequestMethod.GET)
	public @ResponseBody GameSimulation resetSimulation(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.resetGame();
		
		System.out.println(gameSimulation.getAwayTeam().getName() + " " + gameSimulation.getAwayTeamScore());
		System.out.println(gameSimulation.getHomeTeam().getName() + " " + gameSimulation.getHomeTeamScore());
		System.out.println("Possessions: " + gameSimulation.getPossessionsRemaining());
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	// Lists a specific game simulation
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public @ResponseBody GameSimulation getGameSimulation(@PathVariable long id) {
		return gameSimulationRepository.findById(id).get();
	}

}

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

/**
 * A controller class that represents the back-end REST API
 * @author mmorth
 *
 */
@CrossOrigin(origins="*")
@Controller
@RequestMapping(path="/api/game-simulation")
public class GameSimulationController {
	
	/**
	 * Manages the GameSimulation for the database
	 */
	@Autowired
	private GameSimulationRepository gameSimulationRepository;
	
	/**
	 * Manages the Team for the database
	 */
	@Autowired 
	private TeamRepository teamRepository;

	/**
	 * Creates a new GameSimulation object in the database
	 * @param gameSimulation The game simulation accepted as json from the front-end
	 * @return The newly created GameSimulation object as json
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody GameSimulation createGameSimulation(@RequestBody GameSimulation gameSimulation) {

		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulationRepository.findById(gameSimulation.getId()).get();
	}
	
	/**
	 * Deletes a GameSimulation object from the database
	 * @param id The id of the GameSimulation to delete
	 * @return The id of the GameSimulation object deleted
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody long deleteGameSimulation(@PathVariable long id) {
		GameSimulation deleteGameSimulation = gameSimulationRepository.findById(id).get();
		gameSimulationRepository.delete(deleteGameSimulation);
		
		return id;
	}
	
	/**
	 * Updates the away team for the GameSimulation object in the database
	 * @param awayTeamID The id of the team to set as json
	 * @param id The id of the GameSimulation
	 * @return The updated GameSimulation object as json
	 */
	@RequestMapping(path="/{id}/away-team", method = RequestMethod.PUT)
	public @ResponseBody GameSimulation updateAwayTeam(@RequestBody Long awayTeamID, @PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		Team newAwayTeam = teamRepository.findById(awayTeamID).get();
		
		gameSimulation.setAwayTeam(newAwayTeam);
		gameSimulation.resetGame();
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	/**
	 * Updates the home team for the GameSimulation object in the database
	 * @param homeTeamID The id of the team to set as json
	 * @param id The id of the GameSimulation
	 * @return The updated GameSimulation object as json
	 */
	@RequestMapping(path="/{id}/home-team", method = RequestMethod.PUT)
	public @ResponseBody GameSimulation updateHomeTeam(@RequestBody Long homeTeamID, @PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		Team newHomeTeam = teamRepository.findById(homeTeamID).get();
		
		gameSimulation.setHomeTeam(newHomeTeam);
		gameSimulation.resetGame();
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	/**
	 * Simulates a possession and updates the GameSimulation object's information in the back-end
	 * @param id The id of the GameSimulation to simulate
	 * @return The updated GameSimulation object as json
	 */
	@RequestMapping(path="/{id}/simulate-possession", method = RequestMethod.GET)
	public @ResponseBody GameSimulation simulatePossession(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.simulationPossession(1);
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	/**
	 * Simulates a quarter and updates the GameSimulation object's information in the back-end
	 * @param id The id of the GameSimulation to simulate
	 * @return The updated GameSimulation object as json
	 */
	@RequestMapping(path="/{id}/simulate-quarter", method = RequestMethod.GET)
	public @ResponseBody GameSimulation simulateQuarter(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.simulationPossession((gameSimulation.getPossessionsRemaining()%25) + 1);
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	/**
	 * Simulates a game and updates the GameSimulation object's information in the back-end
	 * @param id The id of the GameSimulation to simulate
	 * @return The updated GameSimulation object as json
	 */
	@RequestMapping(path="/{id}/simulate-game", method = RequestMethod.GET)
	public @ResponseBody GameSimulation simulateGame(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.simulationPossession(gameSimulation.getPossessionsRemaining() + 1);
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	/**
	 * Resets the simulation and updates the GameSimulation object's information in the back-end
	 * @param id The id of the GameSimulation to reset
	 * @return The updated GameSimulation object as json
	 */
	@RequestMapping(path="/{id}/reset-simulation", method = RequestMethod.GET)
	public @ResponseBody GameSimulation resetSimulation(@PathVariable long id) {
		GameSimulation gameSimulation = gameSimulationRepository.findById(id).get();
		
		gameSimulation.resetGame();
		
		gameSimulationRepository.save(gameSimulation);
		
		return gameSimulation;
	}
	
	/**
	 * Returns the GameSimulation object with the given id from the database
	 * @param id The id of the GameSimulation object to return
	 * @return The GameSimulation object as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public @ResponseBody GameSimulation getGameSimulation(@PathVariable long id) {
		return gameSimulationRepository.findById(id).get();
	}

}

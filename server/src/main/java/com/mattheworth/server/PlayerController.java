package com.mattheworth.server;

import java.util.Optional;

import javax.swing.plaf.synth.SynthSeparatorUI;

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
 * A class that handles REST API requests sent from the front-end
 * @author mmorth
 *
 */
@CrossOrigin(origins="*")
@Controller 
@RequestMapping(path="/api") 
public class PlayerController {
	
	/**
	 * Handles Player object requests with the database
	 */
	@Autowired
	private PlayerRepository playerRepository;
	
	/**
	 * Handles Team object requests with the database
	 */
	@Autowired
	private TeamRepository teamRepository;

	/**
	 * Creates a new player from the input json 
	 * @param jsonPlayer The player constructed from the input json
	 * @return The id of the new player created as json
	 */
	@RequestMapping(path="/teams/{teamID}/players", method = RequestMethod.POST, produces = "application/json") 
	public @ResponseBody boolean createPlayer(@PathVariable long teamID, @RequestBody Player jsonPlayer) {
		boolean validCreate = true;
		
		Team team = teamRepository.findById(teamID).get();
		
		System.out.println(jsonPlayer);
		System.out.println(jsonPlayer.getName());
		System.out.println(jsonPlayer.getOffensiveRating());
		System.out.println(jsonPlayer.getRole());
		
		if (team.validPlayerRole(jsonPlayer.getId(), jsonPlayer.getRole())) {
			jsonPlayer.resetPlayerGameStats();
			jsonPlayer.setOverallRating();
			
			int gamePosition = team.determineDefaultGamePosition(jsonPlayer.getId(), jsonPlayer.getRole());
			
			if (gamePosition != -1) {
				jsonPlayer.setPositionPlay(gamePosition);
			}
			
			playerRepository.save(jsonPlayer);
			
			team.addPlayer(jsonPlayer);
			teamRepository.save(team);
		} else {
			validCreate = false;
		}
		
		return validCreate;
	}
	
	/**
	 * Deletes a player with the given id
	 * @param id The id of the player to delete
	 * @return The id of the deleted player as json
	 */
	@RequestMapping(path="/teams/{teamID}/{playerID}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody long deletePlayer(@PathVariable long teamID, @PathVariable long playerID) {
		Player deletePlayer = playerRepository.findById(playerID).get();
		playerRepository.delete(deletePlayer);
		
		Team team = teamRepository.findById(teamID).get();
		team.removePlayer(deletePlayer);
		teamRepository.save(team);
		
		return playerID;
	}
	
	/**
	 * Updates a player from the input json
	 * @param id The id of the player to update
	 * @param jsonPlayer The Player object constructed from the input json
	 * @return The id of the player that was updated as json
	 */
	@RequestMapping(path="/teams/{teamID}/{playerID}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody boolean updatePlayer(@PathVariable long teamID, @PathVariable long playerID, @RequestBody Player jsonPlayer) {
		Player updatePlayer = playerRepository.findById(playerID).get();
		Team team = teamRepository.findById(teamID).get();
		
		updatePlayer.setOffensiveRating(jsonPlayer.getOffensiveRating());
		updatePlayer.setDefensiveRating(jsonPlayer.getDefensiveRating());
		updatePlayer.setPosition(jsonPlayer.getPosition());
		updatePlayer.setRotationMinutes(jsonPlayer.getRotationMinutes());
		updatePlayer.setStamina(jsonPlayer.getStamina());
		updatePlayer.resetPlayerGameStats();
		updatePlayer.setOverallRating();
		
		boolean validChange = true;
		
		if (team.validPlayerRole(updatePlayer.getId(), jsonPlayer.getRole())) {
			updatePlayer.setRole(jsonPlayer.getRole());
			
			if (updatePlayer.getRole().equals("Starter")) {
				team.determineDefaultGamePosition(updatePlayer.getId(), updatePlayer.getRole());
			}
		} else {
			validChange = false;
		}
		
		team.changeGamePosition(updatePlayer.getId(), updatePlayer.getRole(), jsonPlayer.getPositionPlay(), updatePlayer.getPositionPlay());
		updatePlayer.setPositionPlay(jsonPlayer.getPositionPlay());
		
		playerRepository.save(updatePlayer);

		team.setOffensiveRating();
		team.setDefensiveRating();
		team.setOverallRating();
		teamRepository.save(team);
		
		return validChange;
	}
	
	/**
	 * Returns an object of the player with the given id
	 * @param playerID The id of the player that is returned
	 * @return The player with the specified id as json
	 */
	@RequestMapping(path="/players/{playerID}", method = RequestMethod.GET)
	public @ResponseBody Player getPlayer(@PathVariable long playerID) {
		return playerRepository.findById(playerID).get();
	}
	
	/**
	 * Returns a list of all Player objects stored in the database
	 * @return A list of all Player object stored in the database as json
	 */
	@RequestMapping(path="/players", method = RequestMethod.GET)
	public @ResponseBody Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}
	
	/**
	 * Creates a new coach from the input json 
	 * @param jsonPlayer The coach constructed from the input json
	 * @return The id of the new coach created as json
	 */
	@RequestMapping(path="/teams/{teamID}/coach", method = RequestMethod.POST, produces = "application/json") 
	public @ResponseBody long createCoach(@PathVariable long teamID, @RequestBody Player jsonCoach) {
		Team team = teamRepository.findById(teamID).get();

		playerRepository.save(jsonCoach);
		
		team.setCoach(jsonCoach);
		teamRepository.save(team);
		
		long playerID = jsonCoach.getId();
		
		return playerID;
	}
	
	/**
	 * Deletes a coach with the given id
	 * @param id The id of the coach to delete
	 * @return The id of the deleted coach as json
	 */
	@RequestMapping(path="/teams/{teamID}/coach/{coachID}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody long deleteCoach(@PathVariable long teamID, @PathVariable long coachID) {
		Team team = teamRepository.findById(teamID).get();
		Player deleteCoach = playerRepository.findById(coachID).get();
		team.setCoach(null);
		
		teamRepository.save(team);
		playerRepository.delete(deleteCoach);
		
		return coachID;
	}
	
	/**
	 * Updates a coach from the input json
	 * @param id The id of the coach to update
	 * @param jsonCoach The Player object constructed from the input json
	 * @return The id of the coach that was updated as json
	 */
	@RequestMapping(path="/teams/{teamID}/coach/{coachID}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody long updateCoach(@PathVariable long teamID, @PathVariable long coachID, @RequestBody Player jsonCoach) {
		Player updatePlayer = playerRepository.findById(coachID).get();
		
		updatePlayer.setOffensiveRating(jsonCoach.getOffensiveRating());
		updatePlayer.setDefensiveRating(jsonCoach.getDefensiveRating());
		updatePlayer.setPosition(jsonCoach.getPosition());
		updatePlayer.setRotationMinutes(jsonCoach.getRotationMinutes());
		updatePlayer.setRole(jsonCoach.getRole());
		updatePlayer.resetPlayerGameStats();
		updatePlayer.setOverallRating();
		playerRepository.save(updatePlayer);
		
		Team team = teamRepository.findById(teamID).get();
		team.setOffensiveRating();
		team.setDefensiveRating();
		team.setOverallRating();
		teamRepository.save(team);
		
		return coachID;
	}
}

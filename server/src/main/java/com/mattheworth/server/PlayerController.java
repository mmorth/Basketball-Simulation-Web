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
 * A class that handles REST API requests sent from the front-end
 * @author mmorth
 *
 */
@CrossOrigin(origins="*")
@Controller 
@RequestMapping(path="/api/players") 
public class PlayerController {
	
	/**
	 * Handles Team object requests with the database
	 */
	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * Creates a new player from the input json 
	 * @param jsonTeam The player constructed from the input json
	 * @return The id of the new player created as json
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json") 
	public @ResponseBody long createPlayer(@RequestBody Player jsonPlayer) {

		playerRepository.save(jsonPlayer); 
		
		long teamID = jsonPlayer.getId();
		
		return teamID;
	}
	
	/**
	 * Deletes a player with the given id
	 * @param id The id of the player to delete
	 * @return The id of the deleted player as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody long deletePlayer(@PathVariable long id) {
		Player deletePlayer = playerRepository.findById(id).get();
		playerRepository.delete(deletePlayer);
		
		return id;
	}
	
	/**
	 * Updates a player from the input json
	 * @param id The id of the player to update
	 * @param jsonTeam The Player object constructed from the input json
	 * @return The id of the player that was updated as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody long updatePlayer(@PathVariable long id, @RequestBody Player jsonPlayer) {
		Player updatePlayer = playerRepository.findById(id).get();
		
		updatePlayer.setOffensiveRating(jsonPlayer.getOffensiveRating());
		updatePlayer.setDefensiveRating(jsonPlayer.getDefensiveRating());
		playerRepository.save(updatePlayer);
		
		return id;
	}
	
	/**
	 * Returns an object of the player with the given id
	 * @param id The id of the player that is returned
	 * @return The player with the specified id as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public @ResponseBody Player getTeam(@PathVariable long id) {
		return playerRepository.findById(id).get();
	}
	
	/**
	 * Returns a list of all Player objects stored in the database
	 * @return A list of all Player object stored in the database as json
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}
}

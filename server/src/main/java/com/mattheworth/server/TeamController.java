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
@RequestMapping(path="/api/teams") 
public class TeamController {
	
	/**
	 * Handles Team object requests with the database
	 */
	@Autowired
	private TeamRepository teamRepository;

	/**
	 * Creates a new team from the input json 
	 * @param jsonTeam The team constructed from the input json
	 * @return The id of the new team created as json
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json") 
	public @ResponseBody long createTeam(@RequestBody Team jsonTeam) {
		jsonTeam.setOffensiveRating();
		jsonTeam.setDefensiveRating();
		jsonTeam.resetGameStats();
		
		teamRepository.save(jsonTeam); 
		
		long teamID = jsonTeam.getId();
		
		return teamID;
	}
	
	/**
	 * Deletes a team with the given id
	 * @param id The id of the team to delete
	 * @return The id of the deleted team as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody long deleteTeam(@PathVariable long id) {
		Team deleteTeam = teamRepository.findById(id).get();
		teamRepository.delete(deleteTeam);
		
		return id;
	}
	
	/**
	 * Updates a team from the input json
	 * @param id The id of the team to update
	 * @param jsonTeam The Team object constructed from the input json
	 * @return The id of the team that was updated as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody long updateTeam(@PathVariable long id, @RequestBody Team jsonTeam) {
		Team updateTeam = teamRepository.findById(id).get();
		
		updateTeam.setOffensiveRating();
		updateTeam.setDefensiveRating();
		teamRepository.save(updateTeam);
		
		return id;
	}
	
	/**
	 * Returns an object of the team with the given id
	 * @param id The id of the team that is returned
	 * @return The team with the specified id as json
	 */
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public @ResponseBody Team getTeam(@PathVariable long id) {
		return teamRepository.findById(id).get();
	}
	
	/**
	 * Returns a list of all Team objects stored in the database
	 * @return A list of all Team object stored in the database as json
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Iterable<Team> getAllTeams() {
		return teamRepository.findAll();
	}
}

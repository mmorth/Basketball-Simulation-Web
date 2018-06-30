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
@RequestMapping(path="/api/teams") 
public class TeamController {
	@Autowired
	private TeamRepository teamRepository;

	// Creates a new team
	@RequestMapping(method = RequestMethod.POST, produces = "application/json") 
	public @ResponseBody long createTeam(@RequestBody Team jsonTeam) {

		teamRepository.save(jsonTeam); 
		
		long teamID = jsonTeam.getId();
		
		return teamID;
	}
	
	// Deletes a team
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody long deleteTeam(@PathVariable long id) {
		Team deleteTeam = teamRepository.findById(id).get();
		teamRepository.delete(deleteTeam);
		
		return id;
	}
	
	// Updates a team
	@RequestMapping(path="/{id}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody long updateTeam(@PathVariable long id, @RequestBody Team jsonTeam) {
		Team updateTeam = teamRepository.findById(id).get();
		
		updateTeam.setOffensiveRating(jsonTeam.getOffensiveRating());
		updateTeam.setDefensiveRating(jsonTeam.getDefensiveRating());
		teamRepository.save(updateTeam);
		
		return id;
	}
	
	// Lists a specific team
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public @ResponseBody Team getTeam(@PathVariable long id) {
		return teamRepository.findById(id).get();
	}
	
	// Lists all the teams
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Iterable<Team> getAllTeams() {
		return teamRepository.findAll();
	}
}

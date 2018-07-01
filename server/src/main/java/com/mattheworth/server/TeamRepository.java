package com.mattheworth.server;

import org.springframework.data.repository.CrudRepository;

/**
 * A class that manages Team objects in the database
 * @author mmorth
 *
 */
public interface TeamRepository extends CrudRepository<Team, Long> {

}

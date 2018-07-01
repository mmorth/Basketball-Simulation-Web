package com.mattheworth.server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A class that manages GameSimulation entries in the database
 * @author mmorth
 *
 */
public interface GameSimulationRepository extends JpaRepository<GameSimulation, Long> {

}

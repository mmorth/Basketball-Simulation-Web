package com.mattheworth.server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSimulationRepository extends JpaRepository<GameSimulation, Long> {

}

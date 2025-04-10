package com.appstate.cisco.presentation.repository;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import com.appstate.cisco.presentation.model.entity.PlayerKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository
extends CrudRepository<PlayerEntity, PlayerKey> {

    @Query(
            value = "SELECT * FROM player_db.player WHERE points = (SELECT MAX(points) FROM player_db.player WHERE side = ?1 AND game_id = ?2) AND side = ?1 ORDER BY game_time asc limit 2",
            nativeQuery = true)
    List<PlayerEntity> getHighestScoringPlayers(String side, String gameId);
    @Query(
            value = "SELECT * FROM player_db.player WHERE points = (SELECT MAX(points) FROM player_db.player WHERE game_id = ?1) ORDER BY game_time asc limit 2",
            nativeQuery = true)
    List<PlayerEntity> getHighestScoringPlayers(String gameId);

    @Query(
            value = "SELECT * FROM player_db.player WHERE points = (SELECT MIN(points) FROM player_db.player WHERE side = ?1 AND game_id = ?2) AND side = ?1 ORDER BY game_time asc limit 2",
            nativeQuery = true)
    List<PlayerEntity> getLowestScoringPlayers(String side, String gameId);
    @Query(
            value = "SELECT * FROM player_db.player WHERE points = (SELECT MIN(points) FROM player_db.player WHERE game_id = ?1) ORDER BY game_time asc limit 2",
            nativeQuery = true)
    List<PlayerEntity> getLowestScoringPlayers(String gameId);
}

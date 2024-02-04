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
    List<PlayerEntity> findAllByKeySide(String side);

    @Query(
            value = "SELECT DISTINCT number WHERE side= ?1",
            nativeQuery = true)
    List<Integer> getPlayerNumbers(String side);

    @Query(
            value = "SELECT DISTINCT number",
            nativeQuery = true)
    List<Integer> getPlayerNumbers();

    @Query(
            value = "SELECT * FROM player_db.player WHERE points = (SELECT MAX(points) FROM player_db.player WHERE side = ?1)  ORDER BY game_time asc limit 2",
            nativeQuery = true)
    List<PlayerEntity> getHighestScoringPlayers(String side);
    @Query(
            value = "SELECT * FROM player_db.player WHERE points = (SELECT MAX(points) FROM player_db.player)  ORDER BY game_time asc limit 2",
            nativeQuery = true)
    List<PlayerEntity> getHighestScoringPlayers();
}

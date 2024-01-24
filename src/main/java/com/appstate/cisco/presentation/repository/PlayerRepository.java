package com.appstate.cisco.presentation.repository;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import com.appstate.cisco.presentation.model.entity.PlayerKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

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
            value = "SELECT(*) WHERE points = (SELECT MAX(points) WHERE side = ?1)",
            nativeQuery = true)
    List<PlayerEntity> getHighestScoringPlayers(String side);
    @Query(
            value = "SELECT(*) WHERE points = (SELECT MAX(points))",
            nativeQuery = true)
    List<PlayerEntity> getHighestScoringPlayers();
}

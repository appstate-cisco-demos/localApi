package com.appstate.cisco.presentation.service;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import com.appstate.cisco.presentation.model.entity.PlayerKey;
import com.appstate.cisco.presentation.model.response.PlayerResponse;
import com.appstate.cisco.presentation.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GameStatsService {

    @Autowired
    private PlayerRepository playerRepository;

    public static String currentGameId;

    public List<PlayerResponse> getHighestScoringPlayer(String side) {
        List<PlayerEntity> highestScoringPlayers;
        if (side != null) {
            highestScoringPlayers = playerRepository.getHighestScoringPlayers(side, currentGameId);
        }
        else {
            highestScoringPlayers = playerRepository.getHighestScoringPlayers(currentGameId);
        }
        if (highestScoringPlayers.isEmpty()) {
            throw new EntityNotFoundException();
        }
        List<PlayerResponse> responses = new ArrayList<>();
        highestScoringPlayers.stream().forEach(player -> {responses.add(new PlayerResponse(player));});
        return responses;
    }

    public List<PlayerResponse> getLowestScoringPlayer(String side) {
        List<PlayerEntity> lowestScoringPlayers;
        if (side != null) {
            lowestScoringPlayers = playerRepository.getLowestScoringPlayers(side, currentGameId);
        }
        else {
            lowestScoringPlayers = playerRepository.getLowestScoringPlayers(currentGameId);
        }
        if (lowestScoringPlayers.isEmpty()) {
            throw new EntityNotFoundException();
        }
        lowestScoringPlayers = playerRepository.getLowestScoringPlayers(side, currentGameId);
        List<PlayerResponse> responses = new ArrayList<>();
        lowestScoringPlayers.stream().forEach(player -> {responses.add(new PlayerResponse(player));});
        return responses;
    }

}

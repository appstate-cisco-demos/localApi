package com.appstate.cisco.presentation.service;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import com.appstate.cisco.presentation.model.entity.PlayerKey;
import com.appstate.cisco.presentation.model.response.PlayerResponse;
import com.appstate.cisco.presentation.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class GameStatsService {

    @Autowired
    private PlayerRepository playerRepository;

    public GameStatsService(){}

    public PlayerResponse getMVP(String side) {
        List<PlayerEntity> highestScoringPlayers;
        if (side != null) {
            highestScoringPlayers = playerRepository.getHighestScoringPlayers(side);
        }
        else {
            highestScoringPlayers = playerRepository.getHighestScoringPlayers();
        }
        if (highestScoringPlayers.isEmpty()) {
            throw new EntityNotFoundException();
        }
        else if (highestScoringPlayers.size() > 1) {
            PlayerKey keyOne = highestScoringPlayers.get(0).getKey();
            PlayerKey keyTwo = highestScoringPlayers.get(1).getKey();
            if (!keyOne.getSide().equals(keyTwo.getSide()) || keyOne.getNumber() != keyTwo.getNumber()) {
                // Not the same player...
                throw new EntityNotFoundException();
            }
        }
        return new PlayerResponse(highestScoringPlayers.get(0));
    }

}

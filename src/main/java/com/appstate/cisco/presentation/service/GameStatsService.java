package com.appstate.cisco.presentation.service;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import com.appstate.cisco.presentation.model.response.PlayerResponse;
import com.appstate.cisco.presentation.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class GameStatsService {
    private PlayerRepository playerRepository;

    public GameStatsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    public PlayerResponse getMVP(String side) {
        List<PlayerEntity> highestScoringPlayers;
        if (side != null) {
            highestScoringPlayers = playerRepository.getHighestScoringPlayers(side);
        }
        else {
            highestScoringPlayers = playerRepository.getHighestScoringPlayers();
        }
        if (highestScoringPlayers.size() > 1 || highestScoringPlayers.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return new PlayerResponse(highestScoringPlayers.get(0));
    }

}

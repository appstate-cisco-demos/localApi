package com.appstate.cisco.presentation.rest;

import com.appstate.cisco.presentation.service.FetchingService;
import com.appstate.cisco.presentation.service.GameStatsService;
import io.swagger.v3.oas.annotations.Operation;
import com.appstate.cisco.presentation.model.response.PlayerResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("v1/gameStats")
public class GameStatsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStatsController.class);
    private final String NO_HIGHEST = "No highest scoring player can currently be determined.";
    private final String NO_LOWEST = "No lowest scoring player can currently be determined.";

    @Autowired
    GameStatsService gameStatsService;

    @Autowired
    FetchingService fetchingService;

    @Operation(
            description =
                    "Get the most valuable player in the game thus far.",
            tags = "Game Stat MVP",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PlayerResponse.class))
                    })
    })
    @GetMapping(path = "/HighestScoring")
    public ResponseEntity<PlayerResponse> getHighestScoringPlayers(@RequestParam(name="side", required = false) String side) {
        try {
            return new ResponseEntity(gameStatsService.getHighestScoringPlayer(side), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(NO_HIGHEST, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/LowestScoring")
    public ResponseEntity<PlayerResponse> getLowestScoringPlayer(@RequestParam(name="side") String side) {
        try {
            return new ResponseEntity(gameStatsService.getLowestScoringPlayer(side), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(NO_LOWEST, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/start")
    public void startFetch() {
        fetchingService.start();
    }
}

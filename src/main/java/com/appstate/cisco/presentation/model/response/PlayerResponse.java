package com.appstate.cisco.presentation.model.response;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import lombok.Data;

@Data
public class PlayerResponse {

    private int number;
    private int points;

    public PlayerResponse(PlayerEntity entity) {
        number = entity.getKey().getNumber();
        points = entity.getPoints();
    }
}

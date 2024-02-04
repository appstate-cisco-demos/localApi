package com.appstate.cisco.presentation.model.entity;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class PlayerKey implements Serializable {
    private static final long serialVersionUID = 4633626418722570343L;

    @Column(name = "number")
    private int number;

    /* Home or Away */
    @Column(name = "side")
    private String side;

    @Column(name = "game_time")
    private String gameTime;

    @Column(name = "quarter")
    private int quarter;

    @Column(name = "game_id")
    private String gameId;
}

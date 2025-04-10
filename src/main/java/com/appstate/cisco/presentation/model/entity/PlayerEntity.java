package com.appstate.cisco.presentation.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Entity(name = "player")
@Data
public class PlayerEntity implements Serializable {
    private static final long serialVersionUID = -5047328347539934011L;

    /* Composite Primary Key (PK) fields: number, side, gameTime, quarter.  */
    @EmbeddedId
    private PlayerKey key;

    @JsonProperty("points")
    @Range(min = 0)
    private int points;

    @JsonProperty("fouls")
    @Range(min = 0)
    private int fouls;

    @JsonProperty("fga")
    @Range(min = 0)
    private int fieldGoalsAttempted;

    @JsonProperty("fgm")
    @Range(min = 0)
    private int fieldGoalsMade;

    @JsonProperty("3pa")
    @Range(min = 0)
    private int ThreePointsAttempted;

    @JsonProperty("3pm")
    @Range(min = 0)
    private int ThreePointsMade;

    @JsonProperty("fta")
    @Range(min = 0)
    private int FreeThrowsAttempted;

    @JsonProperty("ftm")
    @Range(min = 0)
    private int FreeThrowsMade;


}

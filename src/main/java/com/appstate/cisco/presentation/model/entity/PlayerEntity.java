package com.appstate.cisco.presentation.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Entity
@Data
public class PlayerEntity implements Serializable {
    private static final long serialVersionUID = -5047328347539934011L;

    /* Composite Primary Key (PK) fields: number, side, gameTime, quarter.  */
    @EmbeddedId
    private PlayerKey key;

    @Range(min = 0)
    private int points;

    @Range(min = 0)
    private int fouls;

    @Range(min = 0)
    private int fieldGoalsAttempted;

    @Range(min = 0)
    private int fieldGoalsMade;

    @Range(min = 0)
    private int ThreePointsAttempted;

    @Range(min = 0)
    private int ThreePointsMade;

    @Range(min = 0)
    private int FreeThrowsAttempted;

    @Range(min = 0)
    private int FreeThrowsMade;


}

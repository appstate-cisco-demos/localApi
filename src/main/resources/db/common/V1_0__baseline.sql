CREATE TABLE `player` (
    `game_id` varchar(32) NOT NULL,
    `number` int(11) NOT NULL,
    `side` varchar(32) NOT NULL,
    `game_time` varchar(32) NOT NULL,
    `quarter` int(11) NOT NULL,
    `points` int(11) DEFAULT NULL,
    `fouls` int(11) DEFAULT NULL,
    `field_goals_attempted` int(11) DEFAULT NULL,
    `field_goals_made` int(11) DEFAULT NULL,
    `three_points_attempted` int(11) DEFAULT NULL,
    `three_points_made` int(11) DEFAULT NULL,
    `free_throws_attempted` int(11) DEFAULT NULL,
    `free_throws_made` int(11) DEFAULT NULL,
    PRIMARY KEY (`game_id`, `number`, `side`, `game_time`, `quarter`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
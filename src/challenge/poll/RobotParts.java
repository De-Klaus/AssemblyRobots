package challenge.poll;

import challenge.util.RandomUtil;


public enum RobotParts {
    HEAD,
    BODY,
    LEFT_HAND,
    RIGHT_HAND,
    LEFT_LAG,
    RIGHT_LAG,
    CPU,
    RAM,
    HDD;

    public static RobotParts getRandomPart() {
        RobotParts[] availableParts = RobotParts.values();
        return availableParts[RandomUtil.getRandom(availableParts.length)];
    }
}

package challenge.poll;



import challenge.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static challenge.Params.COUNT_GENERATED_ISSUE;

public class Dump {

    private final List<RobotParts> robotParts = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Integer FIRST_NIGHT = 20;

    public Dump() {
    }

    public void inicialize() {
        IntStream.range(0, FIRST_NIGHT)
                .forEach(s->robotParts.add(RobotParts.getRandomPart()));
    }

    public void generateParts() {
        IntStream.range(0, COUNT_GENERATED_ISSUE)
                .forEach(s -> robotParts.add(RobotParts.getRandomPart()));
    }

    public Lock getLock() {
        return lock;
    }

    public void tryGetParts(List<RobotParts> partsToIssue, int numberIssueParts) {
        if (!robotParts.isEmpty()) {
            int size = robotParts.size();
            if (numberIssueParts > size)
                numberIssueParts = size;
            IntStream.range(0, numberIssueParts)
                    .forEach(s -> partsToIssue.add(robotParts.remove(RandomUtil.getRandom(robotParts.size()))));
        }
    }

    @Override
    public String toString() {
        return "Dump{" +
                "robotParts=" + robotParts +
                ", lock=" + lock +
                ", FIRST_NIGHT=" + FIRST_NIGHT +
                '}';
    }
}

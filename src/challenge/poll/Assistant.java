package challenge.poll;

import challenge.util.RandomUtil;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static challenge.Params.COUNT_GENERATED_DELIVERY;

public class Assistant {

    private List<RobotParts> partsToIssue;
    private final Lock lock = new ReentrantLock();

    public Assistant(List<RobotParts> partsToIssue) {
        this.partsToIssue = partsToIssue;
    }

    public Integer getNumberIssueParts() {
        return RandomUtil.getRandom(COUNT_GENERATED_DELIVERY);
    }

    public Lock getLock() {
        return lock;
    }

    public List<RobotParts> getPartsToIssue() {
        return partsToIssue;
    }

    public void takeParts(Dump dump) {
        int numberIssueParts = getNumberIssueParts();
        dump.tryGetParts(partsToIssue,numberIssueParts);
    }
}

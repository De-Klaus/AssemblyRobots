package challenge.poll;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class NightSearchRunnable implements Runnable {

    private final CyclicBarrier cyclicBarrier;
    private final Assistant assistant_1;
    private final Assistant assistant_2;
    private final Dump dump;

    public NightSearchRunnable(CyclicBarrier cyclicBarrier, Assistant assistant_1, Assistant assistant_2, Dump dump) {
        this.cyclicBarrier = cyclicBarrier;
        this.assistant_1 = assistant_1;
        this.assistant_2 = assistant_2;
        this.dump = dump;
    }


    @Override
    public void run() {
        long start = System.currentTimeMillis();
        long end = 0L;
        System.out.println("Асистенты отправились на поиск: ");
        lockAccounts();
        try {
            assistant_1.takeParts(dump);
            assistant_2.takeParts(dump);
            end = System.currentTimeMillis();
            long dif = 100L-end+start;
            if(dif>0)
                Thread.sleep(dif);
            System.out.println(String.format("Асистенты вернулись затратив времени: %s милесекунд.",(System.currentTimeMillis()-start)));
            dump.generateParts();
            System.out.println("На свалке новый выгруз деталей ");

        } catch (InterruptedException e) {
            System.out.println("Error "+e.getMessage());
            e.printStackTrace();
        } finally {
            dump.getLock().unlock();
            assistant_1.getLock().unlock();
            assistant_2.getLock().unlock();
        }
        try {
            System.out.println("NumberWaiting: "+cyclicBarrier.getNumberWaiting());
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void lockAccounts() {
        while (true) {
            boolean dumpLockResult = dump.getLock().tryLock();
            boolean as1_lockResult = assistant_1.getLock().tryLock();
            boolean as2_lockResult = assistant_2.getLock().tryLock();
            if (dumpLockResult && as1_lockResult && as2_lockResult) {
                System.out.println("Trying to lock");
                break;
            }
            if (dumpLockResult) {
                dump.getLock().unlock();
            }
            if (as1_lockResult) {
                assistant_1.getLock().unlock();
            }
            if (as2_lockResult) {
                assistant_2.getLock().unlock();
            }
        }
    }
}

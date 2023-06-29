package challenge;

import challenge.poll.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static challenge.Params.*;

public class CompetitionStart {

    public static void main(String[] args) throws InterruptedException {
        // склады деталей
        List<RobotParts> stock_1 = new ArrayList<>();
        List<RobotParts> stock_2 = new ArrayList<>();
        AtomicBoolean finich = new AtomicBoolean(false);
        // запуск ночей соревнования 100
        CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT_NIGHT_SEARCH, () -> {
            System.out.println("Соревнование оконченно!");
            System.out.println("End stock_1-"+stock_1.size()+"; stock_2-"+stock_2.size());
            System.out.println(Winner.determineTheWinner(stock_1,stock_2));
            finich.set(true);
        });
        // свалка деталей заполненая инициализацией свалки
        Dump dump = new Dump();
        Assistant assistant_1 = new Assistant(stock_1);
        Assistant assistant_2 = new Assistant(stock_2);
        dump.inicialize();

        System.out.println("Top stock_1-"+stock_1.size()+"; stock_2-"+stock_2.size());
        // создаём нужно колличество потоков для каждой ночи соревнования
        ExecutorService threadPool = Executors.newCachedThreadPool();
        IntStream.range(0, COUNT_NIGHT_SEARCH)
                .mapToObj(night -> new NightSearchRunnable(
                        cyclicBarrier,
                        assistant_1,
                        assistant_2,
                        dump))
                .forEach(threadPool::submit);

        threadPool.shutdown();
        threadPool.awaitTermination(10L, TimeUnit.SECONDS);
    }

}

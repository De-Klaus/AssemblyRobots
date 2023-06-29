package challenge.poll;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Winner {

    // перебиются все коллеции и собираются работы
    public static String determineTheWinner(List<RobotParts> stock_1, List<RobotParts> stock_2){
        int size_1 = sortDetails(stock_1, new HashMap<>()).size();
        int size_2 = sortDetails(stock_2, new HashMap<>()).size();
        int difference = size_1 - size_2;
        String winnerScientist = (difference>0)?"SCIENTIST_1":"SCIENTIST_2";
        String diffRobots = difference>0 ? String.valueOf(difference):String.valueOf(difference).replace("-", "");

        if (size_1==0&&size_2==0)
            return String.format("ИГРОКАМ НЕ УДАЛОСЬ СОБРАТЬ НИ ОДНОГО РОБОТА :( Собранно роботов: %s", size_1);

        if(difference==0&&size_1!=0&&size_2!=0)
            return String.format("ВЫИГРАЛИ ОБА ИГРОКА СОБРАВ ПО %s РОБОТОВ", size_1);

        return String.format("ВЫИГРАЛ ИГРОК - %s, СОБРАВ НА %s РОБОТОВ БОЛЬШЕ СОПЕРНИКА",winnerScientist, diffRobots);
    }

    private static Map<Integer, List<RobotParts>> sortDetails(List<RobotParts> stock_1, Map<Integer, List<RobotParts>> robots) {
        stock_1.forEach(part -> {
            if (robots.isEmpty()) {
                addDetail(robots, part, 0, new ArrayList<>());
            } else {
                AtomicBoolean found = new AtomicBoolean(false);
                robots.forEach((k, v) -> {
                    found.set(v.contains(part));
                    if (!found.get()) {
                        addDetail(robots, part, k, v);
                    }
                });
                if (found.get()) {
                    addDetail(robots, part, robots.size(), new ArrayList<>());
                }
            }
        });
        return checkReadyRobots(robots);
    }

    private static Map<Integer, List<RobotParts>> checkReadyRobots(Map<Integer, List<RobotParts>> robots) {
        Map<Integer, List<RobotParts>> readyRobots = new HashMap<>();
        if(robots!=null&&!robots.isEmpty()){
            readyRobots = robots.entrySet().stream()
                    .filter(d->{
                        if(d.getValue().size()!=RobotParts.values().length)
                            return false;
                        else return true;
                    })
                    .collect(Collectors.toMap(r->r.getKey(), r->r.getValue()));
        }
        return readyRobots;
    }

    private static void addDetail(
            Map<Integer, List<RobotParts>> robots,
            RobotParts part, int i,
            List<RobotParts> parts) {
        parts.add(part);
        robots.put(i, parts);
    }
}

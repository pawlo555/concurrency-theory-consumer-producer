package Observers;

import java.util.Timer;
import java.util.TimerTask;

public class StatisticsCollector {

    private long totalProduction = 0;
    private long totalConsumption = 0;

    public StatisticsCollector() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                showStatistics();
            }
        }, 0, 60000);
    }

    public void productionEnd(int portion) {
        totalProduction = totalProduction + portion;
    }

    public void consumptionEnd(int portion) {
        totalConsumption = totalConsumption + portion;
    }

    private void showStatistics() {
        System.out.println(totalProduction + ", " + totalConsumption);
    }
}

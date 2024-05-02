import exception.InvalidLineException;
import exception.InvalidQueryException;
import model.Query;
import model.Timeline;
import service.TimelineService;
import service.TimelineServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int quantityOfLines = Integer.parseInt(scanner.nextLine());
        List<Timeline> timelines = new ArrayList<>();
        TimelineService timelineService = new TimelineServiceImpl();

        for (int i = 0; i < quantityOfLines; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            try {
                if (splitLine[0].startsWith("C")) {
                    timelines.add(timelineService.parseLineToTimeline(splitLine));
                }

                if (splitLine[0].startsWith("D")) {
                    Query query = timelineService.parseLineToQuery(splitLine);
                    timelineService.printAverageTime(timelines, query);
                }
            } catch (InvalidLineException | InvalidQueryException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

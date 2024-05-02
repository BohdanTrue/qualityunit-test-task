import exception.InvalidLineException;
import exception.InvalidQueryException;
import model.Timeline;
import parser.LineParser;
import parser.QueryParser;
import parser.TimelineParser;
import service.TimelineService;
import service.TimelineServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String REGEX = " ";
    private static final String UNSUPPORTED_LINE_TYPE_EXCEPTION = "Unsupported line type";
    private static final Character WAITING_TIMELINE_OPERATION_SYMBOL = 'C';
    private static final Character QUERY_OPERATION_SYMBOL = 'D';
    private static final Integer OPERATION_INDEX = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int quantityOfLines = Integer.parseInt(scanner.nextLine());
        List<Timeline> timelines = new ArrayList<>();
        TimelineService timelineService = new TimelineServiceImpl();

        Map<Character, LineParser> parserMap = new HashMap<>();
        parserMap.put(WAITING_TIMELINE_OPERATION_SYMBOL, new TimelineParser());
        parserMap.put(QUERY_OPERATION_SYMBOL, new QueryParser());

        for (int i = 0; i < quantityOfLines; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(REGEX);

            char lineType = splitLine[OPERATION_INDEX].charAt(0);
            LineParser parser = parserMap.get(lineType);

            try {
                if (parser != null) {
                    parser.parse(splitLine, timelines, timelineService);
                } else {
                    System.out.println(UNSUPPORTED_LINE_TYPE_EXCEPTION);
                }
            } catch (InvalidLineException | InvalidQueryException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

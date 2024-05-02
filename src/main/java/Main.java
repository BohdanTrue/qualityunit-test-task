import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int quantityOfLines = Integer.parseInt(scanner.nextLine());
        List<Timeline> timelines = new ArrayList<>();

        for (int i = 0; i < quantityOfLines; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            if (splitLine[0].startsWith("C")) {
                timelines.add(parseToTimeline(splitLine));
            }

            if (splitLine[0].startsWith("D")) {
                Query query = parseToQuery(splitLine);
                printAverageTime(timelines, query);
            }
        }
    }

    private static void printAverageTime(List<Timeline> timelines, Query query) {
        List<Integer> times = new ArrayList<>();
        Date dateFrom = query.getDateFrom();
        Date dateTo = query.getDateTo();

        for (Timeline timeline : timelines) {
            Date date = timeline.getDate();
            if ((!timeline.getServiceId().startsWith(query.getServiceId()) && !query.getServiceId().equals("*"))
                    || (!timeline.getQuestionTypeId().startsWith(query.getQuestionTypeId()) && !query.getQuestionTypeId().equals("*"))
                    || (!timeline.getResponseType().equals(query.getResponseType()) && !query.getResponseType().equals("*"))) {
                continue;
            }

            if (dateFrom.compareTo(date) <= 0 && dateTo.compareTo(date) >= 0) {
                times.add(timeline.getTime());
            }
        }

        System.out.println(times.isEmpty() ? "-" : times.stream()
                .mapToInt(Integer::intValue)
                .average()
                .getAsDouble());
    }

    private static Timeline parseToTimeline(String[] splitLine) {

        if (splitLine.length != 6) {
            throw new RuntimeException("Bad line");
        }

        Timeline timeline = new Timeline();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        timeline.setServiceId(splitLine[1]);
        timeline.setQuestionTypeId(splitLine[2]);
        timeline.setResponseType(splitLine[3]);
        try {
            Date date = dateFormat.parse(splitLine[4]);
            timeline.setDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        timeline.setTime(Integer.valueOf(splitLine[5]));

        return timeline;
    }

    private static Query parseToQuery(String[] splitLine) {
        if (splitLine.length != 5) {
            System.out.println("Bad query");
        }

        Query query = new Query();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        query.setServiceId(splitLine[1]);
        query.setQuestionTypeId(splitLine[2]);
        query.setResponseType(splitLine[3]);

        String[] dates = splitLine[4].split("-");
        if (dates.length > 2) {
            System.out.println("Bad request");
        }

        try {
            Date dateFrom = dateFormat.parse(dates[0]);
            query.setDateFrom(dateFrom);

            if (dates.length == 2) {
                Date dateTo = dateFormat.parse(dates[1]);
                query.setDateTo(dateTo);
            }


        } catch (ParseException e) {
            throw new RuntimeException("Date(-s) in bad format!");
        }

        return query;
    }
}

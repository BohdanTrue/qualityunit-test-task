package service;

import exception.InvalidLineException;
import exception.InvalidQueryException;
import model.Query;
import model.Timeline;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimelineServiceImpl implements TimelineService {
    private final static String ASTERISK = "*";
    private final static String REGEX = "-";
    private final static String INVALID_DATE_FORMAT_EXCEPTION = "Invalid date format";
    private final static String INVALID_TIMELINE_FORMAT_EXCEPTION = "Invalid timeline format";
    private final static String INVALID_QUERY_FORMAT_EXCEPTION = "Invalid query format";
    private final static String DATE_FORMAT = "dd.MM.yyyy";
    private final static Integer QUERY_LENGTH = 5;
    private final static Integer TIMELINE_LENGTH = 6;
    private final static Integer SERVICE_ID_INDEX = 1;
    private final static Integer QUESTION_TYPE_ID_INDEX = 2;
    private final static Integer RESPONSE_TYPE_INDEX = 3;
    private final static Integer DATE_INDEX = 4;
    private final static Integer TIME_INDEX = 5;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public Query parseLineToQuery(String[] splitLine) throws InvalidQueryException {
        if (splitLine.length != QUERY_LENGTH) {
            throw new InvalidQueryException(INVALID_QUERY_FORMAT_EXCEPTION);
        }

        Query query = new Query();

        query.setServiceId(splitLine[SERVICE_ID_INDEX]);
        query.setQuestionTypeId(splitLine[QUESTION_TYPE_ID_INDEX]);
        query.setResponseType(splitLine[RESPONSE_TYPE_INDEX]);

        String[] dates = splitLine[DATE_INDEX].split(REGEX);
        if (dates.length > 2 || dates.length < 1) {
            throw new InvalidQueryException(INVALID_DATE_FORMAT_EXCEPTION);
        }

        try {
            Date dateFrom = dateFormat.parse(dates[0]);
            query.setDateFrom(dateFrom);

            if (dates.length == 2) {
                Date dateTo = dateFormat.parse(dates[1]);
                query.setDateTo(dateTo);
            }


        } catch (ParseException e) {
            throw new InvalidQueryException(INVALID_DATE_FORMAT_EXCEPTION);
        }

        return query;
    }

    @Override
    public Timeline parseLineToTimeline(String[] splitLine) throws InvalidLineException {
        if (splitLine.length != TIMELINE_LENGTH) {
            throw new InvalidLineException(INVALID_TIMELINE_FORMAT_EXCEPTION);
        }

        Timeline timeline = new Timeline();

        timeline.setServiceId(splitLine[SERVICE_ID_INDEX]);
        timeline.setQuestionTypeId(splitLine[QUESTION_TYPE_ID_INDEX]);
        timeline.setResponseType(splitLine[RESPONSE_TYPE_INDEX]);

        try {
            Date date = dateFormat.parse(splitLine[DATE_INDEX]);
            timeline.setDate(date);
        } catch (ParseException e) {
            throw new InvalidLineException(INVALID_DATE_FORMAT_EXCEPTION);
        }

        timeline.setTime(Integer.valueOf(splitLine[TIME_INDEX]));

        return timeline;
    }

    @Override
    public void printAverageTime(List<Timeline> timelines, Query query) {
        List<Integer> times = timelines.stream()
                .filter(timeline -> isValidTimeline(timeline, query))
                .map(Timeline::getTime)
                .toList();

        double averageTime = times.isEmpty() ? -1 : times.stream().mapToInt(Integer::intValue).average().orElse(-1);
        System.out.println(averageTime == -1 ? "-" : averageTime);
    }

    private boolean isValidTimeline(Timeline timeline, Query query) {
        String queryServiceId = query.getServiceId();
        String queryQuestionTypeId = query.getQuestionTypeId();
        String queryResponseType = query.getResponseType();

        return (timeline.getServiceId().startsWith(queryServiceId) || ASTERISK.equals(queryServiceId))
                && (timeline.getQuestionTypeId().startsWith(queryQuestionTypeId) || ASTERISK.equals(queryQuestionTypeId))
                && (timeline.getResponseType().equals(queryResponseType) || ASTERISK.equals(queryResponseType))
                && (query.getDateFrom().compareTo(timeline.getDate()) <= 0)
                && (query.getDateTo() == null || query.getDateTo().compareTo(timeline.getDate()) >= 0);
    }
}

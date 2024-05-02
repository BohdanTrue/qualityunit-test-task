package service;

import exception.InvalidLineException;
import exception.InvalidQueryException;
import model.Query;
import model.Timeline;
import java.util.List;

public interface TimelineService {
    Query parseLineToQuery(String[] splitLine) throws InvalidQueryException;

    Timeline parseLineToTimeline(String[] splitLine) throws InvalidLineException;

    void printAverageTime(List<Timeline> timelines, Query query);
}

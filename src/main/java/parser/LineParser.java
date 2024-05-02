package parser;

import exception.InvalidLineException;
import exception.InvalidQueryException;
import model.Timeline;
import service.TimelineService;

import java.util.List;

public interface LineParser {
    void parse(String[] splitLine, List<Timeline> timelines, TimelineService timelineService) throws InvalidLineException, InvalidQueryException;
}
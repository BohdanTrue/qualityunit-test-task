package parser;

import exception.InvalidQueryException;
import model.Query;
import model.Timeline;
import service.TimelineService;
import java.util.List;

public class QueryParser implements LineParser {
    @Override
    public void parse(String[] splitLine,
                      List<Timeline> timelines,
                      TimelineService timelineService
    ) throws InvalidQueryException {
        Query query = timelineService.parseLineToQuery(splitLine);
        timelineService.printAverageTime(timelines, query);
    }
}
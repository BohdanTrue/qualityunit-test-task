package parser;

import exception.InvalidLineException;
import model.Timeline;
import service.TimelineService;
import java.util.List;

public class TimelineParser implements LineParser {
    @Override
    public void parse(String[] splitLine, List<Timeline> timelines, TimelineService timelineService) throws InvalidLineException {
        timelines.add(timelineService.parseLineToTimeline(splitLine));
    }
}
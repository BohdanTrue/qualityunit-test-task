//package service;
//
//import model.Query;
//import model.Timeline;
//
//import java.util.List;
//
//public class PrinterServiceImpl implements PrinterService {
//    private final String ASTERISK = "*";
//
//    @Override
//    public void printAverageTime(List<Timeline> timelines, Query query) {
//        List<Integer> times = timelines.stream()
//                .filter(timeline -> isValidTimeline(timeline, query))
//                .map(Timeline::getTime)
//                .toList();
//
//        double averageTime = times.isEmpty() ? -1 : times.stream().mapToInt(Integer::intValue).average().orElse(-1);
//        System.out.println(averageTime == -1 ? "-" : averageTime);
//    }
//
//    private boolean isValidTimeline(Timeline timeline, Query query) {
//        String queryServiceId = query.getServiceId();
//        String queryQuestionTypeId = query.getQuestionTypeId();
//        String queryResponseType = query.getResponseType();
//
//        return (timeline.getServiceId().startsWith(queryServiceId) || ASTERISK.equals(queryServiceId))
//                && (timeline.getQuestionTypeId().startsWith(queryQuestionTypeId) || ASTERISK.equals(queryQuestionTypeId))
//                && (timeline.getResponseType().equals(queryResponseType) || ASTERISK.equals(queryResponseType))
//                && (query.getDateFrom().compareTo(timeline.getDate()) <= 0)
//                && (query.getDateTo() == null || query.getDateTo().compareTo(timeline.getDate()) >= 0);
//    }
//}

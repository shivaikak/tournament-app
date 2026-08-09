import java.util.List;

public interface ScheduleStrategy {
    List<Match> generateSchedule(List<Team> teams);
    String getFormatName();
}

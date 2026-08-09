import java.io.Serializable;
import java.util.List;

public interface ScheduleStrategy
        extends Serializable {

    List<Match> generateSchedule(
        List<Team> teams
    );

    String getFormatName();
}

import java.util.ArrayList;
import java.util.List;

public class RoundRobinScheduler
        implements ScheduleStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public List<Match> generateSchedule(
        List<Team> teams
    ) {

        if (teams.size() < 2) {
            throw new IllegalStateException(
                "At least two teams are required."
            );
        }

        List<Match> matches =
            new ArrayList<>();

        int matchNumber = 1;

        for (
            int first = 0;
            first < teams.size();
            first++
        ) {

            for (
                int second = first + 1;
                second < teams.size();
                second++
            ) {

                matches.add(
                    new Match(
                        matchNumber++,
                        1,
                        teams.get(first),
                        teams.get(second)
                    )
                );
            }
        }

        return matches;
    }

    @Override
    public String getFormatName() {
        return "Round Robin";
    }
}

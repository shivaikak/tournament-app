import java.util.ArrayList;
import java.util.List;

public class RoundRobinScheduler
        implements ScheduleStrategy {

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

                Match match =
                    new Match(
                        matchNumber,
                        1,
                        teams.get(first),
                        teams.get(second)
                    );

                matches.add(match);

                matchNumber++;
            }
        }

        return matches;
    }

    @Override
    public String getFormatName() {
        return "Round Robin";
    }
}
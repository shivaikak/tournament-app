import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardStandingsPolicy
        implements StandingsPolicy {

    @Override
    public List<Standing> calculateStandings(
        List<Team> teams,
        List<Match> matches
    ) {

        Map<Team, Standing> standingsMap =
            new HashMap<>();

        for (Team team : teams) {
            standingsMap.put(
                team,
                new Standing(team)
            );
        }

        for (Match match : matches) {

            if (!match.isCompleted()) {
                continue;
            }

            Team homeTeam =
                match.getHomeTeam();

            Team awayTeam =
                match.getAwayTeam();

            Standing homeStanding =
                standingsMap.get(homeTeam);

            Standing awayStanding =
                standingsMap.get(awayTeam);

            int homeScore =
                match.getHomeScore();

            int awayScore =
                match.getAwayScore();

            if (homeScore > awayScore) {

                homeStanding.recordWin(
                    homeScore,
                    awayScore
                );

                awayStanding.recordLoss(
                    awayScore,
                    homeScore
                );

            } else if (
                awayScore > homeScore
            ) {

                awayStanding.recordWin(
                    awayScore,
                    homeScore
                );

                homeStanding.recordLoss(
                    homeScore,
                    awayScore
                );

            } else {

                homeStanding.recordDraw(
                    homeScore,
                    awayScore
                );

                awayStanding.recordDraw(
                    awayScore,
                    homeScore
                );
            }
        }

        List<Standing> standings =
            new ArrayList<>(
                standingsMap.values()
            );

        standings.sort(
            Comparator
                .comparingInt(
                    Standing::getPoints
                )
                .thenComparingInt(
                    Standing::getScoreDifference
                )
                .reversed()
        );

        return standings;
    }
}
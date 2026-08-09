import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournament {

    private final String name;
    private final String sport;

    private TournamentStatus status;

    private final List<Team> teams;

    private List<Match> matches;

    private final ScheduleStrategy scheduleStrategy;

    public Tournament(
        String name,
        String sport,
        ScheduleStrategy scheduleStrategy
    ) {

        if (
            name == null
            || name.isBlank()
        ) {

            throw new IllegalArgumentException(
                "Tournament name cannot be empty."
            );
        }

        this.name = name;
        this.sport = sport;

        this.scheduleStrategy =
            scheduleStrategy;

        teams = new ArrayList<>();
        matches = new ArrayList<>();

        status =
            TournamentStatus.REGISTRATION;
    }

    public void addTeam(Team team) {

        if (
            status
            != TournamentStatus.REGISTRATION
        ) {

            throw new IllegalStateException(
                "Teams cannot be added after " +
                "the tournament begins."
            );
        }

        for (Team existingTeam : teams) {

            if (
                existingTeam
                    .getName()
                    .equalsIgnoreCase(
                        team.getName()
                    )
            ) {

                throw new IllegalArgumentException(
                    "Team already exists."
                );
            }
        }

        teams.add(team);
    }

    public void generateSchedule() {

        if (
            status
            != TournamentStatus.REGISTRATION
        ) {

            throw new IllegalStateException(
                "Schedule has already been generated."
            );
        }

        matches =
            scheduleStrategy
                .generateSchedule(teams);

        status =
            TournamentStatus.IN_PROGRESS;
    }

    public void recordMatchScore(
        int matchNumber,
        int homeScore,
        int awayScore
    ) {

        Match match =
            findMatch(matchNumber);

        match.recordScore(
            homeScore,
            awayScore
        );

        checkCompletion();
    }

    public Match findMatch(
        int matchNumber
    ) {

        for (Match match : matches) {

            if (
                match.getMatchNumber()
                == matchNumber
            ) {

                return match;
            }
        }

        throw new IllegalArgumentException(
            "Match not found."
        );
    }

    private void checkCompletion() {

        if (matches.isEmpty()) {
            return;
        }

        boolean allCompleted = true;

        for (Match match : matches) {

            if (!match.isCompleted()) {
                allCompleted = false;
                break;
            }
        }

        if (allCompleted) {

            status =
                TournamentStatus.COMPLETED;
        }
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public String getFormatName() {
        return scheduleStrategy
            .getFormatName();
    }

    public List<Team> getTeams() {

        return Collections
            .unmodifiableList(teams);
    }

    public List<Match> getMatches() {

        return Collections
            .unmodifiableList(matches);
    }

    @Override
    public String toString() {

        return name +
               " | " +
               sport +
               " | " +
               getFormatName() +
               " | " +
               status;
    }
}
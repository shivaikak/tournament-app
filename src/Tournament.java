import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournament
        implements Serializable {

    private static final long serialVersionUID = 1L;

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

        if (
            sport == null
            || sport.isBlank()
        ) {

            throw new IllegalArgumentException(
                "Sport cannot be empty."
            );
        }

        this.name =
            name.trim();

        this.sport =
            sport.trim();

        this.scheduleStrategy =
            scheduleStrategy;

        teams =
            new ArrayList<>();

        matches =
            new ArrayList<>();

        status =
            TournamentStatus.REGISTRATION;
    }

    public void addTeam(
        Team team
    ) {

        if (
            status
            != TournamentStatus.REGISTRATION
        ) {

            throw new IllegalStateException(
                "Teams cannot be added after "
                + "the tournament begins."
            );
        }

        for (
            Team existingTeam
            : teams
        ) {

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

        teams.add(
            team
        );
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
                .generateSchedule(
                    teams
                );

        status =
            TournamentStatus.IN_PROGRESS;
    }

    public void recordMatchScore(
        int matchNumber,
        int homeScore,
        int awayScore
    ) {

        Match match =
            findMatch(
                matchNumber
            );

        match.recordScore(
            homeScore,
            awayScore
        );

        checkCompletion();
    }

    public Match findMatch(
        int matchNumber
    ) {

        for (
            Match match
            : matches
        ) {

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

        for (
            Match match
            : matches
        ) {

            if (!match.isCompleted()) {
                return;
            }
        }

        status =
            TournamentStatus.COMPLETED;
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
            .unmodifiableList(
                teams
            );
    }

    public List<Match> getMatches() {

        return Collections
            .unmodifiableList(
                matches
            );
    }

    public List<Match> getCompletedMatches() {

        List<Match> completed =
            new ArrayList<>();

        for (
            Match match
            : matches
        ) {

            if (match.isCompleted()) {

                completed.add(
                    match
                );
            }
        }

        return completed;
    }

    @Override
    public String toString() {

        return name
            + " | "
            + sport
            + " | "
            + getFormatName()
            + " | "
            + status;
    }
}

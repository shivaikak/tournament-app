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

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Tournament name cannot be blank. Please enter a tournament name."
            );
        }

        if (sport == null || sport.isBlank()) {
            throw new IllegalArgumentException(
                "Sport cannot be blank. Please enter the sport for this tournament."
            );
        }

        if (scheduleStrategy == null) {
            throw new IllegalArgumentException(
                "Tournament format is missing. Please choose Round Robin or Single Elimination."
            );
        }

        this.name = name.trim();
        this.sport = sport.trim();
        this.scheduleStrategy = scheduleStrategy;

        teams = new ArrayList<>();
        matches = new ArrayList<>();

        status = TournamentStatus.REGISTRATION;
    }

    public void addTeam(Team team) {

        if (status != TournamentStatus.REGISTRATION) {
            throw new IllegalStateException(
                "Teams can only be added while the tournament is in REGISTRATION. "
                + "A schedule has already been generated for this tournament."
            );
        }

        for (Team existingTeam : teams) {

            if (existingTeam.getName().equalsIgnoreCase(team.getName())) {
                throw new IllegalArgumentException(
                    "A team named \"" + team.getName()
                    + "\" is already registered in this tournament. Please use a different team name."
                );
            }
        }

        teams.add(team);
    }

    public void generateSchedule() {

        if (status != TournamentStatus.REGISTRATION) {
            throw new IllegalStateException(
                "The schedule cannot be generated again because this tournament has already started."
            );
        }

        if (teams.size() < 2) {
            throw new IllegalStateException(
                "A schedule cannot be generated yet. Register at least 2 teams first."
            );
        }

        matches = scheduleStrategy.generateSchedule(teams);
        status = TournamentStatus.IN_PROGRESS;
    }

    public void recordMatchScore(
        int matchNumber,
        int homeScore,
        int awayScore
    ) {

        Match match = findMatch(matchNumber);

        if (match.isCompleted()) {
            throw new IllegalStateException(
                "Match " + matchNumber + " already has a recorded final score."
            );
        }

        match.recordScore(homeScore, awayScore);
        checkCompletion();
    }

    public Match findMatch(int matchNumber) {

        for (Match match : matches) {

            if (match.getMatchNumber() == matchNumber) {
                return match;
            }
        }

        throw new IllegalArgumentException(
            "Match " + matchNumber
            + " was not found in this tournament. Please choose a match number shown in the schedule."
        );
    }

    private void checkCompletion() {

        if (matches.isEmpty()) {
            return;
        }

        for (Match match : matches) {

            if (!match.isCompleted()) {
                return;
            }
        }

        status = TournamentStatus.COMPLETED;
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
        return scheduleStrategy.getFormatName();
    }

    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public List<Match> getCompletedMatches() {

        List<Match> completed = new ArrayList<>();

        for (Match match : matches) {

            if (match.isCompleted()) {
                completed.add(match);
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentManager {
    private final List<Tournament> tournaments;
    private final StandingsService standingsService;

    public TournamentManager() {
        tournaments = new ArrayList<>();
        standingsService = new StandingsService(new StandardStandingsPolicy());
    }

    public Tournament createTournament(String name, String sport, ScheduleStrategy strategy) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("A tournament with that name already exists.");
            }
        }

        Tournament tournament = new Tournament(name, sport, strategy);
        tournaments.add(tournament);
        return tournament;
    }

    public Tournament findTournament(String name) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equalsIgnoreCase(name)) return tournament;
        }

        throw new IllegalArgumentException("Tournament not found.");
    }

    public void addTeam(Tournament tournament, Team team) {
        tournament.addTeam(team);
    }

    public void generateSchedule(Tournament tournament) {
        tournament.generateSchedule();
    }

    public void recordScore(Tournament tournament, int matchNumber, int homeScore, int awayScore) {
        tournament.recordMatchScore(matchNumber, homeScore, awayScore);
    }

    public List<Standing> getStandings(Tournament tournament) {
        return standingsService.getStandings(tournament);
    }

    public Team getLeader(Tournament tournament) {
        return standingsService.getLeader(tournament);
    }

    public List<Tournament> getTournaments() {
        return Collections.unmodifiableList(tournaments);
    }
}

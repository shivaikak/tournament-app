import java.util.List;

public class StandingsService {

    private final StandingsPolicy policy;

    public StandingsService(
        StandingsPolicy policy
    ) {

        this.policy = policy;
    }

    public List<Standing> getStandings(
        Tournament tournament
    ) {

        return policy.calculateStandings(
            tournament.getTeams(),
            tournament.getMatches()
        );
    }

    public Team getLeader(
        Tournament tournament
    ) {

        List<Standing> standings =
            getStandings(tournament);

        if (standings.isEmpty()) {
            return null;
        }

        return standings
            .get(0)
            .getTeam();
    }
}
import java.util.List;

public interface StandingsPolicy {

    List<Standing> calculateStandings(
        List<Team> teams,
        List<Match> matches
    );
}
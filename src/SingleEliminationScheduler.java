import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleEliminationScheduler implements ScheduleStrategy {

    @Override
    public List<Match> generateSchedule(List<Team> teams) {
        validateTeamCount(teams.size());

        List<Team> shuffledTeams = new ArrayList<>(teams);
        Collections.shuffle(shuffledTeams);

        List<List<BracketMatch>> rounds = new ArrayList<>();
        List<Match> allMatches = new ArrayList<>();

        int totalRounds = (int) (Math.log(teams.size()) / Math.log(2));
        int matchNumber = 1;

        for (int round = 1; round <= totalRounds; round++) {
            int matchesInRound = teams.size() / (int) Math.pow(2, round);
            List<BracketMatch> roundMatches = new ArrayList<>();

            for (int i = 0; i < matchesInRound; i++) {
                BracketMatch match = new BracketMatch(matchNumber++, round, null, null);
                roundMatches.add(match);
                allMatches.add(match);
            }

            rounds.add(roundMatches);
        }

        assignFirstRound(rounds.get(0), shuffledTeams);
        connectRounds(rounds);

        return allMatches;
    }

    private void assignFirstRound(List<BracketMatch> firstRound, List<Team> teams) {
        int index = 0;

        for (BracketMatch match : firstRound) {
            match.setHomeTeam(teams.get(index++));
            match.setAwayTeam(teams.get(index++));
        }
    }

    private void connectRounds(List<List<BracketMatch>> rounds) {
        for (int round = 0; round < rounds.size() - 1; round++) {
            List<BracketMatch> currentRound = rounds.get(round);
            List<BracketMatch> nextRound = rounds.get(round + 1);

            for (int i = 0; i < currentRound.size(); i++) {
                currentRound.get(i).setNextMatch(nextRound.get(i / 2), i % 2 == 0);
            }
        }
    }

    private void validateTeamCount(int teamCount) {
        if (teamCount < 2) {
            throw new IllegalArgumentException("At least two teams are required.");
        }

        boolean powerOfTwo = (teamCount & (teamCount - 1)) == 0;

        if (!powerOfTwo) {
            throw new IllegalArgumentException(
                "Single elimination currently requires 2, 4, 8, 16, etc. teams."
            );
        }
    }

    @Override
    public String getFormatName() {
        return "Single Elimination";
    }
}

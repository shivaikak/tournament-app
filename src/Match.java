import java.io.Serializable;

public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int matchNumber;
    private final int roundNumber;

    private Team homeTeam;
    private Team awayTeam;

    private Integer homeScore;
    private Integer awayScore;

    private MatchStatus status;

    public Match(
        int matchNumber,
        int roundNumber,
        Team homeTeam,
        Team awayTeam
    ) {

        this.matchNumber = matchNumber;
        this.roundNumber = roundNumber;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        if (homeTeam == null || awayTeam == null) {
            status = MatchStatus.WAITING;
        } else {
            status = MatchStatus.SCHEDULED;
        }
    }

    public void recordScore(
        int homeScore,
        int awayScore
    ) {

        if (!isReadyToPlay()) {
            throw new IllegalStateException(
                "This match is not ready to be scored yet. Both teams must be assigned first."
            );
        }

        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException(
                "Scores cannot be negative. Please enter 0 or a positive whole number for both teams."
            );
        }

        this.homeScore = homeScore;
        this.awayScore = awayScore;

        status = MatchStatus.COMPLETED;
    }

    public Team getWinner() {

        if (!isCompleted()) {
            throw new IllegalStateException(
                "A winner cannot be determined because this match has not been completed yet."
            );
        }

        if (homeScore > awayScore) {
            return homeTeam;
        }

        if (awayScore > homeScore) {
            return awayTeam;
        }

        return null;
    }

    public boolean isReadyToPlay() {
        return homeTeam != null
            && awayTeam != null
            && status != MatchStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return status == MatchStatus.COMPLETED;
    }

    public void setHomeTeam(Team team) {
        homeTeam = team;
        updateStatus();
    }

    public void setAwayTeam(Team team) {
        awayTeam = team;
        updateStatus();
    }

    private void updateStatus() {

        if (
            homeTeam != null
            && awayTeam != null
            && status != MatchStatus.COMPLETED
        ) {
            status = MatchStatus.SCHEDULED;
        }
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public MatchStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {

        String home =
            homeTeam == null
                ? "TBD"
                : homeTeam.getName();

        String away =
            awayTeam == null
                ? "TBD"
                : awayTeam.getName();

        if (isCompleted()) {

            return "Match "
                + matchNumber
                + ": "
                + home
                + " (Home) "
                + homeScore
                + " - "
                + awayScore
                + " "
                + away
                + " (Away)";
        }

        return "Match "
            + matchNumber
            + ": "
            + home
            + " (Home) vs "
            + away
            + " (Away)";
    }
}

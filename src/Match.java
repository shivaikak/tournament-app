public class Match {
    private final int matchNumber;
    private final int roundNumber;
    private Team homeTeam;
    private Team awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private MatchStatus status;

    public Match(int matchNumber, int roundNumber, Team homeTeam, Team awayTeam) {
        this.matchNumber = matchNumber;
        this.roundNumber = roundNumber;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        status = (homeTeam == null || awayTeam == null) ? MatchStatus.WAITING : MatchStatus.SCHEDULED;
    }

    public void recordScore(int homeScore, int awayScore) {
        if (!isReadyToPlay()) throw new IllegalStateException("Both teams must be assigned before recording a score.");
        if (homeScore < 0 || awayScore < 0) throw new IllegalArgumentException("Scores cannot be negative.");
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        status = MatchStatus.COMPLETED;
    }

    public Team getWinner() {
        if (!isCompleted()) throw new IllegalStateException("Match has not been completed.");
        if (homeScore > awayScore) return homeTeam;
        if (awayScore > homeScore) return awayTeam;
        return null;
    }

    public boolean isReadyToPlay() {
        return homeTeam != null && awayTeam != null && status != MatchStatus.COMPLETED;
    }

    public boolean isCompleted() { return status == MatchStatus.COMPLETED; }

    public void setHomeTeam(Team team) {
        homeTeam = team;
        updateStatus();
    }

    public void setAwayTeam(Team team) {
        awayTeam = team;
        updateStatus();
    }

    private void updateStatus() {
        if (homeTeam != null && awayTeam != null && status != MatchStatus.COMPLETED) {
            status = MatchStatus.SCHEDULED;
        }
    }

    public int getMatchNumber() { return matchNumber; }
    public int getRoundNumber() { return roundNumber; }
    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public Integer getHomeScore() { return homeScore; }
    public Integer getAwayScore() { return awayScore; }
    public MatchStatus getStatus() { return status; }

    @Override
    public String toString() {
        String home = homeTeam == null ? "TBD" : homeTeam.getName();
        String away = awayTeam == null ? "TBD" : awayTeam.getName();
        if (isCompleted()) {
            return "Match " + matchNumber + ": " + home + " " + homeScore + " - " + awayScore + " " + away;
        }
        return "Match " + matchNumber + ": " + home + " vs " + away;
    }
}

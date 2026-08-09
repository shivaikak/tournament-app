public class Standing {
    private final Team team;
    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int points;
    private int scored;
    private int conceded;

    public Standing(Team team) { this.team = team; }

    public void recordWin(int scored, int conceded) {
        played++;
        wins++;
        points += 3;
        this.scored += scored;
        this.conceded += conceded;
    }

    public void recordDraw(int scored, int conceded) {
        played++;
        draws++;
        points++;
        this.scored += scored;
        this.conceded += conceded;
    }

    public void recordLoss(int scored, int conceded) {
        played++;
        losses++;
        this.scored += scored;
        this.conceded += conceded;
    }

    public Team getTeam() { return team; }
    public int getPlayed() { return played; }
    public int getWins() { return wins; }
    public int getDraws() { return draws; }
    public int getLosses() { return losses; }
    public int getPoints() { return points; }
    public int getScored() { return scored; }
    public int getConceded() { return conceded; }
    public int getScoreDifference() { return scored - conceded; }
}

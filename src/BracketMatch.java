public class BracketMatch extends Match {

    private BracketMatch nextMatch;

    private boolean winnerGoesToHomeSlot;

    public BracketMatch(
        int matchNumber,
        int roundNumber,
        Team homeTeam,
        Team awayTeam
    ) {

        super(
            matchNumber,
            roundNumber,
            homeTeam,
            awayTeam
        );
    }

    public void setNextMatch(
        BracketMatch nextMatch,
        boolean winnerGoesToHomeSlot
    ) {

        this.nextMatch = nextMatch;
        this.winnerGoesToHomeSlot =
            winnerGoesToHomeSlot;
    }

    @Override
    public void recordScore(
        int homeScore,
        int awayScore
    ) {

        if (homeScore == awayScore) {
            throw new IllegalArgumentException(
                "Elimination matches cannot end in a tie."
            );
        }

        super.recordScore(
            homeScore,
            awayScore
        );

        advanceWinner();
    }

    private void advanceWinner() {

        if (nextMatch == null) {
            // This was the championship match
            return;
        }

        Team winner = getWinner();

        if (winnerGoesToHomeSlot) {
            nextMatch.setHomeTeam(winner);
        } else {
            nextMatch.setAwayTeam(winner);
        }
    }

    public BracketMatch getNextMatch() {
        return nextMatch;
    }
}
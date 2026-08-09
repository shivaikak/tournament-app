public class TournamentApp {

    public static void main(String[] args) {

        TournamentRepository repository =
            new FileTournamentRepository("tournaments.bin");

        TournamentManager manager =
            new TournamentManager(repository);

        TournamentCLI cli =
            new TournamentCLI(manager);

        cli.start();
    }
}

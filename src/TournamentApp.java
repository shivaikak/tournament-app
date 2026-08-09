public class TournamentApp {

    public static void main(String[] args) {
        TournamentManager manager = new TournamentManager();

        TournamentCLI cli = new TournamentCLI(manager);

        cli.start();
    }
}
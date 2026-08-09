import java.util.List;
import java.util.Scanner;

public class TournamentCLI {

    private final Scanner scanner;

    private final TournamentManager manager;

    private int nextTeamId = 1;

    public TournamentCLI(
        TournamentManager manager
    ) {

        scanner = new Scanner(System.in);

        this.manager = manager;
    }

    public void start() {

        boolean running = true;

        while (running) {

            printMainMenu();

            int choice =
                readInt("Choose an option: ");

            try {

                switch (choice) {

                    case 1 ->
                        createTournament();

                    case 2 ->
                        viewTournaments();

                    case 3 ->
                        manageTournament();

                    case 0 ->
                        running = false;

                    default ->
                        System.out.println(
                            "Invalid option."
                        );
                }

            } catch (
                IllegalArgumentException
                | IllegalStateException e
            ) {

                System.out.println(
                    "Error: "
                    + e.getMessage()
                );
            }
        }

        System.out.println(
            "Goodbye!"
        );
    }

    private void printMainMenu() {

        System.out.println();
        System.out.println(
            "=== Sports Tournament Manager ==="
        );

        System.out.println(
            "1. Create Tournament"
        );

        System.out.println(
            "2. View Tournaments"
        );

        System.out.println(
            "3. Manage Tournament"
        );

        System.out.println(
            "0. Exit"
        );
    }

    private void createTournament() {

        String name =
            readString(
                "Tournament name: "
            );

        String sport =
            readString(
                "Sport: "
            );

        System.out.println(
            "1. Round Robin"
        );

        System.out.println(
            "2. Single Elimination"
        );

        int format =
            readInt(
                "Select format: "
            );

        ScheduleStrategy strategy;

        if (format == 1) {

            strategy =
                new RoundRobinScheduler();

        } else if (format == 2) {

            strategy =
                new SingleEliminationScheduler();

        } else {

            throw new IllegalArgumentException(
                "Invalid format."
            );
        }

        manager.createTournament(
            name,
            sport,
            strategy
        );

        System.out.println(
            "Tournament created."
        );
    }

    private void viewTournaments() {

        List<Tournament> tournaments =
            manager.getTournaments();

        if (tournaments.isEmpty()) {

            System.out.println(
                "No tournaments found."
            );

            return;
        }

        for (
            Tournament tournament
            : tournaments
        ) {

            System.out.println(
                tournament
            );
        }
    }

    private void manageTournament() {

        String name =
            readString(
                "Tournament name: "
            );

        Tournament tournament =
            manager.findTournament(name);

        boolean managing = true;

        while (managing) {

            printTournamentMenu(
                tournament
            );

            int choice =
                readInt(
                    "Choose an option: "
                );

            try {

                switch (choice) {

                    case 1 ->
                        addTeam(tournament);

                    case 2 ->
                        viewTeams(tournament);

                    case 3 -> {
                        manager.generateSchedule(
                            tournament
                        );

                        System.out.println(
                            "Schedule generated."
                        );
                    }

                    case 4 ->
                        displaySchedule(
                            tournament
                        );

                    case 5 ->
                        recordScore(
                            tournament
                        );

                    case 6 ->
                        displayStandings(
                            tournament
                        );

                    case 0 ->
                        managing = false;

                    default ->
                        System.out.println(
                            "Invalid option."
                        );
                }

            } catch (
                IllegalArgumentException
                | IllegalStateException e
            ) {

                System.out.println(
                    "Error: "
                    + e.getMessage()
                );
            }
        }
    }

    private void printTournamentMenu(
        Tournament tournament
    ) {

        System.out.println();

        System.out.println(
            "=== "
            + tournament.getName()
            + " ==="
        );

        System.out.println(
            "Status: "
            + tournament.getStatus()
        );

        System.out.println(
            "1. Add Team"
        );

        System.out.println(
            "2. View Teams"
        );

        System.out.println(
            "3. Generate Schedule"
        );

        System.out.println(
            "4. View Schedule / Bracket"
        );

        System.out.println(
            "5. Record Score"
        );

        System.out.println(
            "6. View Standings"
        );

        System.out.println(
            "0. Back"
        );
    }

    private void addTeam(
        Tournament tournament
    ) {

        String teamName =
            readString(
                "Team name: "
            );

        String coachName =
            readString(
                "Coach name: "
            );

        Team team =
            new Team(
                "T" + nextTeamId++,
                teamName,
                coachName
            );

        manager.addTeam(
            tournament,
            team
        );

        System.out.println(
            "Team registered."
        );
    }

    private void viewTeams(
        Tournament tournament
    ) {

        for (
            Team team
            : tournament.getTeams()
        ) {

            System.out.println(
                team
            );
        }
    }

    private void displaySchedule(
        Tournament tournament
    ) {

        if (
            tournament
                .getMatches()
                .isEmpty()
        ) {

            System.out.println(
                "No schedule generated."
            );

            return;
        }

        int currentRound = -1;

        for (
            Match match
            : tournament.getMatches()
        ) {

            if (
                match.getRoundNumber()
                != currentRound
            ) {

                currentRound =
                    match.getRoundNumber();

                System.out.println(
                    "\nRound "
                    + currentRound
                );
            }

            System.out.println(
                match
            );
        }
    }

    private void recordScore(
        Tournament tournament
    ) {

        displaySchedule(
            tournament
        );

        int matchNumber =
            readInt(
                "Match number: "
            );

        int homeScore =
            readInt(
                "Home score: "
            );

        int awayScore =
            readInt(
                "Away score: "
            );

        manager.recordScore(
            tournament,
            matchNumber,
            homeScore,
            awayScore
        );

        System.out.println(
            "Score recorded."
        );
    }

    private void displayStandings(
        Tournament tournament
    ) {

        List<Standing> standings =
            manager.getStandings(
                tournament
            );

        System.out.println(
            "\n=== Standings ==="
        );

        int rank = 1;

        for (
            Standing standing
            : standings
        ) {

            System.out.println(
                rank++
                + ". "
                + standing
            );
        }
    }

    private int readInt(
        String message
    ) {

        while (true) {

            System.out.print(
                message
            );

            String input =
                scanner.nextLine();

            try {

                return Integer.parseInt(
                    input
                );

            } catch (
                NumberFormatException e
            ) {

                System.out.println(
                    "Enter a valid number."
                );
            }
        }
    }

    private String readString(
        String message
    ) {

        System.out.print(
            message
        );

        return scanner
            .nextLine()
            .trim();
    }
}
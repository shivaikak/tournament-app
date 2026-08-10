import java.util.List;
import java.util.Scanner;

public class TournamentCLI {

    private final Scanner scanner;
    private final TournamentManager manager;

    private int nextTeamId = 1;

    public TournamentCLI(
        TournamentManager manager
    ) {

        scanner =
            new Scanner(
                System.in
            );

        this.manager =
            manager;

        initializeNextTeamId();
    }

    private void initializeNextTeamId() {

        int highestId = 0;

        for (
            Tournament tournament
            : manager.getTournaments()
        ) {

            for (
                Team team
                : tournament.getTeams()
            ) {

                String id =
                    team.getId();

                if (
                    id != null
                    && id.startsWith("T")
                ) {

                    try {

                        int number =
                            Integer.parseInt(
                                id.substring(1)
                            );

                        if (
                            number
                            > highestId
                        ) {

                            highestId =
                                number;
                        }

                    } catch (
                        NumberFormatException ignored
                    ) {
                    }
                }
            }
        }

        nextTeamId =
            highestId + 1;
    }

    public void start() {

        boolean running =
            true;

        while (running) {

            printMainMenu();

            int choice =
                readInt(
                    "Choose an option: "
                );

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
                    "Unable to complete that action: "
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

        System.out.println();

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

        } else if (
            format == 2
        ) {

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

        System.out.println();

        System.out.println(
            "Tournament created successfully."
        );

        waitForBack();
    }

    private void viewTournaments() {

        while (true) {

            System.out.println();

            System.out.println(
                "=== Tournaments ==="
            );

            List<Tournament> tournaments =
                manager.getTournaments();

            if (
                tournaments.isEmpty()
            ) {

                System.out.println(
                    "No tournaments found."
                );

            } else {

                for (
                    int i = 0;
                    i < tournaments.size();
                    i++
                ) {

                    System.out.println(
                        (i + 1)
                        + ". "
                        + tournaments.get(i)
                    );
                }
            }

            System.out.println();

            System.out.println(
                "0. Back"
            );

            int choice =
                readInt(
                    "Choose an option: "
                );

            if (choice == 0) {
                return;
            }

            System.out.println(
                "Invalid option."
            );
        }
    }

    private void manageTournament() {

        String name =
            readString(
                "Tournament name: "
            );

        Tournament tournament =
            manager.findTournament(
                name
            );

        boolean managing =
            true;

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
                        addTeam(
                            tournament
                        );

                    case 2 ->
                        viewTeams(
                            tournament
                        );

                    case 3 ->
                        generateSchedule(
                            tournament
                        );

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

                    case 7 ->
                        displayCompletedMatches(
                            tournament
                        );

                    case 8 ->
                        displayLeader(
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
                    "Unable to complete that action: "
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
            "Sport: "
            + tournament.getSport()
        );

        System.out.println(
            "Format: "
            + tournament.getFormatName()
        );

        System.out.println(
            "Status: "
            + tournament.getStatus()
        );

        System.out.println();

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
            "7. View Completed Matches"
        );

        System.out.println(
            "8. View Current Leader"
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

        Team team =
            new Team(
                "T"
                + nextTeamId++,
                teamName
            );

        manager.addTeam(
            tournament,
            team
        );

        System.out.println();

        System.out.println(
            "Team registered successfully."
        );

        waitForBack();
    }

    private void viewTeams(
        Tournament tournament
    ) {

        System.out.println();

        System.out.println(
            "=== Teams ==="
        );

        if (
            tournament
                .getTeams()
                .isEmpty()
        ) {

            System.out.println(
                "No teams registered."
            );

        } else {

            for (
                Team team
                : tournament.getTeams()
            ) {

                System.out.println(
                    team
                );
            }
        }

        waitForBack();
    }

    private void generateSchedule(
        Tournament tournament
    ) {

        manager.generateSchedule(
            tournament
        );

        System.out.println();

        System.out.println(
            "Schedule generated successfully."
        );

        waitForBack();
    }

    private void displaySchedule(
        Tournament tournament
    ) {

        System.out.println();

        System.out.println(
            "=== Schedule / Bracket ==="
        );

        if (
            tournament
                .getMatches()
                .isEmpty()
        ) {

            System.out.println(
                "No schedule generated."
            );

            waitForBack();

            return;
        }

        int currentRound =
            -1;

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

                System.out.println();

                System.out.println(
                    "Round "
                    + currentRound
                );

                System.out.println(
                    "--------------------"
                );
            }

            System.out.println(
                match
            );
        }

        waitForBack();
    }

    private void recordScore(
        Tournament tournament
    ) {

        if (tournament.getMatches().isEmpty()) {

            System.out.println();
            System.out.println(
                "No schedule has been generated yet. Generate the schedule before recording scores."
            );

            waitForBack();
            return;
        }

        System.out.println();
        System.out.println(
            "=== Record Match Score ==="
        );

        boolean foundIncomplete = false;

        for (Match match : tournament.getMatches()) {

            if (!match.isCompleted()) {
                System.out.println(match);
                foundIncomplete = true;
            }
        }

        if (!foundIncomplete) {

            System.out.println(
                "There are no matches left to score. Every match in this tournament is already completed."
            );

            waitForBack();
            return;
        }

        System.out.println();

        int matchNumber =
            readInt(
                "Enter the match number you want to score: "
            );

        Match selectedMatch =
            tournament.findMatch(
                matchNumber
            );

        if (selectedMatch.isCompleted()) {
            throw new IllegalStateException(
                "Match " + matchNumber
                + " has already been completed. Please select one of the unfinished matches shown above."
            );
        }

        if (!selectedMatch.isReadyToPlay()) {
            throw new IllegalStateException(
                "Match " + matchNumber
                + " is not ready yet because one or both teams are still TBD. "
                + "Complete the earlier bracket match(es) first."
            );
        }

        String homeTeamName =
            selectedMatch
                .getHomeTeam()
                .getName();

        String awayTeamName =
            selectedMatch
                .getAwayTeam()
                .getName();

        System.out.println();
        System.out.println(
            "Selected Match: "
            + homeTeamName
            + " (Home) vs "
            + awayTeamName
            + " (Away)"
        );

        int homeScore =
            readInt(
                "Enter score for "
                + homeTeamName
                + " (Home): "
            );

        int awayScore =
            readInt(
                "Enter score for "
                + awayTeamName
                + " (Away): "
            );

        manager.recordScore(
            tournament,
            matchNumber,
            homeScore,
            awayScore
        );

        System.out.println();
        System.out.println(
            "Score recorded successfully: "
            + homeTeamName
            + " (Home) "
            + homeScore
            + " - "
            + awayScore
            + " "
            + awayTeamName
            + " (Away)"
        );

        waitForBack();
    }

    private void displayStandings(
        Tournament tournament
    ) {

        System.out.println();

        System.out.println(
            "=== Standings ==="
        );

        List<Standing> standings =
            manager.getStandings(
                tournament
            );

        if (
            standings.isEmpty()
        ) {

            System.out.println(
                "No standings available."
            );

            waitForBack();

            return;
        }

        System.out.printf(
            "%-5s %-20s %-13s %-8s %-8s %-8s %-10s %-13s %-14s %-16s%n",
            "Rank",
            "Team",
            "Games Played",
            "Wins",
            "Draws",
            "Losses",
            "Points",
            "Goals Scored",
            "Goals Allowed",
            "Goal Difference"
        );

        System.out.println(
            "----------------------------------------------------------------------------------------------------------------"
        );

        int rank =
            1;

        for (
            Standing standing
            : standings
        ) {

            System.out.printf(
                "%-5d %-20s %-13d %-8d %-8d %-8d %-10d %-13d %-14d %-16d%n",
                rank++,
                standing
                    .getTeam()
                    .getName(),
                standing
                    .getPlayed(),
                standing
                    .getWins(),
                standing
                    .getDraws(),
                standing
                    .getLosses(),
                standing
                    .getPoints(),
                standing
                    .getScored(),
                standing
                    .getConceded(),
                standing
                    .getScoreDifference()
            );
        }

        waitForBack();
    }

    private void displayCompletedMatches(
        Tournament tournament
    ) {

        System.out.println();

        System.out.println(
            "=== Completed Matches ==="
        );

        List<Match> completedMatches =
            tournament
                .getCompletedMatches();

        if (
            completedMatches.isEmpty()
        ) {

            System.out.println(
                "No completed matches yet."
            );

        } else {

            for (
                Match match
                : completedMatches
            ) {

                System.out.println(
                    match
                );
            }
        }

        waitForBack();
    }

    private void displayLeader(
        Tournament tournament
    ) {

        System.out.println();

        System.out.println(
            "=== Current Leader ==="
        );

        Team leader =
            manager.getLeader(
                tournament
            );

        if (leader == null) {

            System.out.println(
                "No leader can be determined yet."
            );

        } else {

            System.out.println(
                leader.getName()
            );
        }

        waitForBack();
    }

    private void waitForBack() {

        while (true) {

            System.out.println();

            System.out.println(
                "0. Back"
            );

            int choice =
                readInt(
                    "Choose an option: "
                );

            if (choice == 0) {
                return;
            }

            System.out.println(
                "Invalid option."
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
                    "Invalid input. Please enter a whole number using digits only."
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

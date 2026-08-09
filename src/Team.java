public class Team {

    private final String id;
    private String name;

    public Team(String id, String name, String coachName) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                "Team ID cannot be empty."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Team name cannot be empty."
            );
        }

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

  

    public void updateInformation(
        String newName
    ) {

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException(
                "Team name cannot be empty."
            );
        }

        this.name = newName;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
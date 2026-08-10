import java.io.Serializable;

public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String name;

    public Team(String id, String name) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                "Team ID is missing. Please create the team with a valid ID."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Team name cannot be blank. Please enter a team name."
            );
        }

        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return id + " - " + name;
    }
}

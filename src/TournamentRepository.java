import java.util.List;

public interface TournamentRepository {

    void save(
        List<Tournament> tournaments
    );

    List<Tournament> load();
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileTournamentRepository
        implements TournamentRepository {

    private final String fileName;

    public FileTournamentRepository(
        String fileName
    ) {

        this.fileName =
            fileName;
    }

    @Override
    public void save(
        List<Tournament> tournaments
    ) {

        try (
            ObjectOutputStream output =
                new ObjectOutputStream(
                    new FileOutputStream(
                        fileName
                    )
                )
        ) {

            output.writeObject(
                new ArrayList<>(
                    tournaments
                )
            );

        } catch (IOException e) {

            System.out.println(
                "Could not save tournament data: "
                + e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tournament> load() {

        File file =
            new File(
                fileName
            );

        if (!file.exists()) {

            return new ArrayList<>();
        }

        try (
            ObjectInputStream input =
                new ObjectInputStream(
                    new FileInputStream(
                        fileName
                    )
                )
        ) {

            return (
                List<Tournament>
            ) input.readObject();

        } catch (
            IOException
            | ClassNotFoundException e
        ) {

            System.out.println(
                "Could not load tournament data: "
                + e.getMessage()
            );

            return new ArrayList<>();
        }
    }
}

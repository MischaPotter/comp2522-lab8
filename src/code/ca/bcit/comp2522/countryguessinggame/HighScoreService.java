package ca.bcit.comp2522.countryguessinggame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

/**
 * Handles reading and writing the highest score.
 *
 * @author Mischa Potter
 * @version 1.0
 */
class HighScoreService
{
    public static        int  HIGHEST_SCORE_NUM;
    public static final  int  NO_HIGHEST_SCORE_NUM = -1;
    private static final Path scorePath;

    static
    {
        scorePath = Paths.get("src", "res", "data", "data.txt");
        try
        {
            getHighestScoreFromFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
    }

    /*
     * Reads the highest score from data.txt and sets HIGHEST_SCORE_NUM
     * to that value. If there is no current best, the value is set to
     * NO_HIGHEST_SCORE_NUM (representing -1) to signal that there was no
     * previously recorded highest score.
     *
     * @throws IOException if a parent directory is not found
     */
    private static void getHighestScoreFromFile() throws IOException
    {
        if (Files.notExists(scorePath))
        {
            try
            {
                Files.createFile(scorePath);
                Files.writeString(scorePath, "COUNTRY=0", StandardOpenOption.WRITE);
            }
            catch (final IOException e)
            {
                e.printStackTrace();
                // throw new RuntimeException(e);
            }
            finally
            {
                HIGHEST_SCORE_NUM = NO_HIGHEST_SCORE_NUM;
            }
        }
        else
        {
            try
            {
                final List<String> lines;
                final Scanner lineScanner;

                lines       = Files.readAllLines(scorePath);
                lineScanner = new Scanner(lines.getFirst());
                lineScanner.useDelimiter("=");
                lineScanner.next();
                HIGHEST_SCORE_NUM = lineScanner.nextInt();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /*
     * Writes to data.txt with the new highest score.
     *
     * @param newHighestScore the new highest score for CountryGuesser
     * @throws IOException if an error occurs trying to write to data.txt
     */
    public static void updateHighestScore(final int newHighestScore) throws IOException
    {
        Files.writeString(scorePath, "COUNTRY=" + newHighestScore, StandardOpenOption.CREATE);
        HIGHEST_SCORE_NUM = newHighestScore;
    }
}

package ca.bcit.comp2522.countryguessinggame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles creating and writing to log files for the CountryGuessing game.
 *
 * @author Mischa Potter
 * @version 1.0
 */
class LoggerService
{
    private static      Path logPath;
    public static final int  LOG_OPTION_EMPTY_GUESS         = 1;
    public static final int  LOG_OPTION_QUIT                = 2;
    public static final int  LOG_OPTION_CORRECT             = 3;
    public static final int  LOG_OPTION_WRONG_LENGTH        = 4;
    public static final int  LOG_OPTION_LETTERS_CORRECT_POS = 5;

    /**
     * Creates a new log file with the name formatted as YYYY-MM-DD-mm-ss_COUNTRY.txt
     *
     * @param country the name of the country to put in the log file's name
     * @throws IOException if the path to the log doesn't exist
     */
    public static void createLogFile(final String country) throws IOException
    {
        final Path dirPath;
        dirPath = Paths.get("src", "res", "data", "logs");

        if (Files.notExists(dirPath))
        {
            Files.createDirectories(dirPath);
        }

        final LocalDateTime currentDate;
        final StringBuilder logName;
        final DateTimeFormatter dateFormatter;
        final String formattedDate;

        currentDate   = LocalDateTime.now();
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_mm-ss");
        formattedDate = currentDate.format(dateFormatter);
        logName       = new StringBuilder();

        logName.append(formattedDate);
        logName.append("_");
        logName.append(country.toUpperCase());
        logName.append(".txt");

        logPath = Paths.get("src", "res", "data", "logs", logName.toString());

        if (Files.notExists(logPath))
        {
            try
            {
                Files.createFile(logPath);
            }
            catch (final IOException e)
            {
                Path logPath;
                logPath = Paths.get("res", "data", "logs");
                Files.createDirectories(logPath);
                Files.createFile(logPath);
            }
        }
    }

    /**
     * Appends a line to the end of the current log file.
     *
     * @param userGuess         the country the user guessed
     * @param logLineOption     the type of outcome option
     * @param numAttempts       the number of attempts the user took
     * @param numCorrectLetters the number of correct letters if the word is the correct length
     * @throws IOException if a parent directory is missing when trying to get the file path
     */
    public static void appendGuess(final String userGuess,
                                   final int logLineOption,
                                   final int numAttempts,
                                   final int numCorrectLetters) throws IOException
    {
        final StringBuilder line;
        line = new StringBuilder();

        line.append(LocalDateTime.now());
        line.append(". Guess: ");
        line.append(userGuess);
        line.append(". Outcome: ");
        switch (logLineOption)
        {
            case LOG_OPTION_EMPTY_GUESS -> line.append("empty guess, user prompted again\n");
            case LOG_OPTION_QUIT -> line.append("user quit, game ended\n");
            case LOG_OPTION_CORRECT -> line.append("user got it CORRECT in ").append(numAttempts).append("\n");
            case LOG_OPTION_WRONG_LENGTH -> line.append("wrong_length\n");
            case LOG_OPTION_LETTERS_CORRECT_POS -> line.append("matches=").append(numCorrectLetters).append("\n");
        }

        Files.writeString(logPath, line.toString(), StandardOpenOption.APPEND);
    }
}

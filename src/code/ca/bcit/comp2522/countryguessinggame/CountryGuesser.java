package ca.bcit.comp2522.countryguessinggame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Models a country guessing game.
 *
 * @author Mischa Potter
 * @author Ben Lazarro
 * @version 1.0
 */
class CountryGuesser
{
    private static final String EXIT_WORD            = "QUIT";
    private static final int    ZERO_CORRECT_LETTERS = 0;

    /**
     * Runs the CountryGuesser game. Rule and loop logic is written in main.
     *
     * @param args unused
     */
    public static void main(final String[] args)
    {
        try
        {
            final Scanner inputScanner;
            final String country;
            final int countryLength;
            int numAttempts = 0;
            String input;

            country = WordList.getCountry();

            if (country.equals(WordList.ERROR_GETTING_COUNTRY_MESSAGE))
            {
                System.out.println("Error getting country. Game cannot proceed.");
                return;
            }

            countryLength = country.length();
            inputScanner  = new Scanner(System.in, StandardCharsets.UTF_8);

            System.out.println("LUCKY VAULT - COUNTRY MODE. Type QUIT to exit.");
            System.out.println("Secret word length: " + countryLength);

            LoggerService.createLogFile(country);

            if (HighScoreService.HIGHEST_SCORE_NUM == HighScoreService.NO_HIGHEST_SCORE_NUM)
            {
                System.out.println("Current best: -");
            }
            else
            {
                System.out.printf("Current best: %d attempts\n", HighScoreService.HIGHEST_SCORE_NUM);
            }

            while (true)
            {
                System.out.println("Your guess: ");

                input = inputScanner.nextLine();
                numAttempts++;

                if (input.isBlank())
                {
                    System.out.println("Empty guess. Try again.");
                    LoggerService.appendGuess(input, LoggerService.LOG_OPTION_EMPTY_GUESS, numAttempts,
                                              ZERO_CORRECT_LETTERS);
                }
                else if (input.equalsIgnoreCase(EXIT_WORD))
                {
                    System.out.println("Bye!");
                    LoggerService.appendGuess(input, LoggerService.LOG_OPTION_QUIT, numAttempts,
                                              ZERO_CORRECT_LETTERS);
                    inputScanner.close();
                    break;
                }
                else if (input.equalsIgnoreCase(country))
                {
                    System.out.printf("Correct in %d attempts! Word was: %s\n", numAttempts, country);
                    if (numAttempts < HighScoreService.HIGHEST_SCORE_NUM)
                    {
                        System.out.println("NEW BEST for COUNTRY mode!");
                        HighScoreService.updateHighestScore(numAttempts);
                    }
                    LoggerService.appendGuess(input, LoggerService.LOG_OPTION_CORRECT, numAttempts,
                                              ZERO_CORRECT_LETTERS);
                    inputScanner.close();
                    break;
                }
                else if (input.length() != countryLength)
                {
                    System.out.printf("Wrong length %d. Need %d.\n", input.length(), countryLength);
                    LoggerService.appendGuess(input, LoggerService.LOG_OPTION_WRONG_LENGTH, numAttempts,
                                              ZERO_CORRECT_LETTERS);
                }
                else
                {
                    final int numLettersInCorrectPosition;
                    numLettersInCorrectPosition = lettersInCorrectPosition(country, input);
                    System.out.printf("Not it. %d letter(s) correct (right position).\n", numLettersInCorrectPosition);
                    LoggerService.appendGuess(input, LoggerService.LOG_OPTION_LETTERS_CORRECT_POS, numAttempts,
                                              numLettersInCorrectPosition);
                }
            }
        }
        catch (final IOException e)
        {
            System.out.println("Error getting file: " + e.getMessage());
        }
    }

    /*
     * Compares two strings and checks how many letters have the same indexes.
     * For example, "china" and "chile" would return 3.
     *
     * @param computerCountry the country picked by the computer
     * @param userCountry the country inputted by the user
     * @return the number of letters that are also correct
     */
    private static int lettersInCorrectPosition(final String computerCountry,
                                                final String userCountry)
    {
        int numLettersCorrect;

        numLettersCorrect = 0;

        for (int i = 0; i < computerCountry.length(); i++)
        {
            if (computerCountry.toLowerCase().charAt(i) == userCountry.toLowerCase().charAt(i))
            {
                numLettersCorrect++;
            }
        }

        return numLettersCorrect;
    }
}

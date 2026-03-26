package ca.bcit.comp2522.countryguessinggame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Contains one method called getCountry that loads countries (data from
 * countries.txt) and randomly selects one of them. Helper class for the
 * CountryGuesser game.
 *
 * @author Mischa Potter
 * @author Ben Lazarro
 * @version 1.0
 */
class WordList
{
    public static final String ERROR_GETTING_COUNTRY_MESSAGE = "empty countries list";

    private static final int MIN_INDEX_NUM = 0;

    /**
     * Reads a hardcoded list of countries (countries.txt) and randomly picks
     * a country from the list.
     *
     * @return a country from the list
     */
    public static String getCountry()
    {
        final Path directoriesPath;
        final Path countriesPath;

        directoriesPath = Paths.get("src", "res", "data", "logs");
        countriesPath   = Paths.get("src", "res", "data", "countries.txt");

        try
        {
            if (Files.notExists(directoriesPath))
            {
                Files.createDirectories(directoriesPath);
            }

            if (Files.notExists(countriesPath))
            {
                Files.createFile(countriesPath);
            }

            final List<String> countries;
            final Random countryIndexNumGenerator;
            final int randomCountryIndexNum;

            countries = Files.readAllLines(countriesPath);

            if (countries.getFirst().isBlank())
            {
                System.out.println("Error retrieving country. Abort game.");
                return ERROR_GETTING_COUNTRY_MESSAGE;
            }

            countryIndexNumGenerator = new Random();
            randomCountryIndexNum    = countryIndexNumGenerator.nextInt(MIN_INDEX_NUM, countries.size());

            //System.out.println(countries.get(randomCountryIndexNum));
            // line above prints out the secret country before we even get to guess
            return countries.get(randomCountryIndexNum);
        }
        catch (final IOException e)
        {
            System.out.println("Error with file I/O: " + e.getMessage());
        }
        return ERROR_GETTING_COUNTRY_MESSAGE;
    }
}

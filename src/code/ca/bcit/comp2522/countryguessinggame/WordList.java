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
 * @version 1.0
 */
class WordList
{
    private static final int MIN_INDEX_NUM = 0;

    /**
     * Reads a hardcoded list of countries (countries.txt) and randomly picks
     * a country from the list.
     *
     * @return a country from the list
     * @throws IOException if the
     */
    public static String getCountry() throws IOException
    {
        final Path directoriesPath;
        final Path countriesPath;

        directoriesPath = Paths.get("src", "res", "data", "logs");
        countriesPath   = Paths.get("src", "res", "data", "countries.txt");

        if (Files.notExists(directoriesPath))
        {
            Files.createDirectories(directoriesPath);
        }

        if (Files.notExists(countriesPath))
        {
            Files.createFile(countriesPath);
            // what to do if no country file is found?
        }

        try
        {
            final List<String> countries;
            final Random countryIndexNumGenerator;
            final int randomCountryIndexNum;

            countries                = Files.readAllLines(countriesPath);
            countryIndexNumGenerator = new Random();
            randomCountryIndexNum    = countryIndexNumGenerator.nextInt(MIN_INDEX_NUM, countries.size());
            System.out.println(countries.get(randomCountryIndexNum));
            return countries.get(randomCountryIndexNum);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        throw new IOException("Error getting country");
    }
}

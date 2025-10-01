package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the services of: <br/>
 * - converting language codes to their names <br/>
 * - converting language names to their codes
 */
public class LanguageCodeConverter {

    private final Map<String, String> languageCodeToLanguage = new HashMap<>();

    public Map<String, String> getLanguageCodeToLanguage() {
        return languageCodeToLanguage;
    }
    private final Map<String, String> languageToLanguageCode = new HashMap<>();

    /**
     * Default constructor that loads the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor that allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resources file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split("\\s");

                if (split.length >= 2) {

                    String code = split[split.length - 1];
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < split.length - 1; i++) {
                        builder.append(split[i]);
                    }

                    String language = builder.toString();

                    languageCodeToLanguage.put(code, language);
                    languageToLanguageCode.put(language, code);
                }

            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return the name of the language for the given language code.
     * @param code the 2-letter language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TODO Task A: update this code to use the correct instance variable to return the appropriate value
        if (languageCodeToLanguage.containsKey(code)) code = languageCodeToLanguage.get(code);
        else code = null;
        return code;
    }

    /**
     * Return the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // TODO Task A: update this code to use the correct instance variable to return the appropriate value
        if (languageToLanguageCode.containsKey(language)) language = languageToLanguageCode.get(language);
        else language = null;
        return language;
    }

    /**
     * Return how many languages are included in this language code converter.
     * @return how many languages are included in this language code converter.
     */
    public int getNumLanguages() {
        return languageCodeToLanguage.size();
    }
}

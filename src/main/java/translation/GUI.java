package translation;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// create a
public class GUI {
    public static void main(String[] args) {

        // for later use:
        LanguageCodeConverter languageconverter = new LanguageCodeConverter();
        CountryCodeConverter countryconverter = new CountryCodeConverter();


        //  --- selection menu for languages: ComboBox ---
        List<String> languages = new ArrayList<>(); //create ArrayList for countries
        try {
            // read file content into string
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/sample.json")));
            // parse as a JSON array
            JSONArray arr = new JSONArray(content);
            JSONObject obj = arr.getJSONObject(0);
            // get all keys
            Iterator<String> keys = obj.keys();
            // loop through each key name
            while (keys.hasNext()) {
                String key = keys.next();
                // skip first three
                if (key.equals("id") || key.equals("alpha2") || key.equals("alpha3")) {
                    continue;
                }
                // get language and add to list
                String translated = languageconverter.fromLanguageCode(key);
                if (translated.charAt((translated.length()) - 1) == ',') {
                    languages.add(translated.substring(0, translated.length() - 1));
                } else {
                    languages.add(translated);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // create ComboBox for choosing languages
        JComboBox<String> languageComboBox = new JComboBox<>(languages.toArray(new String[0]));
        languageComboBox.setSelectedItem("German");
        // add items into this box


        // --- middle translation panel ---
        JPanel middlePanel = new JPanel();
        JLabel translationLabel = new JLabel("Translation:");
        middlePanel.add(translationLabel);
        JLabel resultLabel = new JLabel(" ");
        middlePanel.add(resultLabel);


        // --- JList: a list of all countries ---
        List<String> countries = new ArrayList<>(); //create ArrayList for countries
        try {
            // Read file content into a String
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/sample.json")));

            // Parse as a JSON array
            JSONArray jsonArray = new JSONArray(content);

            // Loop through each object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                // Get the country and add it to ArrayList
                String countrycode = obj.getString("alpha3");
                String country = countryconverter.fromCountryCode(countrycode);
                countries.add(country);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // create JList with countries
        JList<String> countriesJList = new JList<>(countries.toArray(new String[0]));
        countriesJList.setVisibleRowCount(8);          // optional: controls initial height
        JScrollPane countriesScroll = new JScrollPane(countriesJList);


        // --- main panel ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(languageComboBox);
        mainPanel.add(middlePanel);
        mainPanel.add(countriesScroll); // add the scroll pane, not the JList

        JFrame frame = new JFrame("Country Name Translator");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        // --- Unified update function ---
        Runnable update = () -> {
            String language = (String) languageComboBox.getSelectedItem();
            String country = countriesJList.getSelectedValue();

            if (language != null && country != null) {
                // get index of country
                int index = countries.indexOf(country);
                // get language code
                String languagecode = languageconverter.fromLanguage(language);
                // get translated:
                try {
                    // Read file content into a String
                    String content = new String(Files.readAllBytes(Paths.get("src/main/resources/sample.json")));

                    // Parse as a JSON array
                    JSONArray jsonArray = new JSONArray(content);

                    resultLabel.setText(jsonArray.getJSONObject(index).getString(languagecode));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        // --- Attach listeners ---
        languageComboBox.addActionListener(e -> update.run());
        countriesJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) update.run();
        });
    }
}







        /**
         * Overloaded constructor that allows us to specify the filename to load the country code data from.
         * @param filename the name of the file in the resources folder to load the data from
         * @throws RuntimeException if the resources file can't be loaded properly
         */







// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
/* public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
           // JTextField languageField = new JTextField(10);
            //languagePanel.add(new JLabel("Language:"));
            //languagePanel.add(languageField);
            JComboBox<String> languageComboBox = new JComboBox<>();
            LanguageCodeConverter converter = new LanguageCodeConverter();
            for (String languageCode : converter.getLanguageCodeToLanguage().keySet()) {
                languageComboBox.addItem(converter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            String language = languageComboBox.toString();
            String country = countryField.getText();
            Translator translator = new CanadaTranslator();

            String result = translator.translate(country, language);
            if (result == null) {
                result = "no translation found!";
            }
            resultLabel.setText(result);
            resultPanel.add(resultLabelText);
            resultPanel.add(resultLabel);

//
//            // adding listener for when the user clicks the submit button
//            submit.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String language = languageField.getText();
//                    String country = countryField.getText();
//
//                    // for now, just using our simple translator, but
//                    // we'll need to use the real JSON version later.
//                    Translator translator = new CanadaTranslator();
//
//                    String result = translator.translate(country, language);
//                    if (result == null) {
//                        result = "no translation found!";
//                    }
//                    resultLabel.setText(result);
//
//                }
//
//            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            //mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
           // mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
*/
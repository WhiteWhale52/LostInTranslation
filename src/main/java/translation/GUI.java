package translation;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static jdk.internal.org.jline.utils.InfoCmp.Capability.lines;

// create a
public class Gui_new {
    public static void main(String[] args) throws URISyntaxException, IOException {

        JComboBox<String> languageComboBox = new JComboBox<>();
        // add items into this box

        JPanel middlePanel = new JPanel();
        JLabel translationLabel = new JLabel("Translation:");
        middlePanel.add(translationLabel);
        JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
        middlePanel.add(resultLabel);

        JList<String> countriesJList = new JList<>();
        // add countries to this list


        // method to get all the country names from the country-codes file
        // this is for the JList
        public LinkedList<String> CountryCodeConverter() {

            List<String> countries = new LinkedList<>();
            try {
                List<String> lines = Files.readAllLines(Paths.get(Gui_new.class
                        .getClassLoader().getResource("country-codes.txt").toURI()));
            }
            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");
                countryCodeToCountry.put(parts[2], parts[0]);
                countryToCountryCode.put(parts[0], parts[2]);
            cities.add("Toronto");
            cities.add("Vancouver");
            System.out.println(cities.get(0));
        }
            this("country-codes.txt");






        }

        /**
         * Overloaded constructor that allows us to specify the filename to load the country code data from.
         * @param filename the name of the file in the resources folder to load the data from
         * @throws RuntimeException if the resources file can't be loaded properly
         */
    public CountryCodeConverter(String filename) {






        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(languageComboBox);
        mainPanel.add(translationLabel);
        mainPanel.add(countriesJList);

        JFrame frame = new JFrame("Country Name Translator");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}






// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageField);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageField.getText();
                    String country = countryField.getText();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new CanadaTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}

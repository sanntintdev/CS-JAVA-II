import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherApp extends JFrame {

    private JTextField locationInput;
    private JButton searchButton;
    private JLabel temperatureLabel, humidityLabel, windSpeedLabel, conditionLabel;
    private JRadioButton celsiusButton, fahrenheitButton;
    private WeatherApiService apiService;
    private JPanel mainPanel;
    private JTextArea historyArea;
    private List searchHistory;

    public WeatherApp() {
        setTitle("Weather Information App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        apiService = new WeatherApiService();
        searchHistory = new ArrayList();

        mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(getDynamicBackground());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Create panels
        JPanel inputPanel = createInputPanel();
        JPanel infoPanel = createInfoPanel();
        JPanel unitPanel = createUnitPanel();
        JPanel historyPanel = createHistoryPanel();

        // Add panels to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(unitPanel, BorderLayout.SOUTH);

        // Add main panel and history panel to frame
        add(mainPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Start background update timer
        Timer timer = new Timer(60000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.repaint();
            }
        });
        timer.start();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel locationLabel = new JLabel("Location:");
        locationInput = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchWeather();
            }
        });

        panel.add(locationLabel, BorderLayout.WEST);
        panel.add(locationInput, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        temperatureLabel = new JLabel("Temperature: ");
        humidityLabel = new JLabel("Humidity: ");
        windSpeedLabel = new JLabel("Wind Speed: ");
        conditionLabel = new JLabel("Condition: ");

        panel.add(temperatureLabel);
        panel.add(humidityLabel);
        panel.add(windSpeedLabel);
        panel.add(conditionLabel);

        return panel;
    }

    private JPanel createUnitPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ButtonGroup unitToggle = new ButtonGroup();
        celsiusButton = new JRadioButton("Celsius", true);
        fahrenheitButton = new JRadioButton("Fahrenheit");
        unitToggle.add(celsiusButton);
        unitToggle.add(fahrenheitButton);

        panel.add(celsiusButton);
        panel.add(fahrenheitButton);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Search History"));

        historyArea = new JTextArea(10, 20);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void searchWeather() {
        String location = locationInput.getText();
        if (location.isEmpty()) {
            showError("Please enter a location.");
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                try {
                    Map weatherData = apiService.getWeatherData(location);
                    updateWeatherInfo(weatherData);
                    addToHistory(location);
                } catch (Exception e) {
                    final String errorMessage = e.getMessage();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            showError("Error fetching weather data: " + errorMessage);
                        }
                    });
                }
            }
        }).start();
    }

    private void updateWeatherInfo(final Map weatherData) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    boolean isCelsius = celsiusButton.isSelected();
                    temperatureLabel.setText(String.format("Temperature: %s°%s",
                            isCelsius ? weatherData.get("temp_c") : weatherData.get("temp_f"),
                            isCelsius ? "C" : "F"));
                    humidityLabel.setText("Humidity: " + weatherData.get("humidity") + "%");
                    windSpeedLabel.setText("Wind Speed: " + weatherData.get("wind_kph") + " km/h");
                    conditionLabel.setText("Condition: " + weatherData.get("condition"));
                } catch (Exception e) {
                    showError("Error parsing weather data");
                }
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void addToHistory(String location) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        String historyEntry = timestamp + " - " + location;
        searchHistory.add(0, historyEntry); // Add to the beginning of the list
        if (searchHistory.size() > 10) {
            searchHistory.remove(searchHistory.size() - 1); // Remove oldest entry if more than 10
        }
        updateHistoryDisplay();
    }

    private void updateHistoryDisplay() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                historyArea.setText(""); // Clear existing text
                for (Iterator it = searchHistory.iterator(); it.hasNext();) {
                    historyArea.append((String) it.next() + "\n");
                }
            }
        });
    }

    private Color getDynamicBackground() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return new Color(135, 206, 235); // Light blue for morning (5 AM to 11:59 AM)
        } else if (hour >= 12 && hour < 17) {
            return new Color(255, 255, 224); // Light yellow for afternoon (12 PM to 4:59 PM)
        } else if (hour >= 17 && hour < 20) {
            return new Color(255, 165, 0); // Orange for evening (5 PM to 7:59 PM)
        } else {
            return new Color(25, 25, 112); // Dark blue for night (8 PM to 4:59 AM)
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WeatherApp();
            }
        });
    }
}
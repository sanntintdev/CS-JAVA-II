import java.util.*;

public class TextAnalyser {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            String text = getUserInput();
            displayCharacterCount(text);
            displayWordCount(text);
            displayMostCommonCharacter(text);
            displayUniqueWordCount(text);
            System.out.println("__________________________");
            displayCharacterFrequency(text);
            System.out.println("__________________________");
            displayWordFrequency(text);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String getUserInput() {
        String input;
        // use the loop to ensure the input is not empty
        do {
            System.out.println("Please enter a paragraph or lengthy text (cannot be empty):");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static void displayCharacterCount(String text) {
        System.out.println("\nTotal number of characters: " + text.length());
    }

    private static void displayWordCount(String text) {
        String[] words = text.split("\\s+");
        System.out.println("Total number of words: " + words.length);
    }

    private static void displayMostCommonCharacter(String text) {
        try {
            Map<Character, Integer> charFrequency = new HashMap<>();
            for (char c : text.toLowerCase().toCharArray()) {
                if (c != ' ') { // Exclude space character
                    // use Map.getOrDefault() to avoid null checks
                    charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
                }
            }

            if (charFrequency.isEmpty()) {
                System.out.println("No non-space characters found in the text.");
            } else {

                char mostCommonChar = Collections.max(charFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
                System.out.println("Most common non-space character: '" + mostCommonChar +
                        "' (appears " + charFrequency.get(mostCommonChar) + " times)");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayCharacterFrequency(String text) {
        try {
            char charToCheck;
            // use the loop to ensure the input is exactly one character
            while (true) {
                System.out.print("\nEnter a single character to check its frequency: ");
                String input = scanner.nextLine().trim();
                if (input.length() == 1) {
                    charToCheck = input.toLowerCase().charAt(0);
                    break;
                } else {
                    System.out.println("Please enter exactly one character.");
                }
            }
            long charFreq = text.toLowerCase().chars().filter(ch -> ch == charToCheck).count();
            System.out.println("The character '" + charToCheck + "' appears " + charFreq + " times in the text");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayWordFrequency(String text) {
        try {
            String wordToCheck;

            do {
                System.out.print("\nEnter a word to check its frequency (cannot be empty): ");
                final String finalWordToCheck = scanner.nextLine().trim();
                if (finalWordToCheck.isEmpty()) {
                    System.out.println("Word cannot be empty. Please try again.");
                } else {
                    wordToCheck = finalWordToCheck;
                    break;
                }
            } while (true);

            String[] words = text.split("\\s+");
            long wordFreq = Arrays.stream(words).filter(word -> word.equals(wordToCheck)).count();
            System.out.println("The word '" + wordToCheck + "' appears " + wordFreq + " times in the text");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayUniqueWordCount(String text) {
        try {
            String[] words = text.toLowerCase().split("\\s+");
            long uniqueWords = Arrays.stream(words).distinct().count();
            System.out.println("\nNumber of unique words: " + uniqueWords);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
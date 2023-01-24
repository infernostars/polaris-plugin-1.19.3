package dev.infernity.polarisplugin.polarisplugin.utilities;

public class stringUtilities {
    /**
     * Turns a string into a string cased like natural language.
     * @param s The string to be cased.
     * @return A string which is cased, ex. "Hello! How are you doing today?"
     * @since 0.1
     */
    public static String toDisplayCase(String s) {

        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }
}

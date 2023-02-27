package dev.infernity.polarisplugin.polarisplugin.utilities

object stringUtilities {
    /**
     * Turns a string into a string cased like natural language.
     * @param s The string to be cased.
     * @return A string which is cased, ex. "Hello! How are you doing today?"
     * @since 0.1
     */
    fun toDisplayCase(s: String): String {
        val ACTIONABLE_DELIMITERS = " '-/" // these cause the character following
        // to be capitalized
        val sb = StringBuilder()
        var capNext = true
        for (c in s.toCharArray()) {
            var character = if (capNext) c.uppercaseChar() else c.lowercaseChar()
            sb.append(character)
            capNext = ACTIONABLE_DELIMITERS.indexOf(c.code.toChar()) >= 0 // explicit cast not needed
        }
        return sb.toString()
    }
}
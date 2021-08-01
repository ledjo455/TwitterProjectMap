package application.logic.filters;

import twitter4j.Status;

import java.util.List;

/**
 * An interface that captures what it means to be a filter.
 */
public interface Filter {
    /**
     * Returns true if the filter matches the given tweet
     * @param s     the tweet to check
     * @return      whether or not the tweet matches this filter
     */
    boolean matches(Status s);

    /**
     * This static method parses the given input string and creates a Filter
     * that represents the application.data.query. If the input doesn't match the rules for
     * a filter that contains "and", "or", and/or "not" operators, it is treated
     * as a literal string to match.
     * @param queryString   the string containing the filter expression
     * @return              a Filter that represents that filter condition
     */
    static Filter parse(String queryString) {
        try {
            return new ParserContext(queryString).parse();
        } catch (ParserSyntaxError parserSyntaxError) {
            return new BasicFilter(queryString);
        }
    }

    /**
     * Get all the terms (strings in basic application.logic.filters) used in this filter.
     * When we application.data.query the Twitter API, we must indicate all the terms we are
     * interested in, and this allows us to collect them up for each active application.data.query.
     * @return      a list of terms mentioned in this filter
     */
    List<String> terms();
}

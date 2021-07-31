package application.data.twitter;
/**
 * TwitterAPI Class is used for initializing keys from Twitter API Developer
 */
public class TwitterAPI {

     final static String authConsumerKey;
     final static String oAuthConsumerSecret;
     final static String oAuthAccessToken;
     final static String OAuthAccessTokenSecret;

     static {
         authConsumerKey = "tywcAjB5rDzc7FX5f6ygcbCZt";
         oAuthConsumerSecret = "jrhbr4s8e8PdUf9dpMppnnJwbaxkBRVqFu8CpCUMUuWfBJUPtb";
         oAuthAccessToken ="1414972930317033476-uEpWKmQ2YyelsCF0ymRqoJKequZx1u";
         OAuthAccessTokenSecret = "jgpjHke5AvPX24KXSgKkrxIhzw3r0C0fOkv288kz0kmVU";
     }

    public String getAuthConsumerKey() { return authConsumerKey; }

    public String getOAuthConsumerSecret() { return oAuthConsumerSecret; }

    public String getOAuthAccessToken() { return oAuthAccessToken; }

    public String getOAuthAccessTokenSecret() { return OAuthAccessTokenSecret; }


}

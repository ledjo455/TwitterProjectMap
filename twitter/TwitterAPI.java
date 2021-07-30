package twitter;
/**
 * TwitterAPI Class is used for initializing keys from Twitter API Developer
 */
public class TwitterAPI {

     final String authConsumerKey;
     final String oAuthConsumerSecret;
     final String oAuthAccessToken;
     final String OAuthAccessTokenSecret;

    public TwitterAPI() {
        this.authConsumerKey = "VBuOwzrzuWfKcPkN9eYyUP9vK";
        this.oAuthConsumerSecret = "gGIgqg86Y9qGc64UtlSiVkKDzbyBorEW2Cn6pC7LcjVwZKVLXp";
        this.oAuthAccessToken = "70967537-2l7GSGWUTRhmKBDmZA6KhzsHM7mCY3M4takahapMG";
        this.OAuthAccessTokenSecret = "9l0hpQNP0tbdSYrXwUFa2w5HYJRsYv2fjUO9ZIvbpYwet";
    }

    public String getAuthConsumerKey() { return authConsumerKey; }

    public String getOAuthConsumerSecret() { return oAuthConsumerSecret; }

    public String getOAuthAccessToken() { return oAuthAccessToken; }

    public String getOAuthAccessTokenSecret() { return OAuthAccessTokenSecret; }


}

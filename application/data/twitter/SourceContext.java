package application.data.twitter;
/**
 * SourceContext is a class that implements the Strategy Software Design
 */
public class SourceContext {

    LiveTwitterSource liveTwitter;
    PlaybackTwitterSource playbackTwitter;
    TwitterAPI twitterAPI;

    public SourceContext() {
        this.liveTwitter = getLiveTwitter();
        this.playbackTwitter = getPlayBackTwitter();
        this.twitterAPI = getKeys();
    }


    public LiveTwitterSource getLiveTwitter() {
        return new LiveTwitterSource();
    }

    public TwitterAPI getKeys(){
        return new TwitterAPI();
    }

    // To use the recorded application.data.twitter stream, use the following line
    // The number passed to the constructor is a speedup value:
    //  1.0 - play back at the recorded speed
    //  2.0 - play back twice as fast
    public PlaybackTwitterSource getPlayBackTwitter(){
        return new PlaybackTwitterSource(2);
    }

}

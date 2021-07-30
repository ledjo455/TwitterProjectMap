package testing.twitterTest;

import org.junit.jupiter.api.Test;
import twitter.ObserverImplementation;
import twitter.PlaybackTwitterSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the basic functionality of the TwitterSource
 */
public class TestPlaybackTwitterSource {

    @Test
    public void testSetup() {
        PlaybackTwitterSource source = new PlaybackTwitterSource(1.0);
        ObserverImplementation observerImpl = new ObserverImplementation();
        System.out.printf("-------------------Testing Playback Twitter Source ------\n");
        // TODO: Once your TwitterSource class implements Observable, you must add the TestObserver as an observer to it here
        source.addObserver(observerImpl); // Using Observable I added the observer
        source.setFilterTerms(set("food"));
        pause(3 * 1000);
        assertTrue(observerImpl.getNTweets() > 0, "Expected getNTweets() to be > 0, was " + observerImpl.getNTweets());
        assertTrue(observerImpl.getNTweets() <= 10, "Expected getNTweets() to be <= 10, was " + observerImpl.getNTweets());
        int firstBunch = observerImpl.getNTweets();
        System.out.println("Now adding 'the'");
        source.setFilterTerms(set("food", "the"));
        pause(3 * 1000);
        assertTrue(observerImpl.getNTweets() > 0, "Expected getNTweets() to be > 0, was " + observerImpl.getNTweets());
        assertTrue(observerImpl.getNTweets() > firstBunch, "Expected getNTweets() to be < firstBunch (" + firstBunch + "), was " + observerImpl.getNTweets());
        assertTrue(observerImpl.getNTweets() <= 10, "Expected getNTweets() to be <= 10, was " + observerImpl.getNTweets());
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private <E> Set<E> set(E ... p) {
        Set<E> ans = new HashSet<>();
        for (E a : p) {
            ans.add(a);
        }
        return ans;
    }

}

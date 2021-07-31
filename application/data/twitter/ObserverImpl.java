package application.data.twitter;

import twitter4j.Status;

import java.util.Observable;
import java.util.Observer;

/**
 * ObserverImplementation Class is used for testing Twitter package implementing Observer design
 */

public class ObserverImpl implements Observer{

    private int numberOfTweets = 0;

    public int getNTweets() { return numberOfTweets; }


    @Override
        public void update(Observable o, Object arg) {
            Status s = (Status) arg;
            numberOfTweets++;
        }

}

package testing.filtersTest;

import application.logic.filters.*;
import org.junit.jupiter.api.Test;
import twitter4j.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestFilters {

    @Test
    public void testFiltersInfo(){
        System.out.printf("-------------------Testing Filters ----------------------\n");
    }
    @Test
    public void testBasic() {
        Filter f = new BasicFilter("fred");
        assertTrue(f.matches(makeStatus("Fred Flintstone")));
        assertTrue(f.matches(makeStatus("fred Flintstone")));
        assertFalse(f.matches(makeStatus("Red Skelton")));
        assertFalse(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testNot() {
        Filter f = new NotFilter(new BasicFilter("fred"));
        assertFalse(f.matches(makeStatus("Fred Flintstone")));
        assertFalse(f.matches(makeStatus("fred Flintstone")));
        assertTrue(f.matches(makeStatus("Red Skelton")));
        assertTrue(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testOR(){
        Filter f = new OrFilter(new BasicFilter("velvet"), new BasicFilter("pie"));
        assertTrue(f.matches(makeStatus("velvet pie")));
        assertTrue(f.matches(makeStatus("kiwi pie")));
        assertTrue(f.matches(makeStatus("Velvet iceroll")));
        assertFalse(f.matches(makeStatus("elvet icecream")));
        assertFalse(f.matches(makeStatus("dreams holly")));
        assertFalse(f.matches(makeStatus("dreamy cace")));

    }
    @Test
    public void testAND(){
        Filter f = new AndFilter(new BasicFilter("ipad"), new BasicFilter("air"));
        assertTrue(f.matches(makeStatus("ipad air")));
        assertTrue(f.matches(makeStatus("air ipad")));
        assertTrue(f.matches(makeStatus("Ipad air")));
        assertFalse(f.matches(makeStatus("iphone air")));
        assertFalse(f.matches(makeStatus("laptop flow")));
        assertFalse(f.matches(makeStatus("pad ria")));

    }


    private Status makeStatus(String text) {
        return new Status() {
            @Override
            public Date getCreatedAt() {
                return null;
            }

            @Override
            public long getId() {
                return 0;
            }

            @Override
            public String getText() {
                return text;
            }

            @Override
            public String getSource() {
                return null;
            }

            @Override
            public boolean isTruncated() {
                return false;
            }

            @Override
            public long getInReplyToStatusId() {
                return 0;
            }

            @Override
            public long getInReplyToUserId() {
                return 0;
            }

            @Override
            public String getInReplyToScreenName() {
                return null;
            }

            @Override
            public GeoLocation getGeoLocation() {
                return null;
            }

            @Override
            public Place getPlace() {
                return null;
            }

            @Override
            public boolean isFavorited() {
                return false;
            }

            @Override
            public boolean isRetweeted() {
                return false;
            }

            @Override
            public int getFavoriteCount() {
                return 0;
            }

            @Override
            public User getUser() {
                return null;
            }

            @Override
            public boolean isRetweet() {
                return false;
            }

            @Override
            public Status getRetweetedStatus() {
                return null;
            }

            @Override
            public long[] getContributors() {
                return new long[0];
            }

            @Override
            public int getRetweetCount() {
                return 0;
            }

            @Override
            public boolean isRetweetedByMe() {
                return false;
            }

            @Override
            public long getCurrentUserRetweetId() {
                return 0;
            }

            @Override
            public boolean isPossiblySensitive() {
                return false;
            }

            @Override
            public String getLang() {
                return null;
            }

            @Override
            public Scopes getScopes() {
                return null;
            }

            @Override
            public String[] getWithheldInCountries() {
                return new String[0];
            }

            @Override
            public long getQuotedStatusId() {
                return 0;
            }

            @Override
            public Status getQuotedStatus() {
                return null;
            }

            @Override
            public int compareTo(Status o) {
                return 0;
            }

            @Override
            public UserMentionEntity[] getUserMentionEntities() {
                return new UserMentionEntity[0];
            }

            @Override
            public URLEntity[] getURLEntities() {
                return new URLEntity[0];
            }

            @Override
            public HashtagEntity[] getHashtagEntities() {
                return new HashtagEntity[0];
            }

            @Override
            public MediaEntity[] getMediaEntities() {
                return new MediaEntity[0];
            }

            @Override
            public ExtendedMediaEntity[] getExtendedMediaEntities() {
                return new ExtendedMediaEntity[0];
            }

            @Override
            public SymbolEntity[] getSymbolEntities() {
                return new SymbolEntity[0];
            }

            @Override
            public RateLimitStatus getRateLimitStatus() {
                return null;
            }

            @Override
            public int getAccessLevel() {
                return 0;
            }
        };
    }
}

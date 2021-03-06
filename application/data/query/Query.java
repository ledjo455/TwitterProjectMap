package application.data.query;

import application.logic.filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import twitter4j.Status;
import application.presentation.ui.MapMarkerFancy;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import static application.logic.util.Util.imageFromURL;
import static application.logic.util.Util.statusCoordinate;


/**
 * A query over the twitter stream.
 * TODO: Task 4: you are to complete this class.
 */
public class Query implements Observer {
    // The map on which to display markers when the application.data.query matches
    private final JMapViewer map;
    // Each application.data.query has its own "layer" so they can be turned on and off all at once
    private Layer layer;
    // The color of the outside area of the marker
    private final Color color;
    // The string representing the filter for this application.data.query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;
    // The checkBox in the UI corresponding to this application.data.query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;

    private List<MapMarkerCircle> mapMarker = new ArrayList<>();

    public Color getColor() { return color; }
    public String getQueryString() { return queryString; }
    public Filter getFilter() { return filter; }
    public Layer getLayer() { return layer; }
    public JCheckBox getCheckBox() { return checkBox; }
    public void setCheckBox(JCheckBox checkBox) { this.checkBox = checkBox; }
    public void setVisible(boolean visible) { layer.setVisible(visible); }
    public boolean getVisible() { return layer.isVisible(); }

    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    /**
     * This application.data.query is no longer interesting, so terminate it and remove all traces of its existence.
     * <p>
     * TODO: Implement this method
     */
    public void terminate() {
        for (MapMarkerCircle mapMarker : mapMarker) {
            map.removeMapMarker(mapMarker);
        }
    }


    @Override
    public void update(Observable o, Object arg)  {
        Status status = (Status) arg;
        visuallyExplore(status);
    }

    /**
     * Used For displaying markers on the map with profile picture and color
     * @parameter Status is the tweet
     */
    private void visuallyExplore(Status status) {
        Coordinate coord = statusCoordinate(status);
        String tweetContent = status.getText();
        String profilePicUrl = status.getUser().getProfileImageURL();
        Image profilePicture = imageFromURL(profilePicUrl);
        String username = status.getUser().getScreenName();
        String name = status.getUser().getName();

        //MapMarkerSimple simple = new MapMarkerSimple(layer, coord); //task4
        MapMarkerFancy fancy = new MapMarkerFancy(layer, coord, color, tweetContent, profilePicture, profilePicUrl, username, name);
        if (filter.matches(status)) {
            mapMarker.add(fancy);
            map.addMapMarker(fancy);
        }
        else{
            mapMarker.remove(fancy);
            map.removeMapMarker(fancy); }
    }

}


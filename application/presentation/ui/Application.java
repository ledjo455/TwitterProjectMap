package application.presentation.ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import application.data.query.Query;
import application.data.twitter.LiveTwitterSource;
import application.data.twitter.TwitterSource;
import application.logic.util.SphericalGeometry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * The Twitter viewer application
 * Derived from a JMapViewer demo program written by Jan Peter Stotz
 */
public class Application extends JFrame {
    // The content panel, which contains the entire UI
    private final ContentPanel contentPanel;
    // The provider of the tiles for the map, we use the Bing source
    private BingAerialTileSource bing;
    // All of the active queries
    private List<Query> queries = new ArrayList<>();
    // The source of tweets, a TwitterSource, either live or playback
    private TwitterSource twitterSource;
    private  String htmlContent;
    private void initialize() {
        // To use the live application.data.twitter stream, use the following line
        twitterSource = new LiveTwitterSource();
        // To use the recorded application.data.twitter stream, use the following line
        // The number passed to the constructor is a speedup value:
        //  1.0 - play back at the recorded speed
        //  2.0 - play back twice as fast
        //twitterSource = new PlaybackTwitterSource(60.0);
        queries = new ArrayList<>();
    }

    /**
     * A new application.data.query has been entered via the User Interface
     * @param   query   The new application.data.query object
     */

    public void addQuery(Query query) {
        queries.add(query);
        Set<String> allterms = getQueryTerms();
        twitterSource.setFilterTerms(allterms);
        contentPanel.addQuery(query);
        // TODO: This is the place where you should connect the new application.data.query to the application.data.twitter source
        twitterSource.addObserver(query);
    }

    /**
     * return a list of all terms mentioned in all queries. The live application.data.twitter source uses this
     * to request matching tweets from the Twitter API.
     * @return
     */


    private Set<String> getQueryTerms() {
        Set<String> ans = new HashSet<>();
        for (Query q : queries) {
            ans.addAll(q.getFilter().terms());
        }
        return ans;
    }

    /**
     * Constructs the {@code Application}.
     */
    public Application() {
        super("Twitter content viewer");
        setSize(300, 300);
        initialize();
        bing = new BingAerialTileSource();
        contentPanel = contentPanelInit();

        mapInitialize();
        Coordinate coord = new Coordinate(0, 0);
        Timer bingTimer = new Timer();
        TimerTask bingAttributionCheck = new TimerTask() {
            @Override
            public void run() {
                // This is the best method we've found to determine when the Bing application.data has been loaded.
                // We use this to trigger zooming the map so that the entire world is visible.
                if (!bing.getAttributionText(0, coord, coord).equals("Error loading Bing attribution application.data")) {
                    map().setZoom(2);
                    bingTimer.cancel();
                }
            }
        };
        bingTimer.schedule(bingAttributionCheck, 100, 200);
       // Set up a motion listener to create a tooltip showing the tweets at the pointer position
        addMouseMapMarkerMotion();


    }

    private void mapInitialize() {
        map().setMapMarkerVisible(true);
        map().setZoomContolsVisible(true);
        map().setScrollWrapEnabled(true);
        map().setTileSource(bing);
    }

    private ContentPanel contentPanelInit() {
        final ContentPanel contentPanel;
        contentPanel = new ContentPanel(this);
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        return contentPanel;
    }

    private void addMouseMapMarkerMotion() {
        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                ICoordinate pos = map().getPosition(p);
                // TODO: Use the following method to set the text that appears at the mouse cursor
                map().setToolTipText("");
                List<MapMarker> mapMarkers = getMarkersCovering(pos, pixelWidth(p));
                if (!mapMarkers.isEmpty()) {
                    htmlContent ="<!DOCTYPE html>";
                    htmlContent = "<html>";
                    for (MapMarker mapmark : mapMarkers) {
                        MapMarkerFancy mapMarkerFancy = (MapMarkerFancy) mapmark;
                        Color color = mapMarkerFancy.getColor();
                        String hex = "#"+Integer.toHexString(color.getRGB()).substring(2);
                        String nameInfo  = "<p style=\"background-color:"+hex+";\">";
                        nameInfo +=  mapMarkerFancy.getName()+ " (@" + mapMarkerFancy.getUsername()+")</p>";
                        String imageInfo = "<p style=\"background-color:"+hex+";\">";
                        imageInfo += "<img src=" + mapMarkerFancy.getprofilePictureURL() + ">";
                        String tweet = mapMarkerFancy.getTweetContent() + "</p>";
                        htmlContent += nameInfo + imageInfo + tweet +"</html>";
                    }
                    //htmlContent += nameInfo + imageInfo + tweet +"</html>";
                    map().setToolTipText(htmlContent);
                }
            }
        });
    }

    // How big is a single pixel on the map?  We use this to compute which tweet markers
    // are at the current most position.
    private double pixelWidth(Point p) {
        ICoordinate center = map().getPosition(p);
        ICoordinate edge = map().getPosition(new Point(p.x + 1, p.y));
        return SphericalGeometry.distanceBetween(center, edge);
    }

    // Get those layers (of tweet markers) that are visible because their corresponding application.data.query is enabled
    private Set<Layer> getVisibleLayers() {
        Set<Layer> ans = new HashSet<>();
        for (Query q : queries) {
            if (q.getVisible()) {
                ans.add(q.getLayer());
            }
        }
        return ans;
    }

    // Get all the markers at the given map position, at the current map zoom setting
    private List<MapMarker> getMarkersCovering(ICoordinate pos, double pixelWidth) {
        List<MapMarker> ans = new ArrayList<>();
        Set<Layer> visibleLayers = getVisibleLayers();
        for (MapMarker m : map().getMapMarkerList()) {
            if (!visibleLayers.contains(m.getLayer())) continue;
            double distance = SphericalGeometry.distanceBetween(m.getCoordinate(), pos);
            if (distance < m.getRadius() * pixelWidth) {
                ans.add(m);
            }
        }
        return ans;
    }

    public JMapViewer map() {
        return contentPanel.getViewer();
    }

    /*
    /**
     * @param args Application program arguments (which are ignored)
     /*
    public static void main(String[] args) {
        new Application().setVisible(true);
    } */

    // Update which queries are visible after any checkBox has been changed
    public void updateVisibility() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                System.out.println("Recomputing visible queries");
                for (Query q : queries) {
                    JCheckBox box = q.getCheckBox();
                    Boolean state = box.isSelected();
                    q.setVisible(state);
                }
                map().repaint();
            }
        });
    }


    // A application.data.query has been deleted, remove all traces of it
    public void terminateQuery(Query query) {
        // TODO: This is the place where you should disconnect the expiring application.data.query from the application.data.twitter source
        queries.remove(query);
        Set<String> allterms = getQueryTerms();
        twitterSource.setFilterTerms(allterms);
        twitterSource.deleteObserver(query);
    }
}
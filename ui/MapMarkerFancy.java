package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.*;

public class MapMarkerFancy extends MapMarkerCircle {
    public static final int defaultMarkerSize = 22;
    private final int radius = 10;
    private final Coordinate coord;
    private final Color color;
    private final String tweetContent;
    private final Image profilePicture;
    private final String profilePictureURL;
    private final String username;
    private final String name;

    public MapMarkerFancy(Layer layer, Coordinate coord, Color color, String tweetContent, Image profilePicture, String profilePictureURL, String username, String name) {
        super(layer, null, coord, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        this.color = color;
        this.coord = coord;
        this.tweetContent = tweetContent;
        this.profilePicture = profilePicture;
        this.profilePictureURL = profilePictureURL;
        this.username = username;
        this.name = name;

        setColor(Color.BLACK);
        setBackColor(color);
    }

    public String getprofilePictureURL() {
        return profilePictureURL;
    }
    public Image getProfilePicture(){return profilePicture;}
    public String getTweetContent() {
        return tweetContent;
    }
    public String getUsername() {return username;};
    public String getName() {return name;}
    public Color getColor() {return color;}
    //displaying the circle with color and profile pic on the map (override from MapMarkerCircle)
    @Override
    public void paint(Graphics g, Point position, int radius) {
        int size = defaultMarkerSize + this.radius * 2;

        if (g instanceof Graphics2D && this.getBackColor() != null) {
            Graphics2D graphics2D = (Graphics2D)g;
            Composite previousComp = graphics2D.getComposite();
            graphics2D.setComposite(AlphaComposite.getInstance(3));
            graphics2D.setPaint(this.getBackColor());
            g.fillOval(position.x - this.radius, position.y - this.radius, size, size);
            graphics2D.setComposite(previousComp);
        }

        g.setColor(color);
        g.drawOval(position.x - this.radius, position.y - this.radius, size, size);
        g.drawImage(profilePicture, position.x, position.y, defaultMarkerSize, defaultMarkerSize, null);
        if (this.getLayer() == null || this.getLayer().isVisibleTexts()) {
            this.paintText(g, position);
        }


    }

}

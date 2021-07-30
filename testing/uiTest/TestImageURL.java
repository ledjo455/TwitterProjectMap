package testing.uiTest;

import org.junit.jupiter.api.Test;
import util.Util;

import java.awt.image.BufferedImage;


public class TestImageURL {
    @Test
    public void testImageInfo(){
        System.out.printf("-------------------Testing ImageURL ---------------------\n");
    }
    @Test
    public void testImage() {
        BufferedImage norm = Util.imageFromURL("https://www.cs.ubc.ca/~norm");
    }
}

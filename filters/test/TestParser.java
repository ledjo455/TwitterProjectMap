package filters.test;


import filters.*;
import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test the parser.
 */
public class TestParser {
    @Test
    public void testBasic() throws SyntaxError {
        Filter f = new Parser("trump").parse();
        assertTrue(f instanceof BasicFilter);
        assertTrue(((BasicFilter)f).getWord().equals("trump"));
    }

    @Test
    public void testOr() throws SyntaxError{
        Filter f = new Parser("velvet or pie").parse();
        assertTrue(f instanceof OrFilter);
        assertTrue(f.toString().equals("(velvet or pie)"));
        assertFalse(f.toString().equals("velvet or pie"));
    }
    @Test
    public void testAnd() throws SyntaxError{
        Filter f = new Parser("ipad and air").parse();
        assertTrue(f instanceof AndFilter);
        assertTrue(f.toString().equals("(ipad and air)"));
        assertFalse(f.toString().equals("ipad and air"));
    }
    @Test
    public void testNot() throws SyntaxError{
        Filter f = new Parser("not fred").parse();
        assertTrue(f instanceof NotFilter);
        assertTrue(f.toString().equals("not fred"));
        assertFalse(f.toString().equals("(not fred)"));
    }

    @Test
    public void testHairy() throws SyntaxError {
        Filter x = new Parser("trump and (evil or blue) and red or green and not not purple").parse();
        assertTrue(x.toString().equals("(((trump and (evil or blue)) and red) or (green and not not purple))"));
        assertFalse(x.toString().equals("trump and (evil or blue) and red or green and not not purple"));
    }

    @Test
    public void testNotAnd() throws SyntaxError{
        Filter x = new Parser("ipad and not air").parse();
        assertTrue(x.toString().equals("(ipad and not air)"));
        assertFalse(x.toString().equals("(ipad) and not air"));
        assertFalse(x.toString().equals("ipad and not air"));
    }
    @Test
    public void testNotOR() throws SyntaxError{
        Filter x = new Parser("ipad or not air").parse();
        assertTrue(x.toString().equals("(ipad or not air)"));
        assertFalse(x.toString().equals("(ipad) or not air"));
        assertFalse(x.toString().equals("ipad or not air"));
    }

    @Test
    public void testAndOR() throws SyntaxError{
        Filter x = new Parser("ipad and air or new").parse();
        assertTrue(x.toString().equals("((ipad and air) or new)"));
        assertFalse(x.toString().equals("(ipad and (air or new))"));
        assertFalse(x.toString().equals("ipad and air or new"));
    }

    @Test
    public void testORAnd() throws SyntaxError{
        Filter x = new Parser("ipad and iphone or air and new").parse();
        assertTrue(x.toString().equals("((ipad and iphone) or (air and new))"));
        assertFalse(x.toString().equals("(ipad and (iphone or air)) and new)"));
        assertFalse(x.toString().equals("ipad and iphone or air and new"));
    }

    @Test
    public void testDevices() throws SyntaxError {
        Filter x = new Parser("iphone and ipad or pc and laptop or pc and  not iphone").parse();
        assertTrue(x.toString().equals("(((iphone and ipad) or (pc and laptop)) or (pc and not iphone))"));
        assertFalse(x.toString().equals("((iphone and (ipad or (pc and laptop))) or (pc and not iphone))"));
        assertFalse(x.toString().equals("iphone and ipad or pc and laptop or pc and not iphone"));
    }



}

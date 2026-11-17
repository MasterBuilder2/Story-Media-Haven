import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import static org.junit.jupiter.api.Assertions.*;

class MediaTest {

    @org.junit.jupiter.api.Test
    void getGlobal_ID() { //Checks to see whether the global ID is updating properly
        Media media1 = new Media("Title 1", new Date(),"Synopsis 1");
        Media media2 = new Media("Title 2", new Date(),"Synopsis 2");
        Media media3 = new Media("Title 3", new Date(),"Synopsis 3");
        Book b1 = new Book("Title 4", new Date(),"Synopsis 4");
        Movie m1 = new Movie("Title 5", new Date(),"Synopsis 5");
        int firstID = media1.getGlobal_ID();
        assertEquals(firstID+0,media1.getGlobal_ID());
        assertEquals(firstID+1,media2.getGlobal_ID());
        assertEquals(firstID+2,media3.getGlobal_ID());
        assertEquals(firstID+3,b1.getGlobal_ID());
        assertEquals(firstID+4,m1.getGlobal_ID());
    }
}
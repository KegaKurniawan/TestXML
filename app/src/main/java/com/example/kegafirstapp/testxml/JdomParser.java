package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 7/21/2016.
 */

import android.app.ListActivity;
import java.util.ArrayList;
import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JdomParser extends ListActivity {
    /*
    Boolean currentElement = false;
    String currentValue = "";
    BookData book = null;
    private ArrayList&lt;BookData&gt; BookList = new ArrayList&lt;BookData&gt;();

    public ArrayList&lt;BookData&gt; getBooksList() {
        return BookList;
    }

    // Called when tag starts
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";
        if (localName.equals("BookInfo")) {
            book = new BookData();
            Log.w("new book","yo");
            Log.w("Size---&gt;",String.valueOf(BookList.size()));
        }

    }

    // Called when tag closing
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        //set value
        if (localName.equalsIgnoreCase("BookNumber")){
            book.setBookNumber(currentValue);
            Log.d("Booknuomber",currentValue);
        }
        else if (localName.equalsIgnoreCase("Description"))
            book.setDescription(currentValue);
        else if (localName.equalsIgnoreCase("Price"))
            book.setPrice(Double.parseDouble(currentValue));
        else if (localName.equalsIgnoreCase("Rating"))
            book.setRating(Double.parseDouble(currentValue));
        else if (localName.equalsIgnoreCase("BookInfo"))
            BookList.add(book);
    }

    // Called to get tag characters
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);

        }

    }

}


*/
}

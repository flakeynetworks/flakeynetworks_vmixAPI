package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Overlay extends XMLParseable implements Comparable<Overlay> {

    @Attribute
    @VMixStatusAttribute(name = "number", type = Integer.class)
    private int number;


    public boolean equals(Overlay compareTo) {

        return number == compareTo.number;
    } // end of equals


    public void update(Overlay newOverlay) {

        if(newOverlay == null) return;
        if(!equals(newOverlay)) return;
    } // end of update


    public int getNumber() {

        return number;
    } // end of getNumber


    @Override
    public int compareTo(Overlay o) {

        return number - o.number;
    } // end of compareTo
} // end of Overlay

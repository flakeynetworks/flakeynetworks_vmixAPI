package uk.co.flakeynetworks.vmix;

public class VMixVersion implements Comparable<VMixVersion> {


    public static final VMixVersion VERSION_13 = new VMixVersion("13");
    public static final VMixVersion VERSION_14 = new VMixVersion("14");
    public static final VMixVersion VERSION_15 = new VMixVersion("15");
    public static final VMixVersion VERSION_16 = new VMixVersion("16");
    public static final VMixVersion VERSION_17 = new VMixVersion("17");
    public static final VMixVersion VERSION_18 = new VMixVersion("18");
    public static final VMixVersion VERSION_19 = new VMixVersion("19");
    public static final VMixVersion VERSION_20 = new VMixVersion("20");
    public static final VMixVersion VERSION_21 = new VMixVersion("21");
    public static final VMixVersion VERSION_22 = new VMixVersion("22");


    private final VersionSegment number;


    public VMixVersion(String version) {

        number = new VersionSegment(version);
    } // end of constructor


    @Override
    public int compareTo(VMixVersion compare) {

        return number.compareTo(compare.number);
    } // end of compareTo


    public boolean equals(VMixVersion compare) {

        return number.compareTo(compare.number) == 0;
    } // end of equals


    @Override
    public String toString() { return number.toString(); } // end of toString
} // end of VersionNumber


class VersionSegment implements Comparable<VersionSegment> {

    private int versionSegment;
    private static final String divider = ".";
    private VersionSegment nextSegment;


    VersionSegment(String segment) {

        if(segment.contains(divider)) {

            int index = segment.indexOf(divider);

            String prefix;

            prefix = segment.substring(0, index);

            if(segment.length() - 1 != index) {

                String rest = segment.substring(index + 1);
                nextSegment = new VersionSegment(rest);
            } // end of if

            versionSegment = Integer.parseInt(prefix);
        } else {
            versionSegment = Integer.parseInt(segment);
        } // end of if
    } // end of constructor


    private int getSegment() { return versionSegment; } // end of getSegment


    @Override
    public String toString() {

        String suffix = "";

        if(nextSegment != null)
            suffix = nextSegment.toString() + suffix;

        if(suffix.compareTo("") == 0)
            suffix = versionSegment + suffix;
        else
            suffix = versionSegment + "." + suffix;

        return suffix;
    } // end of toString


    @Override
    public int compareTo(VersionSegment version) {

        int otherVersion = version.getSegment();
        if(otherVersion < versionSegment) return -1;
        if(otherVersion > versionSegment) return 1;

        return nextSegment.compareTo(version.getNextSegment());
    } // end of compareTo


    private VersionSegment getNextSegment() { return nextSegment; } // end of getNextSegment
} // end of VersionSegment
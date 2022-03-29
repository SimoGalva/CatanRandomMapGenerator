package hexagon.pojo;

public class SwitchingHexagons {
    private String coordSwitchedHex1;
    private String coordSwitchedHex2;

    public SwitchingHexagons(String coordSwitchedHex1, String coordSwitchedHex2) {
        this.coordSwitchedHex1 = coordSwitchedHex1;
        this.coordSwitchedHex2 = coordSwitchedHex2;
    }

    public String getCoordSwitchedHex1() {
        return coordSwitchedHex1;
    }

    public void setCoordSwitchedHex1(String coordSwitchedHex1) {
        this.coordSwitchedHex1 = coordSwitchedHex1;
    }

    public String getCoordSwitchedHex2() {
        return coordSwitchedHex2;
    }

    public void setCoordSwitchedHex2(String coordSwitchedHex2) {
        this.coordSwitchedHex2 = coordSwitchedHex2;
    }
}

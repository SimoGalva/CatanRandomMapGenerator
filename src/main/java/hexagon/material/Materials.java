package hexagon.material;

public enum Materials {
    RIVER(6),
    STONE(5),
    WOOD(4),
    CLAY(3),
    WOOL(2),
    HAY(1),
    WATER(-1),
    DESERT(-2);

    private int materialCode;

    Materials(int materialCode) { this.materialCode = materialCode; }

    public String toString() { return this.name(); }

    public static Materials fromString(String name) {
        return Materials.valueOf(name.toUpperCase());
    }

}

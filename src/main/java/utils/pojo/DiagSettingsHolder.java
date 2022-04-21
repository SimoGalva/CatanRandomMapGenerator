package utils.pojo;

public class DiagSettingsHolder {
    private final int diagStart;
    private final int diagColsAdding;

    public int getDiagStart() {
        return diagStart;
    }

    public int getDiagColsAdding() {
        return diagColsAdding;
    }

    public DiagSettingsHolder(int diagStart, int diagColsAdding) {
        this.diagStart = diagStart;
        this.diagColsAdding = diagColsAdding;
    }
}

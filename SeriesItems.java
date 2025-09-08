public abstract class SeriesItem {
    protected String seriesID;
    protected String seriesName;
    protected int ageRestriction;

    public SeriesItem(String seriesID, String seriesName, int ageRestriction) {
        this.seriesID = seriesID;
        this.seriesName = seriesName;
        this.ageRestriction = ageRestriction;
    }

    public String getSeriesID() {
        return seriesID;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

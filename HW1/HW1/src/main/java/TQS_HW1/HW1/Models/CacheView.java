package TQS_HW1.HW1.Models;

// Model for ViewController
public class CacheView {
    private int hits;
    private int misses;
    private int getRequests;
    private int saveRequests;
    private int deleteRequests;

    public CacheView(int hits, int misses, int getRequests, int saveRequests, int deleteRequests) {
        this.hits = hits;
        this.misses = misses;
        this.getRequests = getRequests;
        this.saveRequests = saveRequests;
        this.deleteRequests = deleteRequests;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getgetRequests() {
        return getRequests;
    }

    public int getsaveRequests() {
        return saveRequests;
    }

    public int getdeleteRequests() {
        return deleteRequests;
    }
}

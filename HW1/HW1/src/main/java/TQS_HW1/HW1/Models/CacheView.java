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

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getgetRequests() {
        return getRequests;
    }

    public void setgetRequests(int getRequests) {
        this.getRequests = getRequests;
    }

    public int getsaveRequests() {
        return saveRequests;
    }

    public void setsaveRequests(int saveRequests) {
        this.saveRequests = saveRequests;
    }

    public int getdeleteRequests() {
        return deleteRequests;
    }

    public void setdeleteRequests(int deleteRequests) {
        this.deleteRequests = deleteRequests;
    }

    
}

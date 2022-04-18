package TQS_HW1.HW1.Models;

public class CacheView {
    private int hits;
    private int misses;
    private int get_requests;
    private int save_requests;
    private int delete_requests;

    public CacheView(int hits, int misses, int get_requests, int save_requests, int delete_requests) {
        this.hits = hits;
        this.misses = misses;
        this.get_requests = get_requests;
        this.save_requests = save_requests;
        this.delete_requests = delete_requests;
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

    public int getGet_requests() {
        return get_requests;
    }

    public void setGet_requests(int get_requests) {
        this.get_requests = get_requests;
    }

    public int getSave_requests() {
        return save_requests;
    }

    public void setSave_requests(int save_requests) {
        this.save_requests = save_requests;
    }

    public int getDelete_requests() {
        return delete_requests;
    }

    public void setDelete_requests(int delete_requests) {
        this.delete_requests = delete_requests;
    }

    
}

package spittr;

import java.util.Date;

public class Spittle {

    private long spitterId;
    private String message;
    private Date postedTime;
    private Long id;

    public Spittle() {
    }

    public Spittle(long spitter, String message, Date postedTime) {
        this.spitterId = spitter;
        this.message = message;
        this.postedTime = postedTime;
    }

    public Spittle(Long id, long spitter, String message, Date postedTime) {
        this.id = id;
        this.spitterId = spitter;
        this.message = message;
        this.postedTime = postedTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostedTime() {
        return this.postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }

    public long getSpitterId() {
        return this.spitterId;
    }

    public void setSpitterId(long spitterId) {
        this.spitterId = spitterId;
    }
}

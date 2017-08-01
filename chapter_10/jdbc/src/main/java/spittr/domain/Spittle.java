package spittr.domain;

import java.util.Date;

public class Spittle {

    private final long spitterId;
    private final String message;
    private final Date postedTime;
    private Long id;

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

    public String getMessage() {
        return this.message;
    }

    public Date getPostedTime() {
        return this.postedTime;
    }

    public long getSpitterId() {
        return this.spitterId;
    }
}

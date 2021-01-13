package nl.conspect.drivedok.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Reservation extends AbstractPersistable<Long> {

    @Id
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;

    public Reservation() {
    }

    public Reservation(Long id, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

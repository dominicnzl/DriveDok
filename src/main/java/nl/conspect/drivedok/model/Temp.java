package nl.conspect.drivedok.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This should NOT be counted towards code coverage
 */
@Entity
public class Temp {

    @Id
    private Long id;
    private String text;

    public Temp(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package ysaak.imeji.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TAG")
public class Tag {
    @Id
    @GenericGenerator(name = "short_id", strategy = "ysaak.imeji.db.ShortIdGenerator")
    @GeneratedValue(generator = "short_id")
    @Column(name="TAG_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "TAG_NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "TAG_TYPE", length = 4, nullable = false)
    private TagType type;

    @Column(name = "TAG_CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

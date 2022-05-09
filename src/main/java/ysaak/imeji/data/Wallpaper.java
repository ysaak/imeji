package ysaak.imeji.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "WALLPAPER")
public class Wallpaper {

    @Id
    @GenericGenerator(name = "short_id", strategy = "ysaak.imeji.db.ShortIdGenerator")
    @GeneratedValue(generator = "short_id")
    @Column(name="WALL_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "WALL_WIDTH", length = 5, nullable = false)
    private int width;

    @Column(name = "WALL_HEIGHT", length = 5, nullable = false)
    private int height;

    @Column(name = "WALL_FILE_SIZE", length = 10, nullable = false)
    private long fileSize;

    @Column(name = "WALL_HASH", length = 50, nullable = false)
    private long hash;

    @Column(name = "WALL_UPLOAD_DATE", nullable = false)
    private LocalDateTime uploadDate;

    @OneToMany(mappedBy = "wallpaper", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<WallpaperColor> palette;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "TAG_WALLPAPER",
            joinColumns = @JoinColumn(name = "WALL_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private Set<Tag> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Set<WallpaperColor> getPalette() {
        return palette;
    }

    public void setPalette(Set<WallpaperColor> palette) {
        this.palette = palette;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Wallpaper.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("width=" + width)
                .add("height=" + height)
                .add("fileSize=" + fileSize)
                .add("hash=" + hash)
                .add("uploadDate=" + uploadDate)
                .add("palette=" + palette)
                .add("tags=" + tags)
                .toString();
    }
}

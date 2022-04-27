package ysaak.imeji.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "WALLPAPER_COLOR")
public class WallpaperColor {
    @Id
    @GenericGenerator(name = "short_id", strategy = "ysaak.imeji.db.ShortIdGenerator")
    @GeneratedValue(generator = "short_id")
    @Column(name="WACO_ID", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "WACO_WALL_ID", nullable = false)
    private Wallpaper wallpaper;

    @Column(name = "WACO_ORDER", length = 1, nullable = false)
    private int order;

    @Column(name = "WACO_RED", length = 3, nullable = false)
    private int red;

    @Column(name = "WACO_GREEN", length = 3, nullable = false)
    private int green;

    @Column(name = "WACO_BLUE", length = 3, nullable = false)
    private int blue;

    @Column(name = "WACO_CIE_L", nullable = false)
    private double cieL;

    @Column(name = "WACO_CIE_A", nullable = false)
    private double cieA;

    @Column(name = "WACO_CIE_B", nullable = false)
    private double cieB;

    public WallpaperColor() {
    }

    public WallpaperColor(Wallpaper wallpaper, int order, int red, int green, int blue, double cieL, double cieA, double cieB) {
        this.wallpaper = wallpaper;
        this.order = order;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.cieL = cieL;
        this.cieA = cieA;
        this.cieB = cieB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Wallpaper getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public double getCieL() {
        return cieL;
    }

    public void setCieL(double cieL) {
        this.cieL = cieL;
    }

    public double getCieA() {
        return cieA;
    }

    public void setCieA(double cieA) {
        this.cieA = cieA;
    }

    public double getCieB() {
        return cieB;
    }

    public void setCieB(double cieB) {
        this.cieB = cieB;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WallpaperColor.class.getSimpleName() + "[", "]")
                .add("red=" + red)
                .add("green=" + green)
                .add("blue=" + blue)
                .add("cieL=" + cieL)
                .add("cieA=" + cieA)
                .add("cieB=" + cieB)
                .toString();
    }
}

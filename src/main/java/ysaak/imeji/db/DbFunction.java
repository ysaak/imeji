package ysaak.imeji.db;

import ysaak.imeji.utils.ImagePHash;

public class DbFunction {
    public static double calculateColorDistance(final double l1, final double a1, final double b1,
                                                 final double l2, final double a2, final double b2) {
        return Math.sqrt(
                Math.pow(l2 - l1, 2) +
                Math.pow(a2 - a1, 2) +
                Math.pow(b2 - b1, 2)
        );
    }

    public static double calculateImageSimilarity(final long image1Hash, final long image2Hash) {
        return ImagePHash.calculateSimilarity(image1Hash, image2Hash);
    }
}

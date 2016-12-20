package rocks.cow.Util;

public class ColorStripper {
    private static String pattern = "<font color=\".*?\">(.*?)</font>";

    public static String stripColorTags(String target) {
        return target.replaceAll(pattern, "$1");
    }
}

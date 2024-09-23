package com.example.dataalg2assignment1.utils;

import com.example.dataalg2assignment1.Driver;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Function;

public class ImageUtilities {
    public static File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(Driver.getScene().getWindow());
    }

    public static Image getImage() {
        File file = chooseFile();
        System.out.println(file.getPath());
        if (file != null) {
            return new Image(file.getPath(), 300, 300, false, true) {
            //return new Image(file.getPath()) {
                @Override
                public String toString() {
                    return file.getName();
                }
            };
            //setImageDetails(file.getName() + ", " + image.getWidth() + "x" + image.getHeight());
        }
        return null;
    }

    public static WritableImage getWritableImage(Image imageToWrite) {
        Image image = imageToWrite;
        if (image != null) {
            PixelReader pixelReader = image.getPixelReader();
            return new WritableImage(pixelReader, Utilities.doubleToInt(image.getWidth()), Utilities.doubleToInt(image.getHeight()));
        }
        return null;
    }

    static class PixelPair {
        private PixelReader pixelReader;
        private PixelWriter pixelWriter;
        private WritableImage image;

        public PixelPair(WritableImage image) {
            this.image = image;
            this.pixelReader = image.getPixelReader();
            this.pixelWriter = image.getPixelWriter();
        }

        public PixelReader getPixelReader() {
            return pixelReader;
        }

        public PixelWriter getPixelWriter() {
            return pixelWriter;
        }

        public WritableImage getImage() {
            return image;
        }
    }

    //generic image editor - pass in nested for loop and image effect in Function
//    public static WritableImage editImage(Image imageToGreyscale, Function<Color, Color> imageEffect) {
//        WritableImage image = getWritableImage(imageToGreyscale);
//
//        if (image != null) {
//            PixelPair pixelPair = new PixelPair(image);
//            for (int i = 0; i < image.getWidth(); i++) {
//                for (int j = 0; j < image.getHeight(); j++) {
//                    Color color = pixelPair.pixelReader.getColor(i, j);
//                    pixelPair.pixelWriter.setColor(i, j, imageEffect.apply(color));
//                }
//            }
//            return image;
//        }
//        return null;
//    }

    public static int[] analyseImage(Image imageToAnalyse, double hue, double saturation, double brightness,
            double hueThreshold, double saturationThreshold, double brightnessThreshold) {

        final int width = Utilities.doubleToInt(imageToAnalyse.getWidth());
        final int height = Utilities.doubleToInt(imageToAnalyse.getHeight());
        int[] pixelArray = new int[width*height];
        PixelReader pixelReader = imageToAnalyse.getPixelReader();

//        System.out.println((45) + " >= " + (0.5-2) + " || " + ((45)%360) + " <= " + ((0.5+2)%360) + ", " +
//                (45) + " <= " + (0.5+2) + " || " + ((45)%360) + " >= " + (((0.5-2)%360)+360)%360);
//        System.out.println((-1.5)%360);

        for (int i = 0; i < width*height; i++) {
            final Color colour = pixelReader.getColor((int) i%width, (int) i/width);
            if (((colour.getHue() >= (hue-hueThreshold) || (((hue+hueThreshold)%360!=hue+hueThreshold) && (colour.getHue())%360 <= (hue+hueThreshold)%360))) &&
                    (colour.getHue() <= (hue+hueThreshold) || (((hue-hueThreshold)%360!=hue-hueThreshold) && (colour.getHue())%360 >= ((hue-hueThreshold)%360+360)%360)) &&
                    (colour.getSaturation() >= saturation-saturationThreshold && colour.getSaturation() <= saturation+saturationThreshold) &&
                    (colour.getBrightness() >= brightness-brightnessThreshold && colour.getBrightness() <= brightness+brightnessThreshold)) {

                pixelArray[i] = -2;
            } else {
                pixelArray[i] = -1;
            }
        }

//        asciiImage(pixelArray, width);
//        System.out.println("");
        DisjointSetFunctions.combineSets(pixelArray, width);
        //System.out.println(DisjointSetFunctions.countSets(pixelArray, 40));
        //asciiImage(pixelArray, width);

        return pixelArray;
    }

//    public static WritableImage blackAndWhiteImage(Image image, int[] pixelArray) {
//        WritableImage newImage = getWritableImage(image);
//        final int width = Utilities.doubleToInt(newImage.getWidth());
//        final int height = Utilities.doubleToInt(newImage.getHeight());
//
//        PixelReader pixelReader = newImage.getPixelReader();
//        PixelWriter pixelWriter = newImage.getPixelWriter();
//
//        for (int i = 0; i < width*height; i++) {
//            if (i < pixelArray.length && pixelArray[i] != -1) {
//                pixelWriter.setColor(i%width, (int) i/width, new Color(1, 1, 1, 1));
//            } else {
//                pixelWriter.setColor(i%width, (int) i/width, new Color(0, 0, 0, 1));
//            }
//        }
//
//        return newImage;
//    }
//
//    public static WritableImage sampleColourImage(Image image, int[] pixelArray, double hue, double saturation, double brightness) {
//        WritableImage newImage = getWritableImage(image);
//        final int width = Utilities.doubleToInt(newImage.getWidth());
//        final int height = Utilities.doubleToInt(newImage.getHeight());
//
//        PixelReader pixelReader = newImage.getPixelReader();
//        PixelWriter pixelWriter = newImage.getPixelWriter();
//
//        for (int i = 0; i < width*height; i++) {
//            if (i < pixelArray.length && pixelArray[i] != -1) {
//                pixelWriter.setColor(i%width, (int) i/width, Color.hsb(hue, saturation, brightness));
//            } else {
//                pixelWriter.setColor(i%width, (int) i/width, new Color(0, 0, 0, 1));
//            }
//        }
//
//        return newImage;
//    }
//
    public static WritableImage randomColourImage(Image image, int[] pixelArray) {
        double rand = new Random().nextDouble();

        WritableImage newImage = getWritableImage(image);
        final int width = Utilities.doubleToInt(newImage.getWidth());
        final int height = Utilities.doubleToInt(newImage.getHeight());

        PixelReader pixelReader = newImage.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int i = 0; i < width*height; i++) {
            if (i < pixelArray.length && pixelArray[i] != -1) {
                pixelWriter.setColor(i%width, (int) i/width, Color.hsb(new Random(Math.round(DisjointSetFunctions.find(pixelArray, i)+(10000*rand))).nextDouble()*360, 1, 1, 1));
            } else {
                pixelWriter.setColor(i%width, (int) i/width, new Color(0, 0, 0, 1));
            }
        }

        return newImage;
    }

    public interface colourFunction {
        Color newColour();
    }

    public static WritableImage drawImageFromArray(Image image, int[] pixelArray, colourFunction fn) {
        WritableImage newImage = getWritableImage(image);
        final int width = Utilities.doubleToInt(newImage.getWidth());
        final int height = Utilities.doubleToInt(newImage.getHeight());

        PixelReader pixelReader = newImage.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        //asciiImage(pixelArray, width);

        for (int i = 0; i < width*height; i++) {
            if (i < pixelArray.length && pixelArray[i] != -1) {
                pixelWriter.setColor(i%width, (int) i/width, fn.newColour());
            } else {
                pixelWriter.setColor(i%width, (int) i/width, new Color(0, 0, 0, 1));
            }
        }

        return newImage;
    }

    public static WritableImage sampleColourImage(Image image, int[] pixelArray) {
        WritableImage newImage = getWritableImage(image);
        final int width = Utilities.doubleToInt(newImage.getWidth());
        final int height = Utilities.doubleToInt(newImage.getHeight());

        PixelReader pixelReader = newImage.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        //asciiImage(pixelArray, width);

        for (int i = 0; i < width*height; i++) {
            if (i < pixelArray.length && pixelArray[i] != -1) {
                int[] coords = DisjointSetFunctions.indexToCoords(DisjointSetFunctions.find(pixelArray, i), width);
                pixelWriter.setColor(i%width, (int) i/width, pixelReader.getColor(coords[0], coords[1]));
            } else {
                pixelWriter.setColor(i%width, (int) i/width, new Color(0, 0, 0, 1));
            }
        }

        return newImage;
    }

    public static void asciiImage(int[] pixelArray, int width) {
        for (int i = 0; i < pixelArray.length; i++) {
            System.out.print(pixelArray[i] + (((i+1)%width==0) ? "\n" : " "));
        }
    }

    public static WritableImage drawRectangles(Image image, HashMap<Integer, LinkedList<Integer>> hashMap) {
        WritableImage writableImage = getWritableImage(image);

        final int width = Utilities.doubleToInt(writableImage.getWidth());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (Integer key : hashMap.keySet()) {
            int[] bounds = DisjointSetFunctions.getBounds(hashMap, key, width);

            for (int i = bounds[0]%width; i <= bounds[2]%width; i++) {
                pixelWriter.setColor(i%width, bounds[1]/width, new Color(0, 0, 1, 1));
                pixelWriter.setColor(i%width, bounds[3]/width, new Color(0, 0, 1, 1));
            }

            for (int i = bounds[1]; i <= bounds[3]; i+=width) {
                pixelWriter.setColor(bounds[0]%width, i/width, new Color(0, 0, 1, 1));
                pixelWriter.setColor(bounds[2]%width, i/width, new Color(0, 0, 1, 1));
            }
        }

        return writableImage;
    }

    public static int[] numberCoords(Image image, HashMap<Integer, LinkedList<Integer>> hashMap) {
        WritableImage writableImage = getWritableImage(image);
        final int width = Utilities.doubleToInt(writableImage.getWidth());
        //HashMap<Integer, LinkedList<Integer>> hashMap = DisjointSetFunctions.createHashMap(pixelArray);

        //get bottom left corner of each rectangle
        int[][] temp = new int[hashMap.keySet().size()][2];
        int[] corners = new int[hashMap.keySet().size()*2];

        int i = 0;
        for (Integer key : hashMap.keySet()) {
            int[] bounds = DisjointSetFunctions.getBounds(hashMap, key, width);
            temp[i][0] = bounds[0];
            temp[i][1] = bounds[3];
            i++;
        }

        Arrays.sort(temp, (a, b) -> DisjointSetFunctions.coordsToIndex(a[0], a[1], width)-DisjointSetFunctions.coordsToIndex(b[0], b[1], width));

        for (int j = 0; j < temp.length; j++) {
            corners[j*2] = temp[j][0];
            corners[(j*2)+1] = temp[j][1];
        }

        return corners;
    }

    public static int[] analyseTwoTone(Image image,  double hue, double saturation, double brightness,
                                       double hue2, double saturation2, double brightness2,
                                       double hueThreshold, double saturationThreshold, double brightnessThreshold) {
        int[] a = analyseImage(image, hue, saturation, brightness, hueThreshold, saturationThreshold, brightnessThreshold);
        int[] b = analyseImage(image, hue2, saturation2, brightness2, hueThreshold, saturationThreshold, brightnessThreshold);

        HashMap<Integer, LinkedList<Integer>> hashMap1 = DisjointSetFunctions.createHashMap(a);
        HashMap<Integer, LinkedList<Integer>> hashMap2 = DisjointSetFunctions.createHashMap(b);
        HashMap<Integer, LinkedList<Integer>> hashMap3 = DisjointSetFunctions.combineHashMaps(hashMap1, hashMap2);
        int[] c = DisjointSetFunctions.hashMapToArray(hashMap3, (int) image.getWidth(), (int) image.getHeight());

        //asciiImage(a, (int) image.getWidth());

        //System.out.println(hashMap.get(37).toString());

        return c;
    }

}


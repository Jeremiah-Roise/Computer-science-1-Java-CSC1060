import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRenderedImage;
import java.io.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.colorchooser.ColorSelectionModel;


public class Main {
    public static void main(String[] args) {


        try {
           BufferedImage reader = ImageIO.read(new FileInputStream("test.bmp"));
           byte[] RGBVals = ((DataBufferByte) reader.getRaster().getDataBuffer()).getData();



           for (int i = RGBVals.length-1; i > 0; i-=3) {

               if (args[0].equals("redshift")){
                   if (Math.abs(RGBVals[i]) + 10 > 255){
                       RGBVals[i] += 10;
                   }
                   else{
                       RGBVals[i] = 127;
                   }
               }
               else if (args[0].equals("greenshift")){
                   if (Math.abs(RGBVals[i-1]) + 10 > 255){
                       RGBVals[i-1] += 10;
                   }
                   else{
                       RGBVals[i-1] = 127;
                   }
               }
               else if (args[0].equals("blueshift")){
                   if (Math.abs(RGBVals[i-2]) + 10 > 255){
                       RGBVals[i-2] += 10;
                   }
                   else{
                       RGBVals[i-2] = 127;
                   }
               }
           }
           
           
           ImageIO.write(reader, "bmp", new File("testout.bmp"));
            
        } catch (IOException ex) {
            System.exit(1);
        }
    }
}

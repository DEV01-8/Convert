/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev01.pkg8;

import java.io.Console;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Veronica
 */
public class DEV018_Converter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner( System.in ).useLocale(Locale.US);
        System.out.println("Choose conversion.");
        System.out.println("1. GPS to Decimal Degree");
        System.out.println("2. Rijksdriehoek to GPS.");
       
        if(input.nextInt() == 1){
        System.out.println("Type latitude");
        double lat = input.nextDouble();
       
        System.out.println("Type longitude");
        double lon = input.nextDouble();
        
        System.out.println(GPStoDegree(lat, lon));
        }
        
        if(input.nextInt() == 2){
        System.out.println("Type X");
        double x = input.nextDouble();
       
        System.out.println("Type Y");
        double y = input.nextDouble();
        
        System.out.println(Arrays.toString(RDtoGPS(x, y)));
        }
        
        

    }

    public static String GPStoDegree(double latitude, double longitude) {
        try {
            int latSeconds = (int) Math.round(latitude * 3600);
            int latDegrees = latSeconds / 3600;
            latSeconds = Math.abs(latSeconds % 3600);
            int latMinutes = latSeconds / 60;
            latSeconds %= 60;

            int longSeconds = (int) Math.round(longitude * 3600);
            int longDegrees = longSeconds / 3600;
            longSeconds = Math.abs(longSeconds % 3600);
            int longMinutes = longSeconds / 60;
            longSeconds %= 60;
            
            String latDegree = latDegrees >= 0 ? "N" : "S";
            String lonDegrees = latDegrees >= 0 ? "E" : "W";

            return Math.abs(latDegrees) + "°" + latMinutes + "'" + latSeconds
                    + "\"" + latDegree + " " + Math.abs(longDegrees) + "°" + longMinutes
                    + "'" + longSeconds + "\"" + lonDegrees;
            
        } catch (Exception e) {

            return "" + String.format("%8.5f", latitude) + "  "
                    + String.format("%8.5f", longitude);
        }

    }
    
    private static double[] RDtoGPS(double rdx, double rdy){
        int referenceRdX = 155000;
        int referenceRdY = 463000;
        
        double dX = (double)(rdx - referenceRdX) * (double)(Math.pow(10,-5));
        double dY = (double)(rdy - referenceRdY) * (double)(Math.pow(10,-5));

        double sumN = 
            (3235.65389 * dY) + 
            (-32.58297 * Math.pow(dX, 2)) + 
            (-0.2475 * Math.pow(dY, 2)) + 
            (-0.84978 * Math.pow(dX, 2) * dY) + 
            (-0.0655 * Math.pow(dY, 3)) + 
            (-0.01709 * Math.pow(dX, 2) * Math.pow(dY, 2)) + 
            (-0.00738 * dX) + 
            (0.0053 * Math.pow(dX, 4)) + 
            (-0.00039 * Math.pow(dX, 2) * Math.pow(dY, 3)) + 
            (0.00033 * Math.pow(dX, 4) * dY) + 
            (-0.00012 * dX * dY);
        double sumE = 
            (5260.52916 * dX) + 
            (105.94684 * dX * dY) + 
            (2.45656 * dX * Math.pow(dY, 2)) + 
            (-0.81885 * Math.pow(dX, 3)) + 
            (0.05594 * dX * Math.pow(dY, 3)) + 
            (-0.05607 * Math.pow(dX, 3) * dY) + 
            (0.01199 * dY) + 
            (-0.00256 * Math.pow(dX, 3) * Math.pow(dY, 2)) + 
            (0.00128 * dX * Math.pow(dY, 4)) + 
            (0.00022 * Math.pow(dY, 2)) + 
            (-0.00022 * Math.pow(dX, 2)) + 
            (0.00026 * Math.pow(dX, 5));
                
            double referenceWgs84X = 52.15517;
            double referenceWgs84Y = 5.387206;

            double latitude = referenceWgs84X + (sumN / 3600);
            double longitude = referenceWgs84Y + (sumE / 3600);

            double[] result = new double[2];
            result[0] = latitude;
            result[1] = longitude;
            
            return result;
    }

}

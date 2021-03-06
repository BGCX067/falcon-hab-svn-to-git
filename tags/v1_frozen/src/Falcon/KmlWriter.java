/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Falcon;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 *
 * @author Joe
 */
public class KmlWriter {

    public static void writeKml(String sKmlname, LinkedList<Position> posList, String lineTitle, Color lineColor){
        //if left blank, lineTitle will name the line "HABET PATH"
        //if left blank, lineColor will be yellow
        //lineColor need to be 8 character hex
        //System.out.println("" + Integer.toHexString(Color.YELLOW.getRGB()));

        File fKml = new File(sKmlname);
        if(!fKml.exists()){
            try{
                fKml.createNewFile();
            }catch(IOException ioe){
                System.out.println(ioe);
            }
        }
        try{
            FileWriter fw = new FileWriter(fKml);
            PrintWriter pw = new PrintWriter(fw);
            writeKmlHeader(pw, lineTitle, lineColor);
            for(Position p : posList){
                pw.println("        " + p.getReadableLong() + "," + p.getReadableLat() + "," + p.getReadableAlt());
            }
            writeKmlFooter(pw);
            fw.close();
            System.out.println("KML File Created");
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }

    private static LinkedList<String> genKmlBody(LinkedList<Position> posList){
        LinkedList<String> sList = new LinkedList<String>();
        for(Position p : posList){
                sList.add("        " + p.getReadableLong() + "," + p.getReadableLat() + "," + p.getReadableAlt());
        }
        return sList;
    }

    public static String getKmlData(LinkedList<Position> posList, String lineTitle, Color lineColor){
        String ret = "";
        LinkedList<String> sList = genKmlHeader(lineTitle, lineColor);
        sList.addAll(genKmlBody(posList));
        sList.addAll(genKmlFooter());
        for(String s:sList){
            ret = ret + s + "\n";
        }
        return ret;
    }

    private static void writeKmlHeader(PrintWriter pw, String lineTitle, Color lineColor){
        LinkedList<String> sList = genKmlHeader(lineTitle, lineColor);
        for(String s:sList){
            pw.println(s);
        }
    }

    private static LinkedList<String> genKmlHeader(String lineTitle, Color lineColor){
        //TODO: add opacity controls
        if(lineTitle.equals("")) lineTitle = "HABET PATH";
        if(lineColor==null) lineColor = Color.RED;
        LinkedList<String> sList = new LinkedList<String>();
        sList.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sList.add("<kml xmlns=\"http://earth.google.com/kml/2.1\">");
        sList.add("  <Document>");
        sList.add("    <name>"+lineTitle+"</name>");
        sList.add("    <description>HABET path generated by FALCON software. See http://falcon.sscl.iastate.edu for more information.</description>");
        sList.add("    <Style id=\"yellowLineGreenPoly\">");
        sList.add("      <LineStyle>");
        sList.add("        <color>"+Integer.toHexString(lineColor.getRGB())+"</color>");
        sList.add("        <width>4</width>");
        sList.add("      </LineStyle>");
        sList.add("      <PolyStyle>");
        sList.add("        <color>7f00ff00</color>");
        sList.add("      </PolyStyle>");
        sList.add("    </Style>");
        sList.add("    <Placemark>");
        sList.add("      <name>"+lineTitle+"</name>");
        sList.add("      <description>Transparent green wall with yellow outlines</description>");
        sList.add("      <styleUrl>#yellowLineGreenPoly</styleUrl>");
        sList.add("      <LineString>");
        sList.add("        <extrude>1</extrude>");
        sList.add("        <tessellate>1</tessellate>");
        sList.add("        <altitudeMode>absolute</altitudeMode>");
        sList.add("        <coordinates>");
        return sList;
    }

    private static void writeKmlFooter(PrintWriter pw){
        LinkedList<String> sList = genKmlFooter();
        for(String s:sList){
            pw.println(s);
        }
    }

    private static LinkedList<String> genKmlFooter(){
        LinkedList<String> sList = new LinkedList<String>();
        sList.add("        </coordinates>");
        sList.add("      </LineString>");
        sList.add("    </Placemark>");
        sList.add("  </Document>");
        sList.add("</kml>	");
        return sList;
    }
}

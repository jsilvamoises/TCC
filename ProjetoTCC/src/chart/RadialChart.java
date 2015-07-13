/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chart;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.gauge.ColorDef;
import jfxtras.labs.scene.control.gauge.Gauge;
import jfxtras.labs.scene.control.gauge.GaugeBuilder;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.Marker;
import jfxtras.labs.scene.control.gauge.Section;

/**
 *
 * @author MOISES
 */
public class RadialChart  extends Application{
    Marker temperatura  = new Marker(50, Color.CORAL);
    Marker[] markerList = new Marker[]{temperatura};
    
    private void teste(){
        Section s1  = new Section(0, 37, Color.CORAL);
        Section s2  = new Section(37, 60, Color.YELLOW);
        Section s3  = new Section(60, 75, Color.ORANGE);
        Gauge radial = GaugeBuilder.create()
                           .prefWidth(500)
                           .prefHeight(500)
                           .gaugeType(GaugeBuilder.GaugeType.RADIAL)
                           .frameDesign(Gauge.FrameDesign.STEEL)
                           .backgroundDesign(Gauge.BackgroundDesign.DARK_GRAY)
                           .lcdDesign(LcdDesign.STANDARD_GREEN)
                           .lcdDecimals(2)
                           .lcdValueFont(Gauge.LcdFont.LCD)
                           .pointerType(Gauge.PointerType.TYPE14)
                           .valueColor(ColorDef.RED)
                           .knobDesign(Gauge.KnobDesign.METAL)
                           .knobColor(Gauge.KnobColor.SILVER)
                           .sections(s1,s2,s3)
                           .sectionsVisible(true)
                           .areas(new Section[] {new Section(75, 100, Color.RED)})
                           .areasVisible(true)
                           .markers(new Marker[] {
                               new Marker(30, Color.MAGENTA),
                               new Marker(75, Color.AQUAMARINE)})
                           .markersVisible(true)
                           .threshold(40)
                           .thresholdVisible(true)
                           .glowVisible(true)
                           .glowOn(true)
                           .trendVisible(true)
                           .trend(Gauge.Trend.UP)
                           .userLedVisible(true)
                           .bargraph(true)
                           .title("Temperature")
                           .unit("°C")
                           .build();
    }
          

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

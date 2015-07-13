package resources.background;


import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MOISES
 */
public class IBackGround {
    private static CornerRadii CORNER_10  = new CornerRadii(2, false);
    public static final Background  BACKGROUND_WHITE = new Background(new BackgroundFill(Color.WHITE, CORNER_10, Insets.EMPTY));
    public static final Background  BACKGROUND_DARKGREEN = new Background(new BackgroundFill(Color.DARKGREEN, CORNER_10, Insets.EMPTY));
    public static final Background  BACKGROUND_LIGHTSEAGREEN = new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CORNER_10, Insets.EMPTY));
    public static final Background  BACKGROUND_TRANSPARENT = new Background(new BackgroundFill(Color.TRANSPARENT, CORNER_10, Insets.EMPTY));
    public static final Background  BACKGROUND_FIREBRICK = new Background(new BackgroundFill(Color.FIREBRICK, CORNER_10, Insets.EMPTY));
}

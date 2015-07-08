/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.net.URL;

/**
 *
 * @author MOISES
 */
public class URLView {
   private static URLView instance;
   public static URLView getInstance(){
       return instance ==null? instance= new URLView():instance;
   }
   
   public URL getUrl(String viewPage){
       return getClass().getResource("/br/com/moises/view/".concat(viewPage).concat(".fxml"));
   }
    
}

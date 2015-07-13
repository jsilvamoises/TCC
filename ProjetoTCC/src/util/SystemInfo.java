/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author MOISES
 */
public class SystemInfo {
    static int mb = 1024^2;
    public static Long getTotalMemory(){
        return Runtime.getRuntime().totalMemory();
    }
    public static Long getFreeMomory(){
        return Runtime.getRuntime().freeMemory();
    }
    public static Long getUsedMemory(){
       return getTotalMemory()-getFreeMomory();
    }
    public static long getMaxMemory(){
        return Runtime.getRuntime().maxMemory();
    }
    
    public static int getTotalMemoryInMB(){
           return (int) (getTotalMemory()/mb);
    }
    
    public static int getFreeMomoryInMB(){
        return (int) (getFreeMomory()/mb);
    }
    public static int getUsedMemoryInMB(){
       return (int) (getTotalMemory()-getFreeMomory())/mb;
    }
    public static int getMaxMemoryInMB(){
        return (int) (getMaxMemory()/mb);
    }
    
    public static float getUsedMemoryInPercent(){
        return (float) getUsedMemory()/getTotalMemory();
    }
}

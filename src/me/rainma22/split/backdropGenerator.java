package me.rainma22.split;

import java.util.ArrayList;

public class backdropGenerator {
    public static void init(){
        generate(512,false);
    }
    public static synchronized void generate(int count,boolean b){
        ArrayList<backdrop> backdrops= (ArrayList<backdrop>) Main.getBackdrops().clone();
        for (int i = 0; i < count; i++) {
            backdrops.add(new backdrop(b));
        }
        Main.setBackdrops(backdrops);
    }
}

package util;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hit implements Runnable{
    private CopyOnWriteArrayList<GameObject> HitList  = new CopyOnWriteArrayList<GameObject>();
    private GameObject g;
    private int delay_time = 500;

    public Hit(CopyOnWriteArrayList<GameObject> HitList,GameObject g){
        this.HitList = HitList;
        this.g = g;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay_time);
            HitList.remove(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.interrupted();
    }
}

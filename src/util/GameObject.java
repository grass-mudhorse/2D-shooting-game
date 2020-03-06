package util;

/*

 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */
public class GameObject {

    public Vector3f vector;
    private Point3f centre = new Point3f(0, 0, 0);            // Centre of object, using 3D as objects may be scaled
    private int width = 10;
    private int height = 10;
    private boolean hasTextured = false;
    private String textureLocation;
    private String blanktexture = "res/blankSprite.png";
	public int health = 1;

    public GameObject() {

    }

    public GameObject(String textureLocation, int width, int height, Point3f centre) {
        hasTextured = true;
        this.textureLocation = textureLocation;
        this.width = width;
        this.height = height;
        this.centre = centre;
    }

    public Point3f getCentre() {
        return centre;
    }

    public void setCentre(Point3f centre) {
        this.centre = centre;

        //make sure to put boundaries on the gameObject

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTexture() {
        if (hasTextured) {
            return textureLocation;
        }

        return blanktexture;
    }

    public Vector3f getVector() {
        return vector;
    }

    public void setVector(Vector3f v) {
        vector = v;
    }

    public void ApplyrandomMove() {
        if (getCentre().getX() >= 950f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (270 - 90) + 90) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getX() <= 0f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180) - 90) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getY() <= 150f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180) - 180) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getY() >= 760f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180)) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        }
    }

    public void ApplyrandomupgradeMove() {
        if (getCentre().getX() >= 950f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (270 - 90) + 90) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getX() <= 0f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180) - 90) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getY() <= 140f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180) - 180) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getY() >= 760f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180)) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        }
    }

    public void ApplyrandombossMove() {
        if (getCentre().getX() >= 950f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (270 - 90) + 90) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getX() <= 600f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180) - 90) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getY() <= 150f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180) - 180) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        } else if (getCentre().getY() >= 760f) {
            float randomAngel = (float) ((float) Math.PI * (Math.random() * (180)) / 180);
            vector = new Vector3f((float) ((float) Math.cos(randomAngel) * 0.5), (float) ((float) Math.sin(randomAngel) * 0.5), 0);
        }
    }

}

/*
 *  Game Object 
 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::c:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::clc::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::lol:;::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::;;cool:;::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::codk0Oxolc:::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::coxk0XNWMMWWWNK0kxdolc::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::loxO0XWMMMMMMMWWWMMMMMMWWNK0Oxdlc::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::cldkOKNWMMMMMMMMMMMMMWWMMMMMMMMMMMMMWNX0Okdolc:::::::::::::::::::::::::::::::::
::::::::::::::::codk0KNWMMMMMMMMMMMMMMMMMMWMMMMMMMMMMMMMMMMMMMMWWXKOkxo:::::::::::::::::::::::::::::
::::::::::::;cdOXNWMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMMMMMNKOdc::::::::::::::::::::::::::::
:::::::::::::cxKXXNWWMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMWX0kdolc:::::::::::::::::::::::::::::
::::::::::::::d0000KKXNNWMMMMMMMMMMMMMMMMWWMMMMMMMMMMMMMMMMWNKOxolllllc:::::::::::::::::::::::::::::
::::::::::::::oO00000000KXXNWWMMMMMMMMMMMMWMMMMMMMMMMMMMWX0kdollllllllc:::::::::::::::::::::::::::::
::::::::::::::lk00000O000000KKXNWWMMMMMMMWWWMMMMMMMMWNKOxollllllllllll::::::::::::::::::::::::::::::
::::::::::::::cx0000000000O000000KXXNWMMMWWWMMMMWXK0kdlllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::dO00000000000000000000KKXNNNWWNKOxolllllllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::lO000000000000000OOOO0000000Kkdlllllllllllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::ck00000000000000000OOOOOOOOkxollllllllllllllllllllllll:::::::::::::::::::::::::::::::
:::::::::::::;;cx00000000000000000000OOOOOOxocllllllllllllllllllllllc:;;;;;;;;;;::::::::::::::::::::
;;;;;;;;;;;;;;;:oO00000000000000000OOOO0000kdllllcclllllllllllllllllc:;;;;;;;;;;;;;;;;;;::::::::::::
;;;;;;;;;;;;;;;:lO00000000000000OOO00000000Oolllllllllllllllllllllllc:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;ck0000000000OOO000000000000kolllllllllllllllllllllll:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;:dOO0000OOO0000000000000000kolllllllllllllllllllllllc:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;:lxkOOOO0000000000000000000kolllllllllllllllllllllooool::;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;::;;::cokOkkO00000000000000000000kolllllllllllllllllllllccccllcc::::;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;:;;:;::ccllodxkO00000000000000000000kolllllllllllllllllllcc:;;;;:::::;;:;;;;;;;;;;;;;;;;;;;;;
;;;;;;;::::::::::;;:ldkO0000000000000000000kolllllllllllllllllc::;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;::;;:::;;;;;:;::ldkO0000000000000000kollllllllllllllcc::;;;;:;;:;;;;;:;;;:;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;:;;;;:cldkO0000000000000kollllllllllllc:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;::ldkO0000000000kolllllllllcc::;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;::;:;;::ldkO0000000kolllllllc::::;;;;:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;::ldkO0000kolllcc:::;;;;;;;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;;;;:ldkO0kolcc::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;;::lodl:::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;::;:::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;::;;;;;;;;;;;:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
*/
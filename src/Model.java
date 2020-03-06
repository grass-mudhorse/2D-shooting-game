import util.GameObject;
import util.Hit;
import util.Point3f;
import util.Vector3f;

import java.util.concurrent.CopyOnWriteArrayList;
/*
 *

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

/*
 * Reference List:
 * 1. player 1 image: https://opengameart.org/content/war-on-water-gfx
 * 2. player 2 image: https://opengameart.org/content/war-on-water-gfx
 * 3. The pick-ups of shield, power-up, and air force support : https://opengameart.org/content/shield-OCAL
 * 4. Shield : https://opengameart.org/content/shield-aura-effect
 * 5. Boss: https://opengameart.org/content/large-spaceship
 * 6. Background: https://www.123rf.com/photo_49703570_stock-vector-water-waves-or-ocean-sea-seamless-backgrounds-set-.html?fromid=L1ZwNUpFaVNUS0J1a1VBemtRQ3A0QT09
 * 7. Boom : https://opengameart.org/content/war-on-water-gfx
 * 8. player1 and player2's missiles : https://opengameart.org/content/war-on-water-gfx
 */
public class Model {

    private GameObject Player1;
    private GameObject Player2;
    private GameObject Boss = null;
    private boolean bossDead = false;
    private boolean bossini = false;
    private int bossHP = 100;
    private long bossAttack = 0;

    private GameObject support = null;
    private CopyOnWriteArrayList<GameObject> supportBulletList = new CopyOnWriteArrayList<GameObject>();

    private GameObject shieldPlayer1;
    private GameObject shieldPlayer2;

    private CopyOnWriteArrayList<GameObject> EnemiesList = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> Player1BulletList_0 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> Player1BulletList_1 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> Player1BulletList_2 = new CopyOnWriteArrayList<GameObject>();

    private CopyOnWriteArrayList<GameObject> Player2BulletList_0 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> Player2BulletList_1 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> Player2BulletList_2 = new CopyOnWriteArrayList<GameObject>();

    private CopyOnWriteArrayList<GameObject> HitList = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> EnemyBulletList1 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> EnemyBulletList2 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> EnemyBulletList3 = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> PlayerList = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> Shield = new CopyOnWriteArrayList<GameObject>();
    private CopyOnWriteArrayList<GameObject> ShieldUsing = new CopyOnWriteArrayList<GameObject>();

    private CopyOnWriteArrayList<GameObject> upgrade = new CopyOnWriteArrayList<GameObject>();

    private CopyOnWriteArrayList<GameObject> supportPick = new CopyOnWriteArrayList<GameObject>();

    private boolean Shieldflag = false;
    private long count = 0;
    private boolean plaerini = false;
    private int Score = 0;
    private int stage = 1;
    private int numSupport = 0;
    private boolean pass = false;
    private int health1 = 10000;
    private int health2 = 10000;

    private int enemyHealth = 2;
    private int Player1Shield = 0;
    private int Player2Shield = 0;

    private int SP1 = 0;
    private int SP2 = 0;

    private int difficulty = 0; // 1 means simple, 2 means difficult.

    public Model() {
        // setup game world
        // Player1
        Player1 = new GameObject("res/player1.png", 91, 50, new Point3f(500, 500, 0));
        Player2 = new GameObject("res/ship.png", 115, 32, new Point3f(500, 118, 0));

        // Enemies starting with four
        for (int i = 0; i < 4; i++)
            EnemiesList.add(new GameObject("res/Ninja.png", 30, 30,
                    new Point3f(950, ((float) (Math.random() * (760 - 150) + 150)), 0)));

    }

    // This is the heart of the game , where the model takes in all the inputs
    // ,decides the outcomes and then changes the model accordingly.
    public void gamelogic() {
        if (!plaerini) {
            if (MainWindow.flag == 0) {
                PlayerList.add(Player1);
            } else if (MainWindow.flag == 1) {
                PlayerList.add(Player2);
            } else {
                PlayerList.add(Player1);
                PlayerList.add(Player2);
            }
            plaerini = true;
            this.difficulty = MainWindow.diff;
        }

        if (getStage() == 5) {
            if (!bossini) {
                Boss = new GameObject("res/boss.png", 190, 120, new Point3f(1000, 400, 0));
                bossini = true;
            }
        }
        if (!bossDead || !PlayerList.isEmpty()) {
            Player1Logic();
            Player2Logic();

            // Enemy Logic next
            enemyLogic();
            // Bullets move next

            // interactions between objects
            gameLogic();
            //
            EnemybulletLogic();
            //
            shieldMove();

            upgradeMove();

            bossLogic();

            supportLogic();

            generateSupport();
        }
        bulletLogic();
        // Player1 Logic first

    }

    private void gameLogic() {

        if (getBoss() != null && !bossDead) {

            for (GameObject Bullet : supportBulletList) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);

                    supportBulletList.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        bossDead = true;
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player1BulletList_0) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);

                    Player1BulletList_0.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        bossDead = true;
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player1BulletList_1) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    Player1BulletList_1.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        bossDead = true;
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player1BulletList_2) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    Player1BulletList_2.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        bossDead = true;
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player2BulletList_0) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);

                    Player2BulletList_0.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        bossDead = true;
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player2BulletList_1) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);

                    Player2BulletList_1.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        bossDead = true;
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player2BulletList_2) {
                if (Math.abs(Boss.getCentre().getX() - Bullet.getCentre().getX()) < Boss.getWidth()
                        && Math.abs(Boss.getCentre().getY() - Bullet.getCentre().getY()) < Boss.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    Player2BulletList_2.remove(Bullet);
                    bossHP--;
                    if (bossHP <= 0) {
                        bossDead = true;
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (Boss.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

        }

        // this is a way to increment across the array list data structure

        // see if they hit anything
        // using enhanced for-loop style as it makes it alot easier both code wise and
        // reading wise too
        for (GameObject temp : EnemiesList) {
            for (GameObject Bullet : supportBulletList) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    
                    supportBulletList.remove(Bullet);
                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player1BulletList_0) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);

                    if (temp.health <= 0) {
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    } else {
                        temp.health--;
                    }
                    Player1BulletList_0.remove(Bullet);

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player1BulletList_1) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (temp.health <= 0) {
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    } else {
                        temp.health--;
                    }
                    Player1BulletList_1.remove(Bullet);

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player1BulletList_2) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (temp.health <= 0) {
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    } else {
                        temp.health--;
                    }
                    Player1BulletList_2.remove(Bullet);

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player2BulletList_0) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (temp.health <= 0) {
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    } else {
                        temp.health--;
                    }
                    Player2BulletList_0.remove(Bullet);

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player2BulletList_1) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (temp.health <= 0) {
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    } else {
                        temp.health--;
                    }
                    Player2BulletList_1.remove(Bullet);

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            for (GameObject Bullet : Player2BulletList_2) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (temp.health <= 0) {
                        EnemiesList.remove(temp);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre()));
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    } else {
                        temp.health--;
                    }
                    Player2BulletList_2.remove(Bullet);

                    // HitList.remove(bumb);
                    // delay_bumb(HitList,bumb);
                    // BulletList.remove(bumb);
                    Score++;
                }
            }

            if (getPlayer1() != null) {
                if (Math.abs(temp.getCentre().getX() - Player1.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Player1.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    EnemiesList.remove(temp);
                    if (Player1Shield <= 0) {
                        health1--;
                    } else {

                        Player1Shield--;
                        if (Player1Shield <= 0)
                            ShieldUsing.remove(shieldPlayer1);
                    }
                    System.out.println(1);
                    if (health1 <= 0) {
                        System.out.println(2);
                        PlayerList.remove(Player1);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre())); // BulletList.add(index_Bullet,bumb);
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }

            if (getPlayer2() != null) {
                if (Math.abs(temp.getCentre().getX() - Player2.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Player2.getCentre().getY()) < temp.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    EnemiesList.remove(temp);
                    if (Player2Shield <= 0) {
                        health2--;
                    } else {
                        Player2Shield--;

                        if (Player2Shield <= 0)
                            ShieldUsing.remove(shieldPlayer2);
                    }
                    System.out.println(1);
                    if (health2 <= 0) {
                        System.out.println(2);
                        PlayerList.remove(Player2);
                        GameObject bumb = new GameObject("res/bumb.png", 50, 50, (temp.getCentre())); // BulletList.add(index_Bullet,bumb);
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }

        }

        // Player Enemy collision
        // if Player has been hit detection
        if (getPlayer1() != null) {

            for (GameObject shield : Shield) {
                if (Math.abs(Player1.getCentre().getX() - shield.getCentre().getX()) < Player1.getWidth()
                        && Math.abs(Player1.getCentre().getY() - shield.getCentre().getY()) < Player1.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    Shield.remove(shield);
                    shieldPlayer1 = new GameObject("res/shield.png", 80, 80, (Player1.getCentre()));
                    ShieldUsing.add(shieldPlayer1);
                    // testS.put(1,new GameObject("res/shield.png",80,80,(Player1.getCentre())));
                    Player1Shield = 5;
                    if (difficulty != 1)
                        health1 += 2;
                }
            }

            for (GameObject up : upgrade) {
                if (Math.abs(Player1.getCentre().getX() - up.getCentre().getX()) < Player1.getWidth()
                        && Math.abs(Player1.getCentre().getY() - up.getCentre().getY()) < Player1.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    upgrade.remove(up);
                    // testS.put(1,new GameObject("res/shield.png",80,80,(Player1.getCentre())));
                    SP1 += 10;
                }
            }

            for (GameObject s : supportPick) {
                if (Math.abs(Player1.getCentre().getX() - s.getCentre().getX()) < Player1.getWidth()
                        && Math.abs(Player1.getCentre().getY() - s.getCentre().getY()) < Player1.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    supportPick.remove(s);
                    // testS.put(1,new GameObject("res/shield.png",80,80,(Player1.getCentre())));
                    numSupport++;
                }
            }

            for (GameObject Bullet : EnemyBulletList1) {
                if (Math.abs(Player1.getCentre().getX() - Bullet.getCentre().getX()) < Player1.getWidth()
                        && Math.abs(Player1.getCentre().getY() - Bullet.getCentre().getY()) < Player1.getHeight()) {
                    if (Player1Shield <= 0) {
                        health1--;
                    } else {

                        Player1Shield--;
                        if (Player1Shield <= 0)
                            ShieldUsing.remove(shieldPlayer1);
                    }
                    EnemyBulletList1.remove(Bullet);
                    System.out.println(3);
                    if (health1 <= 0) {
                        System.out.println(4);
                        GameObject bumb = new GameObject("res/bumb.png", 30, 30, (Player1.getCentre()));
                        PlayerList.remove(Player1);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }

            for (GameObject Bullet : EnemyBulletList2) {
                if (Math.abs(Player1.getCentre().getX() - Bullet.getCentre().getX()) < Player1.getWidth()
                        && Math.abs(Player1.getCentre().getY() - Bullet.getCentre().getY()) < Player1.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (Player1Shield <= 0) {
                        health1--;
                    } else {
                        Player1Shield--;

                        if (Player1Shield <= 0)
                            ShieldUsing.remove(shieldPlayer1);
                    }
                    EnemyBulletList2.remove(Bullet);
                    if (health1 <= 0) {
                        GameObject bumb = new GameObject("res/bumb.png", 30, 30, (Player1.getCentre()));
                        PlayerList.remove(Player1);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }

            for (GameObject Bullet : EnemyBulletList3) {
                if (Math.abs(Player1.getCentre().getX() - Bullet.getCentre().getX()) < Player1.getWidth()
                        && Math.abs(Player1.getCentre().getY() - Bullet.getCentre().getY()) < Player1.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (Player1Shield <= 0) {
                        health1--;
                    } else {
                        Player1Shield--;

                        if (Player1Shield <= 0)
                            ShieldUsing.remove(shieldPlayer1);
                    }
                    EnemyBulletList3.remove(Bullet);
                    if (health1 <= 0) {
                        GameObject bumb = new GameObject("res/bumb.png", 30, 30, (Player1.getCentre()));
                        PlayerList.remove(Player2);
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }
        }

        if (getPlayer2() != null) {

            for (GameObject shield : Shield) {
                if (Math.abs(Player2.getCentre().getX() - shield.getCentre().getX()) < Player2.getWidth()
                        && Math.abs(Player2.getCentre().getY() - shield.getCentre().getY()) < Player2.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    Shield.remove(shield);
                    shieldPlayer2 = new GameObject("res/shield.png", 80, 80, (Player2.getCentre()));
                    ShieldUsing.add(shieldPlayer2);
                    // testS.put(1,new GameObject("res/shield.png",80,80,(Player1.getCentre())));
                    Player2Shield = 5;
                    if (difficulty != 1)
                        health2 += 2;
                }
            }

            for (GameObject up : upgrade) {
                if (Math.abs(Player2.getCentre().getX() - up.getCentre().getX()) < Player2.getWidth()
                        && Math.abs(Player2.getCentre().getY() - up.getCentre().getY()) < Player2.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    upgrade.remove(up);
                    // testS.put(1,new GameObject("res/shield.png",80,80,(Player1.getCentre())));
                    SP2 += 10;
                }
            }

            for (GameObject s : supportPick) {
                if (Math.abs(Player2.getCentre().getX() - s.getCentre().getX()) < Player2.getWidth()
                        && Math.abs(Player2.getCentre().getY() - s.getCentre().getY()) < Player2.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    supportPick.remove(s);
                    // testS.put(1,new GameObject("res/shield.png",80,80,(Player1.getCentre())));
                    numSupport++;
                }
            }

            for (GameObject Bullet : EnemyBulletList1) {
                if (Math.abs(Player2.getCentre().getX() - Bullet.getCentre().getX()) < Player2.getWidth()
                        && Math.abs(Player2.getCentre().getY() - Bullet.getCentre().getY()) < Player2.getHeight()) {
                    if (Player2Shield <= 0) {
                        health2--;
                    } else {
                        Player2Shield--;

                        if (Player2Shield <= 0)
                            ShieldUsing.remove(shieldPlayer2);
                    }
                    EnemyBulletList1.remove(Bullet);
                    System.out.println(3);
                    if (health2 <= 0) {
                        System.out.println(4);
                        GameObject bumb = new GameObject("res/bumb.png", 30, 30, (Player2.getCentre()));
                        PlayerList.remove(Player2);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }

            for (GameObject Bullet : EnemyBulletList2) {
                if (Math.abs(Player2.getCentre().getX() - Bullet.getCentre().getX()) < Player2.getWidth()
                        && Math.abs(Player2.getCentre().getY() - Bullet.getCentre().getY()) < Player2.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (Player2Shield <= 0) {
                        health2--;
                    } else {
                        Player2Shield--;

                        if (Player2Shield <= 0)
                            ShieldUsing.remove(shieldPlayer2);
                    }
                    EnemyBulletList2.remove(Bullet);
                    if (health2 <= 0) {
                        GameObject bumb = new GameObject("res/bumb.png", 30, 30, (Player2.getCentre()));
                        PlayerList.remove(Player2);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }

            for (GameObject Bullet : EnemyBulletList3) {
                if (Math.abs(Player2.getCentre().getX() - Bullet.getCentre().getX()) < Player2.getWidth()
                        && Math.abs(Player2.getCentre().getY() - Bullet.getCentre().getY()) < Player2.getHeight()) {
                    // int index_Bullet = BulletList.indexOf(Bullet);
                    if (Player2Shield <= 0) {
                        health2--;
                    } else {
                        Player2Shield--;

                        if (Player2Shield <= 0)
                            ShieldUsing.remove(shieldPlayer2);
                    }
                    EnemyBulletList3.remove(Bullet);
                    if (health2 <= 0) {
                        GameObject bumb = new GameObject("res/bumb.png", 30, 30, (Player2.getCentre()));
                        PlayerList.remove(Player2);
                        // BulletList.add(index_Bullet,bumb);
                        HitList.add(bumb);
                        Hit hit = new Hit(HitList, bumb);
                        Thread t = new Thread(hit);
                        t.start();
                    }
                }
            }
        }

    }

    public int setStage(int i) {
        stage = i;
        return stage;
    }

    private void shieldMove() {

        if (difficulty == 1) {
            if (Shield.size() <= 1 && !Shield.isEmpty()) {
                while (Shield.size() <= 2) {
                    Shield.add(new GameObject("res/shield-OCAL.png", 30, 38,
                            new Point3f(1000, ((float) (Math.random() * (760 - 150) + 150)), 0)));
                }
            }
            if (count % 5000 == 0)
                Shield.add(new GameObject("res/shield-OCAL.png", 30, 38, new Point3f(1000, 140, 0)));
        } else {

            // if (Shield.size() <=1 && !Shield.isEmpty())
            // {
            // while (Shield.size()<2)
            // {
            // Shield.add(new GameObject("res/shield-OCAL.png",30,38,new
            // Point3f(1000,((float)(Math.random()*(760-150)+150)),0)));
            // }
            // }
            // if(count % 2000 == 0)
            // Shield.add(new GameObject("res/shield-OCAL.png",30,38,new
            // Point3f(1000,((float)(Math.random()*(760-150)+150)),0)));

            if (count % 5000 == 0 && Shield.size() <= 1)
                Shield.add(new GameObject("res/shield-OCAL.png", 30, 38, new Point3f(1000, 140, 0)));

        }
        for (GameObject s : Shield) {
            s.ApplyrandomMove();
            s.getCentre().ApplyVector(s.vector);
            // if(s.getCentre().getX()<=0.0f)
            // Shield.remove(s);
        }
    }

    private void upgradeMove() {

        if (difficulty == 1) {
            if (upgrade.size() <= 1 && !upgrade.isEmpty()) {
                while (upgrade.size() <= 2) {
                    upgrade.add(new GameObject("res/upgrade.png", 30, 38,
                            new Point3f(1000, ((float) (Math.random() * (760 - 150) + 150)), 0)));
                }
            }
            if (count % 5000 == 0)
                upgrade.add(new GameObject("res/upgrade.png", 30, 38,
                        new Point3f(1000, ((float) (Math.random() * (760 - 150) + 150)), 0)));

        } else {
            // if (upgrade.size() <=1 && !upgrade.isEmpty())
            // {
            // while (upgrade.size()<2)
            // {
            // upgrade.add(new GameObject("res/upgrade.png",30,38,new
            // Point3f(1000,((float)(Math.random()*(760-150)+150)),0)));
            // }
            // }
            // if(count % 2000 == 0)
            // upgrade.add(new GameObject("res/upgrade.png",30,38,new
            // Point3f(1000,((float)(Math.random()*(760-150)+150)),0)));
            if (count % 5000 == 0 && upgrade.size() <= 1)
                upgrade.add(new GameObject("res/upgrade.png", 30, 38, new Point3f(1000, 140, 0)));

        }
        for (GameObject s : upgrade) {

            s.ApplyrandomupgradeMove();
            s.getCentre().ApplyupgradeVector(s.vector);
        }
    }

    private void generateSupport() {

        if (difficulty == 1) {
            if (supportPick.size() <= 1 && !supportPick.isEmpty()) {
                while (supportPick.size() <= 2) {
                    supportPick.add(new GameObject("res/supportPick.png", 30, 38,
                            new Point3f(1000, ((float) (Math.random() * (760 - 150) + 150)), 0)));
                }
            }
            // if (count % 2000 == 0)
            // supportPick.add(new GameObject("res/supportPick.png", 30, 38, new
            // Point3f(1000, ((float) (Math.random() * (760 - 150) + 150)), 0)));

            if (count % 5000 == 0)
                supportPick.add(new GameObject("res/supportPick.png", 30, 38, new Point3f(1000, 140, 0)));
        } else {

            if (count % 5000 == 0 && supportPick.size() <= 1)
                supportPick.add(new GameObject("res/supportPick.png", 30, 38, new Point3f(1000, 140, 0)));

        }
        for (GameObject s : supportPick) {

            s.ApplyrandomupgradeMove();
            s.getCentre().ApplyupgradeVector(s.vector);
        }
    }

    private void enemyLogic() {
        // TODO Auto-generated method stub
        for (GameObject temp : EnemiesList) {
            if (stage >= 4) {
                // temp.vector = new Vector3f((float)-0.5,0,0);
                temp.ApplyrandomMove();
                temp.getCentre().ApplysubVector(temp.vector);
            } else {
                temp.vector = new Vector3f((float) -0.5, 0, 0);
                temp.getCentre().ApplysubVector(temp.vector);
                if (temp.getCentre().getX() <= 0.0f) // current boundary need to pass value to model
                {
                    EnemiesList.remove(temp);
                }
            }

        }

        if (EnemiesList.size() <= 2 && !EnemiesList.isEmpty()) {
            while (EnemiesList.size() < 4) {
                EnemiesList.add(new GameObject("res/Ninja.png", 30, 30,
                        new Point3f(950, ((float) (Math.random() * (760 - 150) + 150)), 0)));
            }
        }
        if (count % 2000 == 0)
            for (int i = 0; i < 2; i++)
                EnemiesList.add(new GameObject("res/Ninja.png", 30, 30,
                        new Point3f(950, ((float) (Math.random() * (760 - 150) + 150)), 0)));
    }

    private void bulletLogic() {
        // TODO Auto-generated method stub
        // move bullets

        for (GameObject temp : Player1BulletList_0) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f(1.5f, 0, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() == 1000) {
                Player1BulletList_0.remove(temp);
            }
        }

        for (GameObject temp : Player1BulletList_1) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f((float) (Math.sqrt(3) / 2), 0.5f, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() == 1000 || temp.getCentre().getY() <= 0f) {
                Player1BulletList_1.remove(temp);
            }
        }

        for (GameObject temp : Player1BulletList_2) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f((float) (Math.sqrt(3) / 2), -0.5f, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() == 1000 || temp.getCentre().getY() >= 760f) {
                Player1BulletList_2.remove(temp);
            }
        }

        for (GameObject temp : Player2BulletList_0) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f(0, (float) -0.5, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getY() >= 800) {
                Player2BulletList_0.remove(temp);
            }
        }

        for (GameObject temp : Player2BulletList_1) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f(-0.5f, (float) -(Math.sqrt(3) / 2), 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getY() >= 800 || temp.getCentre().getX() <= 0) {
                Player2BulletList_1.remove(temp);
            }
        }

        for (GameObject temp : Player2BulletList_2) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f(0.5f, (float) -(Math.sqrt(3) / 2), 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getY() >= 800 || temp.getCentre().getX() >= 1000) {
                Player2BulletList_2.remove(temp);
            }
        }

        for (GameObject temp : supportBulletList) {
            // check to move them

            temp.getCentre().ApplyVector(new Vector3f(0, -2, 0));
            // see if they hit anything

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getY() >= 800 || temp.getCentre().getX() >= 1000) {
                supportBulletList.remove(temp);
            }
        }

    }

    private void EnemybulletLogic() {
        // TODO Auto-generated method stub
        // move bullets

        for (GameObject temp : EnemyBulletList1) {
            temp.getCentre().ApplyVector(new Vector3f(-1, 0, 0));

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() == 0.0f || temp.getCentre().getY() < 140.0f)
                EnemyBulletList1.remove(temp);
        }
        for (GameObject temp : EnemyBulletList2) {
            temp.getCentre().ApplyVector(new Vector3f((float) -(Math.sqrt(2) / 2), (float) (Math.sqrt(2) / 2), 0));

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() == 0.0f || temp.getCentre().getY() <= 0f || temp.getCentre().getY() > 760.0f)
                EnemyBulletList2.remove(temp);
        }
        for (GameObject temp : EnemyBulletList3) {
            temp.getCentre().ApplyVector(new Vector3f(-(float) (Math.sqrt(2) / 2), -(float) (Math.sqrt(2) / 2), 0));

            // see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getX() == 0.0f || temp.getCentre().getY() <= 0f || temp.getCentre().getY() > 760.0f)
                EnemyBulletList3.remove(temp);
        }
        count++;
        if (count % 500 == 0)
            CreateEnemyBullet();

    }

    private void bossLogic() {
        if (getBoss() != null) {
            Boss.ApplyrandombossMove();
            Boss.getCentre().ApplysubVector(Boss.vector);
            if (count % 500 == 0) {
                EnemyBulletList2.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(Boss.getCentre().getX() + 50, Boss.getCentre().getY() + 50, 0.0f)));
                EnemyBulletList1.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(Boss.getCentre().getX() + 50, Boss.getCentre().getY() + 50, 0.0f)));
                EnemyBulletList3.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(Boss.getCentre().getX() + 50, Boss.getCentre().getY() + 50, 0.0f)));
            }

        }
    }

    private void Player1Logic() {

        // smoother animation is possible if we make a target position // done but may
        // try to change things for students

        // check for movement and if you fired a bullet

        if (Controller.getInstance().isKeyAPressed()) {
            Player1.getCentre().ApplysubVector(new Vector3f(-2, 0, 0));
        }

        if (Controller.getInstance().isKeyDPressed()) {
            Player1.getCentre().ApplysubVector(new Vector3f(2, 0, 0));
        }

        if (Controller.getInstance().isKeyWPressed()) {
            Player1.getCentre().ApplysubVector(new Vector3f(0, 2, 0));
        }

        if (Controller.getInstance().isKeySPressed()) {
            Player1.getCentre().ApplysubVector(new Vector3f(0, -2, 0));
        }

        if (Controller.getInstance().isKeySpacePressed()) {
            CreatePlayer1Bullet();
            Controller.getInstance().setKeySpacePressed(false);
        }
    }

    private void supportLogic() {
        if (Controller.getInstance().isKeyKPressed() && numSupport > 0) {
            support = new GameObject("res/support.png", 181, 50, new Point3f(0, 0, 0));
            Controller.getInstance().setKeyKPressed(false);
            numSupport--;
        }
        if (getSupport() != null) {
            support.getCentre().ApplyVector(new Vector3f(3, 0, 0));
            if (count % 20 == 0)
                supportBulletList.add(new GameObject("res/P2bomb.png", 24, 48,
                        new Point3f(support.getCentre().getX(), support.getCentre().getY(), 0.0f)));
        }
    }

    private void Player2Logic() {

        // smoother animation is possible if we make a target position // done but may
        // try to change things for students

        // check for movement and if you fired a bullet

        if (Controller.getInstance().isKeyLeftPressed()) {
            Player2.getCentre().ApplysubVector2(new Vector3f(-2, 0, 0));
        }

        if (Controller.getInstance().isKeyRightPressed()) {
            Player2.getCentre().ApplysubVector2(new Vector3f(2, 0, 0));
        }

        if (Controller.getInstance().isKeySlashPressed()) {
            CreatePlayer2Bullet();
            Controller.getInstance().setKeySlashPressed(false);
        }
    }

    private void CreateEnemyBullet() {
        if (count % 1000 == 0 && stage >= 2)
            for (GameObject temp : EnemiesList) {
                EnemyBulletList2.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(temp.getCentre().getX(), temp.getCentre().getY(), 0.0f)));
                EnemyBulletList1.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(temp.getCentre().getX(), temp.getCentre().getY(), 0.0f)));
            }
        else if (count % 1500 == 0 && stage >= 3)
            for (GameObject temp : EnemiesList) {
                EnemyBulletList2.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(temp.getCentre().getX(), temp.getCentre().getY(), 0.0f)));
                EnemyBulletList1.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(temp.getCentre().getX(), temp.getCentre().getY(), 0.0f)));
                EnemyBulletList3.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(temp.getCentre().getX(), temp.getCentre().getY(), 0.0f)));

            }
        else
            for (GameObject temp : EnemiesList)
                EnemyBulletList1.add(new GameObject("res/Bullet.png", 64, 32,
                        new Point3f(temp.getCentre().getX(), temp.getCentre().getY(), 0.0f)));

    }

    private void CreatePlayer1Bullet() {
        if (SP1 > 0) {
            Player1BulletList_0.add(new GameObject("res/Player1b.png", 48, 25,
                    new Point3f(Player1.getCentre().getX(), Player1.getCentre().getY(), 0.0f)));
            Player1BulletList_1.add(new GameObject("res/Player1b.png", 48, 25,
                    new Point3f(Player1.getCentre().getX(), Player1.getCentre().getY(), 0.0f)));
            Player1BulletList_2.add(new GameObject("res/Player1b.png", 48, 25,
                    new Point3f(Player1.getCentre().getX(), Player1.getCentre().getY(), 0.0f)));
            SP1--;
        } else {
            Player1BulletList_0.add(new GameObject("res/Player1b.png", 48, 25,
                    new Point3f(Player1.getCentre().getX(), Player1.getCentre().getY(), 0.0f)));
        }
    }

    private void CreatePlayer2Bullet() {
        if (SP2 > 0) {
            Player2BulletList_0.add(new GameObject("res/P2bomb.png", 24, 48,
                    new Point3f(Player2.getCentre().getX(), Player2.getCentre().getY(), 0.0f)));
            Player2BulletList_1.add(new GameObject("res/P2bomb.png", 24, 48,
                    new Point3f(Player2.getCentre().getX(), Player2.getCentre().getY(), 0.0f)));
            Player2BulletList_2.add(new GameObject("res/P2bomb.png", 24, 48,
                    new Point3f(Player2.getCentre().getX(), Player2.getCentre().getY(), 0.0f)));
            SP2--;
        } else {
            Player2BulletList_0.add(new GameObject("res/P2bomb.png", 24, 48,
                    new Point3f(Player2.getCentre().getX(), Player2.getCentre().getY(), 0.0f)));

        }

    }

    public GameObject getPlayer1() {
        if (PlayerList.indexOf(Player1) != -1)
            return PlayerList.get(PlayerList.indexOf(Player1));
        else
            return null;
    }

    public GameObject getPlayer2() {
        if (PlayerList.indexOf(Player2) != -1)
            return PlayerList.get(PlayerList.indexOf(Player2));
        else
            return null;
    }

    public GameObject getshieldPlayer1() {
        if (ShieldUsing.indexOf(shieldPlayer1) != -1)
            return ShieldUsing.get(ShieldUsing.indexOf(shieldPlayer1));
        else
            return null;
    }

    public GameObject getshieldPlayer2() {
        if (ShieldUsing.indexOf(shieldPlayer2) != -1)
            return ShieldUsing.get(ShieldUsing.indexOf(shieldPlayer2));
        else
            return null;
    }

    public CopyOnWriteArrayList<GameObject> getEnemies() {
        return EnemiesList;
    }

    public CopyOnWriteArrayList<GameObject> getShield() {
        return Shield;
    }

    public CopyOnWriteArrayList<GameObject> getUpgrade() {
        return upgrade;
    }

    public CopyOnWriteArrayList<GameObject> getSupportPick() {
        return supportPick;
    }

    public CopyOnWriteArrayList<GameObject> getHit() {
        return HitList;
    }

    public CopyOnWriteArrayList<GameObject> getEnemyBulletList1() {
        return EnemyBulletList1;
    }

    public CopyOnWriteArrayList<GameObject> getEnemyBulletList2() {
        return EnemyBulletList2;
    }

    public CopyOnWriteArrayList<GameObject> getEnemyBulletList3() {
        return EnemyBulletList3;
    }

    public CopyOnWriteArrayList<GameObject> getPlayer1Bullets_0() {
        return Player1BulletList_0;
    }

    public CopyOnWriteArrayList<GameObject> getPlayer1Bullets_1() {
        return Player1BulletList_1;
    }

    public CopyOnWriteArrayList<GameObject> getPlayer1Bullets_2() {
        return Player1BulletList_2;
    }

    public CopyOnWriteArrayList<GameObject> getPlayer2Bullets_0() {
        return Player2BulletList_0;
    }

    public CopyOnWriteArrayList<GameObject> getPlayer2Bullets_1() {
        return Player2BulletList_1;
    }

    public CopyOnWriteArrayList<GameObject> getPlayer2Bullets_2() {
        return Player2BulletList_2;
    }

    public int getHealth1() {
        return health1;
    }

    public int getHealth2() {
        return health2;
    }

    public int getBossHP() {
        return bossHP;
    }

    public int getScore() {
        return Score;
    }

    //
    // public void setFlag1(){
    // this.flag = 1;
    // }
    // public void setFlag2(){
    // this.flag = 2;
    // }
    public CopyOnWriteArrayList<GameObject> getPlayerList() {
        return PlayerList;
    }

    public int getStage() {
        return stage;
    }

    public CopyOnWriteArrayList<GameObject> getShieldUsing() {
        return ShieldUsing;
    }

    public boolean getBossDead() {
        return bossDead;
    }

    public GameObject getBoss() {
        if (Boss != null) {
            return Boss;
        } else {
            return null;
        }
    }

    public GameObject getSupport() {
        if (support != null) {
            return support;
        } else {
            return null;
        }
    }

    public CopyOnWriteArrayList<GameObject> getSupportBullet() {
        return supportBulletList;
    }

    public int getnumSupport() {
        return numSupport;
    }
}

/*
 * MODEL OF your GAME world
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWNNNXXXKKK000000000000KKKXXXNNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNXXK0OOkkxddddooooooolllllllloooooooddddxkkOO0KXXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNXK0OkxddooolllllllllllllllllllllllllllllllllllllllloooddxkO0KXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OkdooollllllllooddddxxxkkkOOOOOOOOOOOOOOOkkxxdddooolllllllllllooddxO0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kxdoollllllloddxkO0KKXNNNNWWWWWWMMMMMMMMMMMMMWWWWNNNXXK00Okkxdoollllllllloodxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKOxdooolllllodxkO0KXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXK0OkxdolllllolloodxOKXWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOxdoolllllodxO0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXKOkdolllllllloodxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdolllllooxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kdolllllllllodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xdolllllodk0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMMWN0kdolllllllllodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoollllodxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMWNXKOkkkk0WMMMMMMMMMMMMWNKkdolllloololodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0kdolllllox0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0kxk0KNWWWWNX0OkdoolllooONMMMMMMMMMMMMMMMWXOxolllllllollodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdollllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0xooollloodkOOkdoollllllllloxXWMMMMMMMMMMMMMMMWXkolllllllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0koolllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllox0XWWMMMMMMMMMWNKOdoloooollllllllllllok0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoolllllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxllolllllllllllllllllloollllllolodxO0KXNNNXK0kdoooxO0K0Odolllollllllllox0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllllllloolllllllllllllllllllolllloddddoolloxOKNWMMMWNKOxdolollllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolllolllllllllllllloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxlllolllllloxkxolllllllllllllllllolllllllllllllxKWMWWWNNXXXKKOxoollllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMWXOdollllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllxKNKOxooollolllllllllllllllllllolod0XX0OkxdddoooodoollollllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMN0xollllllllllllllllllllllld0NMMMMMMMMMMMMMMMMMMMMMMMWWNKKNMMMMMMMMMMMW0dlllllllllokXWMWNKkoloolllllllllllllllllllolokkxoolllllllllllllollllllllllllllox0NMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllloONMMMMMMMMMMMMMMMMMMMWNKOxdookNMMMMMMMMMWXkollllllodx0NWMMWWXkolooollllllllllllllllllllooollllllllllllllolllllllllllloooolloxKWMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMWXOdllllllllllllllooollllllllollld0WMMMMMMMMMMMMMMMMWXOxollllloOWMMMMMMMWNkollloodxk0KKXXK0OkdoollllllllllllllllllllllllllllllollllllllloollllllollllllllllllldOXWMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMN0xolllllllllllolllllllllllloodddddONMMMMMMMMMMMMMMMNOdolllllllokNMMMMMMWNkolllloddddddoooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllox0NMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMWXkolllllllllllllllllllodxxkkO0KXNNXXXWMMMMMMMMMMMMMMNkolllllllllod0NMMMMMNOollllloollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllolllllllllllllokXWMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMWKxollllllllllllllllllox0NWWWWWMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllookKNWWNOolollloolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMN0dlllllllllllllllllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloolllollllolloxO0Odllllllllllllllllllllllllllllllllllllllllllllollllllllllllllllllllllllllllllllllllllllllllllld0NWMMMMMMMMMMMMM
 * MMMMMMMMMMMMMXkolllllllllllllllllolllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXOO0KKOdollllllllllooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONWMMMMMMMMMMMM
 * MMMMMMMMMMMWXkollllllllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWMMMMWNKOxoollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMM
 * MMMMMMMMMMWKxollllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMMM
 * MMMMMMMMMWKxollllllllllllodxkkkkkkkO0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKOkO0KK0OkdolllllloolllllllllllloooollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMM
 * MMMMMMMMWKxllllllllllolodOXWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolloooollllllllllllllllloollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMMM
 * MMMMMMMWKxlllllllllollokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxololllllllooolloollllloolloooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMM
 * MMMMMMWXxllllllllooodkKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdloollllllllllololodxxddddk0KK0kxxxdollolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWMMMMMM
 * MMMMMMXkolllllodk0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdllollllllllllllodOXWWNXXNWMMMMWWWNX0xolollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokNMMMMMM
 * MMMMMNOollllodONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dooollllllllllllodOXNWWWWWWMMMMMMMMMWXOddxxddolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONMMMMM
 * MMMMW0dllllodKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKKK0kdlllllllllllloodxxxxkkOOKNWMMMMMMWNNNNNXKOkdooooollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllld0WMMMM
 * MMMWKxllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllollllllllllllllllodOKXWMMMMMMMMMMMMWNXKK0OOkkkxdooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMM
 * MMMNkollllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWXOdlllllolllllllllllloloolllooxKWMMMMMMMMMMMMMMMMMMMMWWWNXKOxoollllllllllllllllllllllllllllllllllllllllllllllllllllllllllolokNMMM
 * MMW0ollllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOOkxdollllllllllllllllllllllllllllox0NWMMMMWWNNXXKKXNWMMMMMMMMMWNKOxolllolllllllllllllllllllllllllllllllllllllllllllllllllllllllo0WMM
 * MMXxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkolllllllllllllllllllllllllllllllllllooxO000OkxdddoooodkKWMMMMMMMMMMWXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWM
 * MWOollllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXkollllllllllllllllllllllllllllllllllllllllllllllllllllllld0WMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloOWM
 * MXxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkollllllllllllllllllllllllllllllllllooollllllllllllllllllold0WMMMMMMWN0dolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXM
 * W0ollllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKkdolllllllllllllllllllllllllllllllllllllllllllllllllolllllllllokKXNWWNKkollllllllloxdollllllllolllllllllllllllllllllllllolllllllllllllolo0W
 * NkllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllodxkkdoolollllllllxKOolllllllllllllllllllllllollooollllllloolllllloolllllkN
 * KxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0doolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkX0dlllllllllllllllllllloollloOKKOkxdddoollllllllllllllxK
 * Oolllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXXkollllooolllllllllllllllloONMMMWNNNXX0xolllllllllolloO
 * kolllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWXkollollllllllllllllllllodKMMMMMMMMMMWKxollllolollolok
 * kllllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloolllllllllxXWWXkolllllllllllllllolllloONMMMMMMMMMMMW0dllllllllllllk
 * xollolld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllllolloONMMN0xoolllllllolllllllloxXWMMMMMMMMMMMMXxollllllloollx
 * dollllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollld0WMMWWXOdollollollllllloxXWMMMMMMMMMMMMMNOollllllokkold
 * olllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNxlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldONMMMMWXxollllolllllox0NWMMMMMMMMMMMMMMNOollllllxXOolo
 * llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONMMMMMXxddxxxxkkO0XWMMMMMMMMMMMMMMMMMNOolllllxKW0olo
 * llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldONWMMMWNXNNWWWMMMMMMMMMMMMMMMMMMMMMMMW0dllollOWW0oll
 * llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxO0KXXXXKKKXNWMMMMMMMMMMMMMMMMMMMMMMMNOdolllkNWOolo
 * ollllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllooooddooloodkKWMMMMMMMMMMMMMMMMMMMMMMWXOolldKNOooo
 * dollllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllo0WMMMMMMMMMMMMMMMMMMMMMMMMXkold0Nkold
 * xollllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllollokNMMMMMMMMMMMMMMMMMMMMMMMMMWOookXXxolx
 * xolllllloONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMN00XW0dlox
 * kollllllloxOKXXNNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOxollllllllllllllllllllllllllllllolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllolllllolo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOollk
 * OolllllllllloodddkKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOkkxddooooollllllllllooodxxdollolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloO
 * KdllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXXXK0OOkkkkkkkkOKXXXNNX0xolllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllllllloox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdlldK
 * NkllllollloolllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWMMMMMMMMMWNOdlllllllllllllllllllllllllllllllllllllllllllllllllllllllollllllllodOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOolokN
 * WOolllllllllllolllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllod0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXxolo0W
 * WXxllllllllllllllllox0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollxXM
 * MWOollllllllllllllooloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdllllllllllllllllllllllllllllllllllllllllllllllllllllloolld0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlloOWM
 * MWXxllolllllllllllllllldOXWWNNK00KXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllllllllllllllllllllllllllod0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollxXWM
 * MMWOollllllllloollllllolodxkxdollodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllllllllllllllllllllllllllllllllllllllllodxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWN0dlllo0WMM
 * MMMXxllolllllllllllllllllllllllllllloox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dooollllllllllllllllllllllllllllllllllllllllllllodOXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKOkxxolllokNMMM
 * MMMW0dlllllllllllllllllllolllllllollollokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdoolllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNOoollllllldKWMMM
 * MMMMNOollllllllllllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKOdolllllllllllllllllllllllllllllllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllloOWMMMM
 * MMMMMXkollllllllllllllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllllllllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllolllllokNMMMMM
 * MMMMMWXxlllllllllllllllllllllllllllllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0ollllllllllllllllllllllllllllllllllllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOollllllllxXWMMMMM
 * MMMMMMWKdlllllllllllllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolllllllllllllllllllllllllllllllllllllloONWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllxKWMMMMMM
 * MMMMMMMW0dlllllllllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllloollllllllllllllllllllllllllllllllloxkOKKXXKKXNMMMMMMMMMMMMMMMMMMMMMMMMNOolllllldKWMMMMMMM
 * MMMMMMMMW0dllllllllllllllllllllllllllllldKMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllloooood0WMMMMMMMMMMMMMMMMMMMMMMMNOollolldKWMMMMMMMM
 * MMMMMMMMMW0dlllllllllllllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllolllllllllllllllllld0WMMMMMMMMMMMMMMMMMMMMMMWKxllllldKWMMMMMMMMM
 * MMMMMMMMMMW0dlllllllllllllllllllllllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllllllllllllllllllllllllllllllllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMWXOdolllldKWMMMMMMMMMM
 * MMMMMMMMMMMWKxollllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllloolllllllolllollllkNMMMMMMMMMMMMMMMMMMMWXOdolllloxKWMMMMMMMMMMM
 * MMMMMMMMMMMMWKxollllllllllllllllllllllllod0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloollllllllllllllllllllllllllllloddollllllllllllld0WMMMMMMMMMMMMMMMWWNKOdolllllokXWMMMMMMMMMMMM
 * MMMMMMMMMMMMMWXkollllllllllllllllllllllllldKMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllollllllllllllllllllllllllllllld0XOollllllllllllkNMMMMMMMMMMMMWNK0OkxollllllloONWMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMNOdlllllllllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dlllllllllllllllllllllllllolllld0NWN0dlllllloodxkKWMMMMMMMMMMMMNOollllllllllld0NMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMWKxolollllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllllllllllllllllllllllldONMMMWKkdoooxOXNNWMMMMMMMMMMMMMNOollllllllllokXWMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMWXOdlllllllllllllllllloONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdllllllllllllllllllllllllllld0NMMMMMWWXXXXNWMMMMMMMMMMMMMMMMW0dlllllllllod0NMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMWKxollolllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllokXWMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMWNOdollllllloolllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllloollllooolllllllllodONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkllllllolox0NMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMWXkollllllolllllox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllollllllllllllllodkKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllodOXWMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMWKxoolllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dollllllllllooddxxk0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdollllldOXWMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMN0xolllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllodk0KXNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKkdollolodkXWMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMNKxoolllllllllodOKNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolldOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoollllodkXWMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKkolllollllllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOx0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOdolllllldOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKOdollllllllllodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOdoollllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xollollollollodxOXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdooollllodk0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKkdooolllllllllooxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOxdollllllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0kdllllllllollllodkOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWXKOkdoolllllloodOKNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdolllllllllllllodxO0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNX0OxdollloolllloxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdoolllllllllllllooxkO0XNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNX0OkxoololllllllooxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kdoolllllllllllloooodxkO0KXNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXK0Okxdoolllllollllloxk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOkdoollllllllloolllllloodxkkO00KXXNNWWWWWWMMMMMMMMMWWWWWWWNNXXK00Okxxdoolllllllllllloooxk0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kxdoollllllllllllllllllllloodddxxxkkOOOOOOOOOOOkkkxxxdddoollllllllllllllllloodxO0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OxdooollllllllllllooolllllllllllllllllllllllllllllllllllllllllllooodkO0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OkxdooollllllllllllllllllllllllllllllllllllllllllloooddxkO0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNXK0OOkkxdddoooooollllllllllllllllooooooddxxkOO0KKXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNNXXXKK00OOOOOOOOOOOOOOOO00KKXXXNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 */

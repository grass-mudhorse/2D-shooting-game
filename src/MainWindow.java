import util.UnitTests;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



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


public class MainWindow {
    public static int diff;
    public static boolean startGame = false;
    public static JLabel BackgroundImageForStartMenu;
    public static Thread1 t = new Thread1();
    public static int flag = 2;
    private static JFrame frame = new JFrame("My First Game");   // Change to the name of your game
    private static Model gameworld = new Model();
    private static Viewer canvas = new Viewer(gameworld);
    private static int TargetFPS = 300;
    JTextArea infor = new JTextArea();
    private KeyListener Controller = new Controller();
    private JMenuBar bar = new JMenuBar();
    private JMenu menu1 = new JMenu("Menu");
    private JMenuItem item1;
    private JMenuItem item2;
    private JMenuItem item3;


    public MainWindow() {
        frame.setSize(1000, 1000);  // you can customise this later and adapt it to change on size.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
        frame.setLayout(null);
        frame.add(canvas);
        canvas.setBounds(0, 0, 1000, 1000);
        canvas.setBackground(new Color(255, 255, 255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen
        canvas.setVisible(false);   // this will become visible after you press the key.


        t.start();

        this.item1 = new JMenuItem("Stop");
        this.item2 = new JMenuItem("Resume");
        this.item3 = new JMenuItem("Exit");
        this.menu1.add(item1);
        this.menu1.add(item2);
        this.menu1.add(item3);
        this.bar.add(menu1);
        frame.setJMenuBar(this.bar);

        item1.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
                java.awt.Event.CTRL_MASK));
        item2.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
                java.awt.Event.CTRL_MASK));
        item3.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,
                java.awt.Event.CTRL_MASK));

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.setSleep(true);
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.setSleep(false);
            }
        });

        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	t.setSleep(true);
                int n = JOptionPane.showConfirmDialog(null, "Are you sure to exit the game?", "Exit game", JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    t.setStop(true);
                    frame.dispose();
                }else {
                	t.setSleep(false);
                }
            }
        });

        JButton startMenuButton1 = new JButton("One Player driving submarine(Easy)");  // start button
        startMenuButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton1.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(Controller);    //adding the controller to the Canvas
                canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
                startGame = true;
                flag = 0;
                diff = 1;
            }
        });
        startMenuButton1.setBounds(200, 500, 250, 40);

        JButton startMenuButton2 = new JButton("One Player driving naval ship(Easy)");  // start button
        startMenuButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton2.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(Controller);    //adding the controller to the Canvas
                canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
                startGame = true;
                flag = 1;
                diff = 1;
            }
        });
        startMenuButton2.setBounds(200, 550, 250, 40);

        JButton startMenuButton3 = new JButton("two Players(Easy)");  // start button
        startMenuButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton3.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(Controller);    //adding the controller to the Canvas
                canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
                startGame = true;
                flag = 2;
                diff = 1;
            }
        });
        startMenuButton3.setBounds(200, 600, 250, 40);

        JButton startMenuButton4 = new JButton("One Player driving submarine(Tricky)");  // start button
        startMenuButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton4.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(Controller);    //adding the controller to the Canvas
                canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
                startGame = true;
                flag = 0;
                diff = 2;
            }
        });
        startMenuButton4.setBounds(500, 500, 250, 40);

        JButton startMenuButton5 = new JButton("One Player driving naval ship(Tricky)");  // start button
        startMenuButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton5.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(Controller);    //adding the controller to the Canvas
                canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
                startGame = true;
                flag = 1;
                diff = 2;
            }
        });
        startMenuButton5.setBounds(500, 550, 250, 40);

        JButton startMenuButton6 = new JButton("Two Player(Tricky)");  // start button
        startMenuButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton6.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(Controller);    //adding the controller to the Canvas
                canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
                startGame = true;
                flag = 2;
                diff = 2;
            }
        });
        startMenuButton6.setBounds(500, 600, 250, 40);

        frame.add(startMenuButton1);
        frame.add(startMenuButton3);
        frame.add(startMenuButton2);
        frame.add(startMenuButton4);
        frame.add(startMenuButton5);
        frame.add(startMenuButton6);
        //loading background image
        File BackroundToLoad = new File("res/test.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {

            BufferedImage myPicture = ImageIO.read(BackroundToLoad);
            BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
            //Icon myIcon = new ImageIcon(new URL("file:///E://SMS2//Game Development//PS test//test.gif"));
            //BackgroundImageForStartMenu = new JLabel(myIcon);
            //BackgroundImageForStartMenu.add(infor);
            BackgroundImageForStartMenu.setBounds(0, 0, 1000, 1000);
            frame.add(BackgroundImageForStartMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }


        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow hello = new MainWindow();  //sets up environment

    }

    //Basic Model-View-Controller pattern
    private static void gameloop() throws InterruptedException {
        // GAMELOOP

        // controller input  will happen on its own thread
        // So no need to call it explicitly
        // model update
        gameworld.gamelogic();
        // view update

        canvas.updateview();

        // Both these calls could be setup as  a thread but we want to simplify the game logic for you.
        //score update
        //frame.setTitle("Score =  "+ gameworld.getScore());


    }

    /**
     *
     * @param filePath
     * @param jb
     * @throws IOException
     */
    /***
     public static void showText(String filePath, JTextArea jb) throws IOException {
     FileInputStream fin;
     try {
     fin = new FileInputStream(filePath);
     InputStreamReader reader = new InputStreamReader(fin);
     BufferedReader buffReader = new BufferedReader(reader);
     String strTmp = "";

     while ((strTmp = buffReader.readLine()) != null) {
     jb.append(strTmp.toString() + "\n");
     try {
     TimeUnit.MILLISECONDS.sleep((long) 800);
     } catch (InterruptedException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }

     }
     buffReader.close();
     } catch (FileNotFoundException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }

     }
     */
    static class Thread1 extends Thread {
        private boolean isSleep = false;
        private boolean isStop = false;

        public void run() {
            while (!isStop) {
                if (isSleep) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it

                    int TimeBetweenFrames = 1000 / TargetFPS;
                    long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames;

                    //wait till next time step
                    while (FrameCheck > System.currentTimeMillis()) {
                    }


                    if (startGame) {
                        try {
                            gameloop();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //UNIT test to see if framerate matches
                    UnitTests.CheckFrameRate(System.currentTimeMillis(), FrameCheck, TargetFPS);


                }
            }
            System.out.println("Thread: " + Thread.currentThread().getName() + " finished.");
            Thread.currentThread().interrupt();
        }

        public void setSleep(boolean sleep) {
            this.isSleep = sleep;
        }

        public void setStop(boolean stop) {
            this.isStop = stop;
        }
    }


}

/*
 * 
 * 

Hand shake agreement 
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,=+++
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,:::::,=+++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,:++++????+??
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,:,,:,:,,,,,,,,,,,,,,,,,,,,++++++?+++++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,=++?+++++++++++??????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++?+++?++?++++++++++?????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++++++++++++++????+++++++???????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,:===+=++++++++++++++++++++?+++????????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,~=~~~======++++++++++++++++++++++++++????????????????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,::::,,,,,,=~.,,,,,,,+===~~~~~~====++++++++++++++++++++++++++++???????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,~~.~??++~.,~~~~~======~=======++++++++++++++++++++++++++????????????????II
:::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,:=+++??=====~~~~~~====================+++++++++++++++++++++?????????????????III
:::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,++~~~=+=~~~~~~==~~~::::~~==+++++++==++++++++++++++++++++++++++?????????????????IIIII
::::::::::::::::::::::::::::::::::::::::::::::::,:,,,:++++==+??+=======~~~~=~::~~===++=+??++++++++++++++++++++++++?????????????????I?IIIIIII
::::::::::::::::::::::::::::::::::::::::::::::::,,:+????+==??+++++?++====~~~~~:~~~++??+=+++++++++?++++++++++??+???????????????I?IIIIIIII7I77
::::::::::::::::::::::::::::::::::::::::::::,,,,+???????++?+?+++???7?++======~~+=====??+???++++++??+?+++???????????????????IIIIIIIIIIIIIII77
:::::::::::::::::::::::::::::::::::::::,,,,,,=??????IIII7???+?+II$Z77??+++?+=+++++=~==?++?+?++?????????????III?II?IIIIIIIIIIIIIIIIIIIIIIIIII
::::::::::::::::::::::::::::::,,,,,,~=======++++???III7$???+++++Z77ZDZI?????I?777I+~~+=7+?II??????????????IIIIIIIIIIIIIIIIIIIIII??=:,,,,,,,,
::::::::,:,:,,,,,,,:::~==+=++++++++++++=+=+++++++???I7$7I?+~~~I$I??++??I78DDDO$7?++==~I+7I7IIIIIIIIIIIIIIIIII777I?=:,,,,,,,,,,,,,,,,,,,,,,,,
++=++=++++++++++++++?+????+??????????+===+++++????I7$$ZZ$I+=~$7I???++++++===~~==7??++==7II?~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+++++++++++++?+++?++????????????IIIII?I+??I???????I7$ZOOZ7+=~7II?+++?II?I?+++=+=~~~7?++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+?+++++????????????????I?I??I??IIIIIIII???II7II??I77$ZO8ZZ?~~7I?+==++?O7II??+??+=====.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
?????????????III?II?????I?????IIIII???????II777IIII7$ZOO7?+~+7I?+=~~+???7NNN7II?+=+=++,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
????????????IIIIIIIIII?IIIIIIIIIIII????II?III7I7777$ZZOO7++=$77I???==+++????7ZDN87I??=~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIII?II??IIIIIIIIIIIIIIIIIIIIIIIIIII???+??II7777II7$$OZZI?+$$$$77IIII?????????++=+.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?+++?IIIII7777$$$$$$7$$$$7IIII7I$IIIIII???I+=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII???????IIIIII77I7777$7$$$II????I??I7Z87IIII?=,,,,,,,,,,,:,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777777777777I7I777777777~,,,,,,,+77IIIIIIIIIII7II7$$$Z$?I????III???II?,,,,,,,,,,::,::::::::,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777$77777777777+::::::::::::::,,,,,,,=7IIIII78ZI?II78$7++D7?7O777II??:,,,:,,,::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$$$$$$$$$$$77=:,:::::::::::::::::::::::::::,,7II$,,8ZZI++$8ZZ?+=ZI==IIII,+7:,,,,:::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$I~::::::::::::::::::::::::::::::::::::::::::II+,,,OOO7?$DOZII$I$I7=77?,,,,,,:::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::+ZZ?,$ZZ$77ZZ$?,,,,,::::::::::::::::::::::::::,::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::I$:::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
                                                                                                                             GlassGiant.com
 * 
 * 
 */

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import util.GameObject;


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
public class Viewer extends JPanel {
	private long CurrentAnimationTime= 0;
	private long existTime = 3;
	
	Model gameworld = new Model();
	 
	public Viewer(Model World) {
		this.gameworld=World;
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		
		this.repaint();
		// TODO Auto-generated method stub
		
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		CurrentAnimationTime++; // runs animation time step 
		
		
		//Draw Player1 Game Object
		//Draw background 
		try {
			drawBackground(g);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(gameworld.getPlayer1() != null){
			int x1 = (int) gameworld.getPlayer1().getCentre().getX();
			int y1 = (int) gameworld.getPlayer1().getCentre().getY();
			int width1 = (int) gameworld.getPlayer1().getWidth();
			int height1 = (int) gameworld.getPlayer1().getHeight();
			String texture1 = gameworld.getPlayer1().getTexture();
			drawPlayer1(x1,y1,width1,height1,texture1,g);
		}

		if(gameworld.getPlayer2() != null){
			int x2 = (int) gameworld.getPlayer2().getCentre().getX();
			int y2 = (int) gameworld.getPlayer2().getCentre().getY();
			int width2 = (int) gameworld.getPlayer2().getWidth();
			int height2 = (int) gameworld.getPlayer2().getHeight();
			String texture2 = gameworld.getPlayer2().getTexture();
			drawPlayer2(x2,y2,width2,height2,texture2,g);
		}


		if(!gameworld.getPlayerList().isEmpty()){
			if(gameworld.getScore()>=0 && gameworld.getScore() <10)
				gameworld.setStage(1);
			else if(gameworld.getScore()>=10 && gameworld.getScore() <20)
				gameworld.setStage(2);
			else if(gameworld.getScore()>=20 && gameworld.getScore() <30)
				gameworld.setStage(3);
			else if(gameworld.getScore()>=30 && gameworld.getScore() <40)
				gameworld.setStage(4);
			else{
				gameworld.setStage(5);
				gameworld.getEnemies().forEach((temp) ->
				{ gameworld.getEnemies().remove(temp);});
				gameworld.getEnemyBulletList1().forEach((temp) ->
				{ gameworld.getEnemyBulletList1().remove(temp);});
				gameworld.getEnemyBulletList2().forEach((temp) ->
				{ gameworld.getEnemyBulletList2().remove(temp);});
				gameworld.getEnemyBulletList3().forEach((temp) ->
				{ gameworld.getEnemyBulletList3().remove(temp);});

				drawending(g);
			}
		}else{

			gameworld.getEnemies().forEach((temp) ->
			{ gameworld.getEnemies().remove(temp);});
			drawending(g);
//			MainWindow.t.setSleep(true);
		}
		//Draw Player1
		/**
		 * gameworld.getPlayerList().forEach((temp) ->
		 *                {
		 * 			drawPlayer((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		 *        });
		 */

		//drawPlayer1(x, y, width, height, texture,g);


		  
		//Draw Bullets 
		// change back
		gameworld.getHit().forEach((temp) ->
		{
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		gameworld.getEnemyBulletList1().forEach((temp) ->
		{
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});
		gameworld.getEnemyBulletList2().forEach((temp) ->
		{
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});
		gameworld.getEnemyBulletList3().forEach((temp) ->
		{
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		gameworld.getPlayer1Bullets().forEach((temp) ->
		{ 
			drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 
		});
		gameworld.getPlayer2Bullets().forEach((temp) ->
		{
			drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
			drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 
		 
	    }); 
	}
	
	private void drawEnemies(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31  
			int currentPositionInAnimation= ((int) (CurrentAnimationTime%4 )*32); //slows down animation so every 10 frames we get another frame so every 100ms 
			//add a word in the image;
	            g.drawImage(myImage, x,y, x+width, y+width, currentPositionInAnimation  , 0, currentPositionInAnimation+31, 32, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	private void drawBackground(Graphics g) throws IOException {

		//super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		Point2D start = new Point2D.Float(322,0);
		Point2D end   = new Point2D.Float(322,150);
		float[]   dist1   = {0.0f,1.0f};
		Color[]   colors1 = {new Color(72,209,187),Color.white};
		LinearGradientPaint p1 =
				new LinearGradientPaint(start, end, dist1, colors1);

		g2.setPaint(p1);
		g2.fillRect(0, 0,this.getWidth(), 150);


		Point2D pStart = new Point2D.Float(322,0);
		Point2D pEnd   = new Point2D.Float(322,458);
		float[]   dist   = {0.0f,0.3f,1.0f};
		Color[]   colors = {new Color(123,104,221),new Color(65,105,209),Color.blue};
		LinearGradientPaint p =
				new LinearGradientPaint(pStart, pEnd, dist, colors);
		g2.setPaint(p);
		g2.fillRect(0, 150,this.getWidth(), this.getHeight());
		this.showScore(g2);

/**
 * File TextureToLoad = new File("res/sea.jpg");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
 * 		try {
 * 			Image myImage = ImageIO.read(TextureToLoad);
 * 			 g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 10, 1000, null);
 *
 *                } catch (IOException e) {
 * 			// TODO Auto-generated catch block
 * 			e.printStackTrace();
 *        }
 */

	}

	private void showScore(Graphics2D g2) {
		g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g2.setColor(Color.red);
		BasicGraphicsUtils.drawString(g2,"Score: " + gameworld.getScore(),100,20,30);
		BasicGraphicsUtils.drawString(g2,"Stage: " + gameworld.getStage(),100,20,50);
	}

	private void showfinalScore(Graphics2D g2) {
		g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g2.setColor(Color.red);
		BasicGraphicsUtils.drawString(g2,"Score: " + gameworld.getScore(),100,500,500);
		BasicGraphicsUtils.drawString(g2,"Stage: " + gameworld.getStage(),100,500,550);
	}

	private void drawending(Graphics g)
	{
		File TextureToLoad = new File("res/sea.jpg");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
  		try {
  			Image myImage = ImageIO.read(TextureToLoad);
  			 g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 1000, 1000, null);
  			 showfinalScore((Graphics2D)g);
                 } catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
        }
	}

	private void drawBullet(int x, int y, int width, int height, String texture,Graphics g)
	{
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			//64 by 128 
			 g.drawImage(myImage, x,y, x+width, y+width, 0 , 0, 63, 127, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBumb(int x, int y, int width, int height, String texture,Graphics g)
	{
			File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
			try {
				Image myImage = ImageIO.read(TextureToLoad);
				//64 by 128
				g.drawImage(myImage, x,y, x+width, y+width, 0 , 0, 63, 127, null);
				existTime--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void drawPlayer1(int x, int y, int width, int height, String texture,Graphics g) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31
			int currentPositionInAnimation = (int) (CurrentAnimationTime % 4) * 32; //slows down animation so every 10 frames we get another frame so every 100ms
			g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0, currentPositionInAnimation + 31, 32, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawPlayer2(int x, int y, int width, int height, String texture,Graphics g) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31  
			int currentPositionInAnimation= (int)(CurrentAnimationTime%4)*115; //slows down animation so every 10 frames we get another frame so every 100ms
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 0, currentPositionInAnimation+114, 32, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
		//Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time 
		// Bullets from https://opengameart.org/forumtopic/tatermands-art 
		// background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images
		
	}
		 
	 

}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */

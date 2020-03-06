import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

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
	Model gameworld = new Model();
	private long CurrentAnimationTime = 0;
	private long existTime = 3;

	public Viewer(Model World) {
		this.gameworld = World;
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

		// Draw Player1 Game Object
		// Draw background
		try {
			drawBackground(g);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (gameworld.getPlayer1() != null) {
			int x1 = (int) gameworld.getPlayer1().getCentre().getX();
			int y1 = (int) gameworld.getPlayer1().getCentre().getY();
			int width1 = gameworld.getPlayer1().getWidth();
			int height1 = gameworld.getPlayer1().getHeight();
			String texture1 = gameworld.getPlayer1().getTexture();
			int health1 = gameworld.getHealth1();
			drawPlayer1(x1, y1, width1, height1, texture1, health1, g);
		}

		if (gameworld.getPlayer2() != null) {
			int x2 = (int) gameworld.getPlayer2().getCentre().getX();
			int y2 = (int) gameworld.getPlayer2().getCentre().getY();
			int width2 = gameworld.getPlayer2().getWidth();
			int height2 = gameworld.getPlayer2().getHeight();
			String texture2 = gameworld.getPlayer2().getTexture();
			int health2 = gameworld.getHealth2();
			drawPlayer2(x2, y2, width2, height2, texture2, health2, g);
		}

		if (gameworld.getshieldPlayer1() != null) {
			int x2 = (int) gameworld.getshieldPlayer1().getCentre().getX();
			int y2 = (int) gameworld.getshieldPlayer1().getCentre().getY();
			int width2 = gameworld.getshieldPlayer1().getWidth();
			int height2 = gameworld.getshieldPlayer1().getHeight();
			String texture2 = gameworld.getshieldPlayer1().getTexture();
			drawShieldUsing(x2, y2, width2, height2, texture2, g);
		}

		if (gameworld.getshieldPlayer2() != null) {
			int x2 = (int) gameworld.getshieldPlayer2().getCentre().getX();
			int y2 = (int) gameworld.getshieldPlayer2().getCentre().getY();
			int width2 = gameworld.getshieldPlayer2().getWidth();
			int height2 = gameworld.getshieldPlayer2().getHeight();
			String texture2 = gameworld.getshieldPlayer2().getTexture();
			drawShieldUsing(x2, y2, width2, height2, texture2, g);
		}

		if (gameworld.getBoss() != null && !gameworld.getBossDead()) {
			int x2 = (int) gameworld.getBoss().getCentre().getX();
			int y2 = (int) gameworld.getBoss().getCentre().getY();
			int width2 = gameworld.getBoss().getWidth();
			int height2 = gameworld.getBoss().getHeight();
			int health2 = gameworld.getBossHP();
			String texture2 = gameworld.getBoss().getTexture();
			drawBoss(x2, y2, width2, height2, texture2, health2, g);
		}

		if (gameworld.getSupport() != null) {
			int x2 = (int) gameworld.getSupport().getCentre().getX();
			int y2 = (int) gameworld.getSupport().getCentre().getY();
			int width2 = gameworld.getSupport().getWidth();
			int height2 = gameworld.getSupport().getHeight();
			String texture2 = gameworld.getSupport().getTexture();
			drawSupport(x2, y2, width2, height2, texture2, g);
		}

		if (!gameworld.getPlayerList().isEmpty()) {
			if (gameworld.getScore() >= 0 && gameworld.getScore() < 10)
				gameworld.setStage(1);
			else if (gameworld.getScore() >= 10 && gameworld.getScore() < 30)
				gameworld.setStage(2);
			else if (gameworld.getScore() >= 20 && gameworld.getScore() < 50)
				gameworld.setStage(3);
			else if (gameworld.getScore() >= 30 && gameworld.getScore() < 70)
				gameworld.setStage(4);
			else {
				gameworld.setStage(5);
				if (gameworld.getBossDead()) {
					gameworld.getEnemies().forEach((temp) -> {
						gameworld.getEnemies().remove(temp);
					});
					gameworld.getEnemyBulletList1().forEach((temp) -> {
						gameworld.getEnemyBulletList1().remove(temp);
					});
					gameworld.getEnemyBulletList2().forEach((temp) -> {
						gameworld.getEnemyBulletList2().remove(temp);
					});
					gameworld.getEnemyBulletList3().forEach((temp) -> {
						gameworld.getEnemyBulletList3().remove(temp);
					});

					gameworld.getShieldUsing().forEach((temp) -> {
						gameworld.getShieldUsing().remove(temp);
					});

					gameworld.getShield().forEach((temp) -> {
						gameworld.getShield().remove(temp);
					});

					gameworld.getUpgrade().forEach((temp) -> {
						gameworld.getUpgrade().remove(temp);
					});

					gameworld.getSupportPick().forEach((temp) -> {
						gameworld.getSupportPick().remove(temp);
					});

					drawHending(g);
				}

			}
		} else {

			gameworld.getEnemies().forEach((temp) -> {
				gameworld.getEnemies().remove(temp);
			});
			gameworld.getEnemyBulletList1().forEach((temp) -> {
				gameworld.getEnemyBulletList1().remove(temp);
			});
			gameworld.getEnemyBulletList2().forEach((temp) -> {
				gameworld.getEnemyBulletList2().remove(temp);
			});
			gameworld.getEnemyBulletList3().forEach((temp) -> {
				gameworld.getEnemyBulletList3().remove(temp);
			});

			gameworld.getShieldUsing().forEach((temp) -> {
				gameworld.getShieldUsing().remove(temp);
			});

			gameworld.getShield().forEach((temp) -> {
				gameworld.getShield().remove(temp);
			});

			gameworld.getUpgrade().forEach((temp) -> {
				gameworld.getUpgrade().remove(temp);
			});

			gameworld.getSupportPick().forEach((temp) -> {
				gameworld.getSupportPick().remove(temp);
			});
			drawBending(g);
			// MainWindow.t.setSleep(true);
		}
		// Draw Player1
		/**
		 * gameworld.getPlayerList().forEach((temp) -> { drawPlayer((int)
		 * temp.getCentre().getX(), (int) temp.getCentre().getY(), (int)
		 * temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g); });
		 */

		// drawPlayer1(x, y, width, height, texture,g);

		// Draw Bullets
		// change back
		gameworld.getSupportBullet().forEach((temp) -> {
			drawSupportBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);
		});

		gameworld.getSupportPick().forEach((temp) -> {
			drawSupportPick((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);
		});

		gameworld.getHit().forEach((temp) -> {
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(), temp.getHeight(),
					temp.getTexture(), g);
		});

		gameworld.getEnemyBulletList1().forEach((temp) -> {
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(), temp.getHeight(),
					temp.getTexture(), g);
		});
		gameworld.getEnemyBulletList2().forEach((temp) -> {
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(), temp.getHeight(),
					temp.getTexture(), g);
		});
		gameworld.getEnemyBulletList3().forEach((temp) -> {
			drawBumb((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(), temp.getHeight(),
					temp.getTexture(), g);
		});

		gameworld.getPlayer1Bullets_0().forEach((temp) -> {

			drawPlayer1Bullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);
			// drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
			// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		gameworld.getPlayer1Bullets_1().forEach((temp) -> {

			drawPlayer1Bullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);
			// drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
			// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		gameworld.getPlayer1Bullets_2().forEach((temp) -> {

			drawPlayer1Bullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);
			// drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
			// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});
		gameworld.getPlayer2Bullets_0().forEach((temp) -> {
			drawPlayer2Bullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);

			// drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
			// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});
		gameworld.getPlayer2Bullets_1().forEach((temp) -> {
			drawPlayer2Bullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);

			// drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
			// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		gameworld.getPlayer2Bullets_2().forEach((temp) -> {
			drawPlayer2Bullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);

			// drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
			// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		});

		// Draw Enemies
		gameworld.getEnemies().forEach((temp) -> {
			drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(), temp.getHeight(),
					temp.getTexture(), temp.health, g);

		});

		// Draw shield movement
		gameworld.getShield().forEach((temp) -> {
			drawShieldMove((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);

		});

		gameworld.getUpgrade().forEach((temp) -> {
			drawUpgradeMove((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp.getWidth(),
					temp.getHeight(), temp.getTexture(), g);

		});

		//
		// gameworld.getShieldUsing().forEach((temp) ->
		// {
		// drawShieldUsing((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
		// (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		//
		// });
	}

	private void drawSupportPick(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 30, 38, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawSupport(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 181, 50, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBending(Graphics g) {
		File TextureToLoad = new File("res/BE.png"); // should work okay on OSX and Linux but check if you have issues
														// depending your eclipse install or if your running this
														// without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0, 0, 1000, 1000, 0, 0, 1000, 1000, null);
			showfinalScore((Graphics2D) g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBoss(int x, int y, int width, int height, String texture, int hp, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 190, 120, null);
			g.setColor(new Color(200, 0, 0));
			g.fillRect(x, y - 10, hp * 2, 8);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawUpgradeMove(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 30, 38, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawShieldUsing(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			g.drawImage(myImage, x + 15, y - 15, x + width + 15, y + height - 15, 0, 0, 80, 80, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawShieldMove(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 30, 38, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawEnemies(int x, int y, int width, int height, String texture, int health, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			int currentPositionInAnimation = ((int) (CurrentAnimationTime % 4) * 32); // slows down animation so every
																						// 10 frames we get another
			int tt = 0;																			// frame so every 100ms
			// add a word in the image;
			g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
					currentPositionInAnimation + 31, 32, null);
			g.setColor(new Color(200, 0, 0));
			if(health == 1)
				tt = 30;
			else
				tt = 15;
			g.fillRect(x, y - 10, tt, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void drawBackground(Graphics g) throws IOException {

		// super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Point2D start = new Point2D.Float(322, 0);
		Point2D end = new Point2D.Float(322, 150);
		float[] dist1 = { 0.0f, 1.0f };
		Color[] colors1 = { new Color(72, 209, 187), Color.white };
		LinearGradientPaint p1 = new LinearGradientPaint(start, end, dist1, colors1);

		g2.setPaint(p1);
		g2.fillRect(0, 0, this.getWidth(), 150);

		Point2D pStart = new Point2D.Float(322, 0);
		Point2D pEnd = new Point2D.Float(322, 458);
		float[] dist = { 0.0f, 0.3f, 1.0f };
		Color[] colors = { new Color(61, 189, 221), new Color(11, 135, 150), new Color(28, 130, 221) };
		LinearGradientPaint p = new LinearGradientPaint(pStart, pEnd, dist, colors);
		g2.setPaint(p);
		g2.fillRect(0, 150, this.getWidth(), this.getHeight());
		this.showScore(g2);

		/**
		 * File TextureToLoad = new File("res/sea.jpg"); //should work okay on OSX and
		 * Linux but check if you have issues depending your eclipse install or if your
		 * running this without an IDE try { Image myImage =
		 * ImageIO.read(TextureToLoad); g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 10,
		 * 1000, null);
		 *
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	private void showScore(Graphics2D g2) {
		g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g2.setColor(Color.red);
		BasicGraphicsUtils.drawString(g2, "Score: " + gameworld.getScore(), 100, 20, 30);
		BasicGraphicsUtils.drawString(g2, "Stage: " + gameworld.getStage(), 100, 20, 50);
		BasicGraphicsUtils.drawString(g2, "Support: " + gameworld.getnumSupport(), 100, 20, 70);
	}

	private void showfinalScore(Graphics2D g2) {
		g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g2.setColor(Color.red);
		BasicGraphicsUtils.drawString(g2, "Score: " + gameworld.getScore(), 100, 500, 500);
		BasicGraphicsUtils.drawString(g2, "Stage: " + gameworld.getStage(), 100, 500, 550);
	}

	private void drawHending(Graphics g) {
		File TextureToLoad = new File("res/HE.png"); // should work okay on OSX and Linux but check if you have issues
														// depending your eclipse install or if your running this
														// without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0, 0, 1000, 1000, 0, 0, 1000, 1000, null);
			showfinalScore((Graphics2D) g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBullet(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			g.drawImage(myImage, x, y, x + width, y + width, 0, 0, 63, 127, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawPlayer1Bullet(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 48, 25, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawPlayer2Bullet(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			int currentPositionInAnimation = (int) (CurrentAnimationTime % 4) * 24; // slows down animation so every 10
																					// frames we get another frame so
																					// every 100ms
			g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
					currentPositionInAnimation + 23, 48, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawSupportBullet(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			int currentPositionInAnimation = (int) (CurrentAnimationTime % 4) * 24; // slows down animation so every 10
																					// frames we get another frame so
																					// every 100ms
			g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
					currentPositionInAnimation + 23, 48, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBumb(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// 64 by 128
			g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 63, 127, null);
			existTime--;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void drawPlayer1(int x, int y, int width, int height, String texture, int health, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			int currentPositionInAnimation = (int) (CurrentAnimationTime % 4) * 91; // slows down animation so every 10
																					// frames we get another frame so
																					// every 100ms
			g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
					currentPositionInAnimation + 90, 50, null);
			g.setColor(new Color(200, 0, 0));
			health = (health > 10) ? 10 : health;
			g.fillRect(x, y - 10, health * 9, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawPlayer2(int x, int y, int width, int height, String texture, int health, Graphics g) {
		File TextureToLoad = new File(texture); // should work okay on OSX and Linux but check if you have issues
												// depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			// The spirte is 32x32 pixel wide and 4 of them are placed together so we need
			// to grab a different one each time
			// remember your training :-) computer science everything starts at 0 so 32
			// pixels gets us to 31
			int currentPositionInAnimation = (int) (CurrentAnimationTime % 4) * 115; // slows down animation so every 10
																						// frames we get another frame
																						// so every 100ms
			g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
					currentPositionInAnimation + 114, 32, null);
			g.setColor(new Color(200, 0, 0));
			health = (health > 10) ? 10 : health;
			g.fillRect(x, y - 10, health * 12, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
		// Lighnting Png from https://opengameart.org/content/animated-spaceships its
		// 32x32 thats why I know to increament by 32 each time
		// Bullets from https://opengameart.org/forumtopic/tatermands-art
		// background image from
		// https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images

	}

}

/*
 * 
 * 
 * VIEWER HMD into the world
 * 
 * . . . .. .........~++++.. . . . . ....,++??+++?+??+++?++?7ZZ7.. . . . .
 * .+?+???++++???D7I????Z8Z8N8MD7I?=+O$.. ..
 * ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O . . ..
 * ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.
 * ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...
 * ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......
 * ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ...... ..
 * ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........
 * ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+....... .
 * 7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,...... .
 * ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....
 * .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....
 * $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:.....
 * ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
 * .......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8...
 * .. .
 * ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8.....
 * .
 * ........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8...
 * ...
 * ......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O...
 * ...
 * ....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,...
 * . .
 * ...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8.....
 * ...
 * ..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,......
 * ...
 * ..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,........
 * ..
 * ..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,.........
 * ...
 * ..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........
 * ..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,...... .
 * ..
 * ...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........ .
 * ....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..
 * .....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........
 * ......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......
 * .......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........
 * .........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............
 * .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............
 * ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................
 * .................,,,,,,,,,,,,,,,,.......................
 * .................................................
 * .................................... .................... .
 * 
 * 
 * GlassGiant.com
 */

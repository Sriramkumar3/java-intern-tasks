package com.snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
	
	static final int SCREEN_WIDTHX=600,SCREEN_HEIGHTY=600,SIZE=20,DELAY=100;
	
	final int x[]=new int[SCREEN_HEIGHTY*SCREEN_WIDTHX];
	final int y[]=new int[SCREEN_HEIGHTY*SCREEN_WIDTHX];
	
	int snakeBody=4,foodEatten,foodX,score,foodY,currentDirectionX=1,currentDirectionY=0;
	boolean gameOn=false;
	Random random;
	Timer timer;
	
	public SnakeGame() {
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_HEIGHTY,SCREEN_WIDTHX));
		this.setBackground(Color.LIGHT_GRAY);
		this.setFocusable(true);
		this.addKeyListener(this);
		startGame();
	}
	
	public void startGame() {
		createFood();
		gameOn=true;
		x[0]=SCREEN_WIDTHX/2;
		y[0]=SCREEN_HEIGHTY/2;
		timer=new Timer(DELAY,this);
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(gameOn) {
//		for(int i=0;i<SCREEN_HEIGHTY;i++) {
//			g.drawLine(i*SIZE, 0, i*SIZE,SCREEN_HEIGHTY);
//			g.drawLine(0, i*SIZE,SCREEN_WIDTHX, i*SIZE);
//		}
		g.setColor(Color.green);
		g.fillOval(foodX, foodY, SIZE, SIZE);
		
		for(int i=0;i<snakeBody;i++) {
			g.setColor(Color.blue);
			g.fillRect(x[i],y[i], SIZE, SIZE);
		}
		}else
			gameOver(g);
	}
	
	public void move() {
		for(int i=snakeBody;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		x[0]=x[0]+currentDirectionX*SIZE;
		y[0]=y[0]+currentDirectionY*SIZE;
	}
	
	public void createFood() {
		foodX=random.nextInt(((int)SCREEN_WIDTHX/SIZE))*SIZE;
		foodY=random.nextInt(((int)SCREEN_HEIGHTY/SIZE))*SIZE;
	}
	
	public void checkFood() {
		if(x[0]== foodX && y[0]==foodY) {
			createFood();
			score++;
			snakeBody++;
		}
	}
	
	public void checkCollied() {
		for(int i=snakeBody;i>0;i--) {
			if (x[0]==x[i] && y[0]==y[i]) {
				gameOn=false;
			}
			if(x[0] < 0 || x[0] >= SCREEN_WIDTHX ||y[0]<0 || y[0] >= SCREEN_HEIGHTY) {
				gameOn=false;
			}
			if(!gameOn) 
				timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Serif",Font.BOLD, 75));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score :"+score,SCREEN_HEIGHTY/5,SCREEN_WIDTHX/3);
		g.drawString("Game Over",SCREEN_HEIGHTY/5,SCREEN_WIDTHX/2);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT: {
			if(currentDirectionX !=1) { //stop moving right
			currentDirectionX=-1;	//moving left
			currentDirectionY=0;	//stop up,down move
			}
			break;
		}
		case KeyEvent.VK_RIGHT:{
			if(currentDirectionX !=-1) {
			currentDirectionX=1;
			currentDirectionY=0;
			}
			break;
		}
		case KeyEvent.VK_UP:{
			if(currentDirectionY !=1) {
			currentDirectionX=0;
			currentDirectionY=-1;
			}
			break;
		}
		case KeyEvent.VK_DOWN:{
			if(currentDirectionY !=-1) {
			currentDirectionX=0;
			currentDirectionY=1;
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + key);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(gameOn) {
			move();
			checkFood();
			checkCollied();
		}
		repaint();
	}

}

package com.wichi.minitennis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	static int WIDTH = 300;
	Ball ball = new Ball(this);
	Racquet racquet = new Racquet(this);
	int speed = 1;
	static boolean gamePlay = false;
	String user;

	private int getScore() {
		return speed - 1;
	}
	
	//----------------------
	
	
	//-----------------------

	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				racquet.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				racquet.keyPressed(e);
			}
		});
		setFocusable(true);
		//Sound.BACK.loop();
	}

	
	
	private void move() {
		ball.move();
		racquet.move();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		ball.paint(g2d);
		racquet.paint(g2d);

		g2d.setColor(Color.gray);
		g2d.setFont(new Font("Verdana", Font.BOLD, 30));
		g2d.drawString(String.valueOf(getScore()), 10, 30);
		
		g2d.setFont(new Font("Verdana", Font.BOLD, 20));
		g2d.drawString(setName(), WIDTH - 130, 30);
	}

	public void gameOver() {
		Sound.BACK.stop();
		Sound.GAMEOVER.play();
		//JOptionPane.showMessageDialog(this, "your score is: " + getScore(),
				//"Game Over", JOptionPane.YES_NO_OPTION);
		gamePlay = false;
		//System.exit(ABORT);
		
		menuGameOver();
	}
	
	//--------------------------------------------
	/****Opciones de game over**********/
	 private void menuGameOver() {
		 String options[] = {"Replay", "Exit"};
		 
		 int replay = JOptionPane.showOptionDialog(this, "You score is: " + getScore() 
		 				+ "\nDo you want to play again?", "Game over", 0, 0, null, options, this);
		 
		 if (replay == JOptionPane.YES_OPTION) {
			 gamePlay = true;
			 gameReload();
		 }
		 //else if (replay == JOptionPane.CANCEL_OPTION) {
			 //System.exit(0);
		 //}
		 else if (replay == JOptionPane.NO_OPTION) {
			 System.exit(0);
		 }
	 }
	
	 private void gameReload() {
		 speed = 1;
		 gamePlay = true;
		 ball.reload();
		 racquet.reload();
	 }
	//-----------------------------------------
	 
	//---------------------------
	/*Caja de dialogo donde se ingresara el nombre del usuario*/
	public void menu() {
		//Solicita nombre de usuario
		user = JOptionPane.showInputDialog(null, "Ingrese nombre");
		//Si esta vacio te pedira que ingreses un nombre
		while (!noName(user)) {
			user = JOptionPane.showInputDialog(null, "No ingreso un nombre, porfavor ingrese un nombre");			
		}
		//if ( user == Integer.toString(JOptionPane.CLOSED_OPTION) ) {
			//System.exit(0);
		//}
		
		//System.out.println(JOptionPane.CLOSED_OPTION);
		//En caso de que el nombre de usuario no este vacio
		//se ejecutara el juego.
		gamePlay = true;
		Sound.BACK.loop();
	}
	
	private static boolean noName(String n) {
		if ("".equals(n)) {
			return false;
		}
		else {return true;}
	}
	
	private String setName() {
		if(user == null) {
			return user = "No name";
		}else {
			return user;
		}
	}
	
	//--------------------------

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Mini Tennis");
		Game game = new Game();
		frame.add(game);
		frame.setSize(WIDTH, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		game.menu();
		
		while (gamePlay) {
			game.move();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
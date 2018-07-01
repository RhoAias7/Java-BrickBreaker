/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ayofo
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    //Declare variables
    private boolean play = false;//Once the games starts it will not play by itself

    private int score = 0;//Starting score

    private int totalBricks = 21;//Number of bricks
    
    private Timer time;//the time of the ball 

    private int delay = 8;//speed of the ball

    
    private int playerX = 350;//starting position of the player on the x

    
    private int ballPosX = 120;//Starting position of the ball on  the x axis
    private int ballPosY = 350;//Starting position of the ball on  the y axis
    private int ballXdir = -1; //Direction of the ball
    private int ballYdir = -2;
    
    private BrickGenerator brick;//brick generator class

    //Constructor    
    public Gameplay(){
        brick = new BrickGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }
    
    public void paint(Graphics draw){
        //backGround
        draw.setColor(Color.black);
        draw.fillRect(1, 1, 692, 592);
        
        //bricks
        brick.draw((Graphics2D)draw);
        
        //borders
        draw.setColor(Color.red);
        draw.fillRect(0, 0, 3, 592);
        draw.fillRect(0, 0, 692, 3);
        draw.fillRect(691, 0, 3, 582);
        
        //the paddle
        draw.setColor(Color.green);
        draw.fillRect(playerX, 550, 100, 8);

        //ball
        draw.setColor(Color.yellow);
        draw.fillOval(ballPosX, ballPosY, 20, 20);
        
        draw.dispose();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }        
        }
    }
    
    public void moveRight()
    {
        play = true;
        //Move 20 pixels to the right
        playerX += 20;
    }
    
    public void moveLeft()
    {
        play = true;
        //Move 20 pixels left
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        //move the ball once an arrow key has been pressed
        if(play){
            //detect collision with the paddle by frawing a box around the ball
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            
            A: for(int i = 0; i < brick.bricks.length; i++){
                for(int j = 0; j < brick.bricks[0].length; j++){
                    if(brick.bricks[i][j] > 0){
                       int brickX = j * brick.brickWidth + 80;                                                                                                                                                                                                                                                                                                         
                       int brickY = i * brick.brickHeight + 50;      
                       int brickWidth = brick.brickWidth;
                       int brickHeight = brick.brickHeight;
                       
                       Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                       Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                       Rectangle brickRect = rect;
                       
                       if(ballRect.intersects(brickRect)){
                           brick.setBrickValue(0, i, j);
                           totalBricks--;
                           score += 5;
                           
                           if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                               ballXdir = -ballXdir;
                           }else{
                               ballYdir = -ballYdir;
                           }
                           break A;
                       }
                    }
                }
            }
            
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            //left border
            if(ballPosX < 0){
                ballXdir = -ballXdir;
            }            
            //top borrder
            if(ballPosY < 0){
                ballYdir = -ballYdir;
            }            
            //right border
            if(ballPosX > 670){
                ballXdir = -ballXdir;
            }
        }
        
        //this is to draw the paddle again once it has been moved
        repaint();    
    }
    
}

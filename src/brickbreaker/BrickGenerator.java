/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ayofo
 */
public class BrickGenerator {
    
    //declare a 2d array that will contain all the bricks
    public int bricks[][];
    public int brickWidth;
    public int brickHeight;
    
    //Constructor
    public BrickGenerator(int row, int col){
        bricks = new int[row][col];
        for(int i = 0; i < bricks.length; i++){
            for(int j = 0; j < bricks[0].length; j++){
                bricks[i][j] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    
    //draw method
    public void draw(Graphics2D draw){
       for(int i = 0; i < bricks.length; i++){
            for(int j = 0; j < bricks[0].length; j++){
                if(bricks[i][j] > 0){
                    draw.setColor(Color.white);
                    draw.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                    draw.setStroke(new BasicStroke(3));
                    draw.setColor(Color.black);
                    draw.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
    
    //method to destroy bricks
    public void setBrickValue(int value, int row, int col){
        bricks[row][col] = value; 
    }
}

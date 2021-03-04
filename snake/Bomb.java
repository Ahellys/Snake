package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class Bomb extends Apple {
    private static final String BOMB = "\ud83d\udca3";

    public Bomb(int x, int y) {
        super(x, y);
    }

    public void draw(Game game){
        game.setCellValueEx(x,y, Color.NONE,BOMB,Color.RED,75);
    }

    public boolean checkCollision(GameObject g){

        if (g.x==this.x && g.y==this.y)return true;

        return false;
    }
}

package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public int x;
    public int y;
    private static final String HEAD_SIGN="\uD83D\uDC7E";
    private static final String BODY_SIGN="\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive=true;
    private Direction direction=Direction.LEFT;

    public Snake(int x,int y){
        this.x=x;
        this.y=y;
        snakeParts.add(new GameObject(x,y));
        snakeParts.add(new GameObject(x+1,y));
        snakeParts.add(new GameObject(x+2,y));
    }

    public void draw (Game game){
        if (this.isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y,Color.NONE,HEAD_SIGN,Color.BLACK,75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,Color.NONE, BODY_SIGN,Color.BLACK,75);
            }
        }
        else {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y,Color.NONE,HEAD_SIGN,Color.RED,75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,Color.NONE, BODY_SIGN,Color.RED,75);
            }
        }
    }
    public void move(Apple apple,Apple bomb){
        GameObject newHead=createNewHead();
        if (newHead.x<0 || newHead.x>=SnakeGame.WIDTH || newHead.y<0 || newHead.y>=SnakeGame.HEIGHT || checkCollision(newHead)){
            isAlive=false;
            return;
        }
        snakeParts.add(0,newHead);
        if (!(bomb==null)){
            if (newHead.x==bomb.x && newHead.y==bomb.y) bomb.isAlive=false;
        }
        if (newHead.x==apple.x && newHead.y==apple.y) apple.isAlive=false;
        else removeTail();

    }

    public GameObject createNewHead(){
        switch (direction){
            case LEFT : return new GameObject(snakeParts.get(0).x-1,snakeParts.get(0).y);
            case UP: return new GameObject(snakeParts.get(0).x,snakeParts.get(0).y-1);
            case RIGHT: return new GameObject(snakeParts.get(0).x+1,snakeParts.get(0).y);
            case DOWN: return new GameObject(snakeParts.get(0).x,snakeParts.get(0).y+1);
        }
        return null;
    }

    public void removeTail(){
            snakeParts.remove(snakeParts.size()-1);
    }
    public void setDirection(Direction direction){

        switch (direction){
            case LEFT:{
                if (!this.direction.equals(Direction.RIGHT) && !(snakeParts.get(0).y==snakeParts.get(1).y)) this.direction=direction;
                break;
            }
            case RIGHT:{
                if (!this.direction.equals(Direction.LEFT) && !(snakeParts.get(0).y==snakeParts.get(1).y)) this.direction=direction;
                break;
            }
            case UP:{
                if (!this.direction.equals(Direction.DOWN) && !(snakeParts.get(0).x==snakeParts.get(1).x)) this.direction=direction;
                break;
            }
            case DOWN:{
                if (!this.direction.equals(Direction.UP) && !(snakeParts.get(0).x==snakeParts.get(1).x)) this.direction=direction;
                break;
            }
        }
    }
    public int getLength(){
        return snakeParts.size();
    }

    public boolean checkCollision(GameObject g){
        for (GameObject p:snakeParts){
            if (g.x==p.x && g.y==p.y)return true;
        }
        return false;
    }



}

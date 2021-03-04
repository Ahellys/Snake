package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH=15;
    public static final int HEIGHT=15;
    private int turnDelay;
    private Snake snake;
    private Apple apple;
    private Apple bomb;
    private boolean isGameStopped;
    private static final int GOAL=28;
    private int score;

    @Override

    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();

    }

    private void createGame(){
        snake=new Snake(WIDTH/2,HEIGHT/2);
        createNewApple();
        isGameStopped=false;
        score=0;
        setScore(score);
        drawScene();
        turnDelay=300;
        setTurnTimer(turnDelay);
    }

    private void drawScene(){
        for (int x=0;x<WIDTH;x++){
            for(int y=0;y<HEIGHT;y++){
                setCellValueEx(y,x,Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
        if (!(bomb==null)) bomb.draw(this);
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.ORANGE,"SNAAAAAKE!!!",Color.BLACK,75);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.ORANGE,"You win, congratulations !",Color.BLACK,75);
    }


    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case LEFT:{
                snake.setDirection(Direction.LEFT);
                break;
            }
            case RIGHT:{
                snake.setDirection(Direction.RIGHT);
                break;
            }
            case UP:{
                snake.setDirection(Direction.UP);
                break;
            }
            case DOWN:{
                snake.setDirection(Direction.DOWN);
                break;
            }
            case SPACE:{
                if (isGameStopped)createGame();
            }
        }

    }

    @Override
    public void onTurn(int step) {
        snake.move(apple,bomb);
        if (!apple.isAlive) {
            score+=5;
            setScore(score);
            turnDelay-=10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive) gameOver();
        if (!(bomb==null) && !(bomb.isAlive)) gameOver();
        if (snake.getLength()>GOAL) win();
        drawScene();
    }

    @Override
    public void setTurnTimer(int timeMs) {
        super.setTurnTimer(timeMs);
    }

    private void createNewApple(){
        Apple newApple;
        Bomb newBomb;
        while(true){
             newApple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
             newBomb = new Bomb(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
             if (!snake.checkCollision(newApple) && !snake.checkCollision(newBomb) && !newBomb.checkCollision(newApple)) break;
        }
        apple=newApple;
        if (getRandomNumber(4)==1) bomb=newBomb;
        else bomb=null;
    }
}



package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
public class Enemy {
    public Vector2 positionEnemy;
    public Vector2 initialPositionEnemy;
    public Sprite spriteEnemy;
    public Boolean alive;
    public Enemy(Vector2 positionEnemy, Texture imgEnemy)
    {
        this.positionEnemy = positionEnemy;
        this.initialPositionEnemy = positionEnemy;
        spriteEnemy = new Sprite(imgEnemy);
        alive = true;
    }
    public void Draw(SpriteBatch batch)
    {
        spriteEnemy.setPosition(positionEnemy.x, positionEnemy.y);
        spriteEnemy.draw(batch);
    }
}

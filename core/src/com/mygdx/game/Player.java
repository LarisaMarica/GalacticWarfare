package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {

    public Vector2 positionRocket;
    public Vector2 positionBullet;
    public Sprite spriteRocket;
    public Sprite spriteBullet;
    public float speedRocket = 400;
    public float speedBullet = 1000;
    public Sound bulletSound;
    public Player(Texture img, Texture imgBullet)
    {
        spriteRocket = new Sprite(img);
        spriteBullet = new Sprite(imgBullet);
        spriteRocket.setScale(0.5f);
        spriteBullet.setScale(0.5f);
        positionRocket = new Vector2(Gdx.graphics.getWidth()/2 - 60,0);
        positionBullet = new Vector2(0,1000);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("gun.wav"));
    }
    public void Update(float deltaTime)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && positionBullet.y >= Gdx.graphics.getHeight())
        {
            positionBullet.x = positionRocket.x + 45;
            positionBullet.y = positionRocket.y + 30;
            bulletSound.play();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            positionRocket.x-=deltaTime* speedRocket;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            positionRocket.x+=deltaTime* speedRocket;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            positionRocket.y-=deltaTime* speedRocket;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            positionRocket.y+=deltaTime* speedRocket;
        }
        positionBullet.y +=deltaTime*speedBullet;
    }
    public void Draw(SpriteBatch batch)
    {
        Update(Gdx.graphics.getDeltaTime());
        spriteRocket.setPosition(positionRocket.x, positionRocket.y);
        spriteRocket.draw(batch);
        spriteBullet.setPosition(positionBullet.x, positionBullet.y);
        spriteBullet.draw(batch);
    }
}

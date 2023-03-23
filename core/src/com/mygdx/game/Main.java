package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture imgRocket;
	Texture imgBullet;
	Texture imgEnemy;
	Texture backgroundTexture;
	Player player;
	Enemy[] enemies;
	int widthEnemies = 11;
	int heightEnemies = 5;
	int spacing = 60;
	int minXEnemy;
	int minYEnemy;
	int maxXEnemy;
	int maxYEnemy;
	Vector2 offsetEnemy;
	int directionEnemy = 1;
	float speedEnemy = 300;
	public int amountAliveEnemies = 0;
	public Music music;
	
	@Override
	public void create () {
		offsetEnemy = Vector2.Zero;
		batch = new SpriteBatch();
		imgRocket = new Texture("SpaceShip.png");
		imgBullet = new Texture("laserBullet.png");
		imgEnemy = new Texture("Duck.png");
		backgroundTexture = new Texture("Background.jpg");
		player = new Player(imgRocket, imgBullet);
		enemies = new Enemy[widthEnemies*heightEnemies];
		music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		int i = 0;
		for(int y = 0; y< heightEnemies;y++)
		{
			for(int x = 0;x<widthEnemies;x++)
			{
				Vector2 position = new Vector2(x*spacing,y*spacing);
				position.x += Gdx.graphics.getWidth()/2;
				position.y += Gdx.graphics.getHeight();
				position.x-=(widthEnemies/2) * spacing;
				position.y-=(heightEnemies) * spacing;
				enemies[i++]=new Enemy(position, imgEnemy);
			}
		}
		music.setLooping(true);
		music.play();
	}
	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(backgroundTexture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player.Draw(batch);
		for(int i=0;i<enemies.length;i++)
		{
			if(enemies[i].alive)
			{
				if(player.spriteBullet.getBoundingRectangle().overlaps(enemies[i].spriteEnemy.getBoundingRectangle()))
				{
					player.positionBullet.y = 10000;
					enemies[i].alive = false;
				}
			}
		}
		minXEnemy = 10000;
		minYEnemy = 10000;
		maxXEnemy = 0;
		maxYEnemy = 0;
		for(int i=0;i<enemies.length;i++)
		{
			if(enemies[i].alive)
			{
				int indexX = i % widthEnemies;
				int indexY = i / heightEnemies;
				if(indexX>maxXEnemy)
				{
					maxXEnemy = indexX;
				}
				if(indexX<minXEnemy)
				{
					minXEnemy = indexX;
				}
				if(indexY>maxYEnemy)
				{
					maxYEnemy = indexY;
				}
				if(indexY<minYEnemy)
				{
					minYEnemy = indexY;
				}
				amountAliveEnemies++;
			}
		}
		if(amountAliveEnemies  == 0)
		{
			for(int i = 0;i<enemies.length;i++)
			{
				enemies[i].alive = true;
			}
			offsetEnemy =  new Vector2(0,0);
			batch.end();
			speedEnemy = 100;
			//TODO WIN
			return;
		}
		offsetEnemy.x += directionEnemy * deltaTime * speedEnemy;
		if(enemies[maxXEnemy].positionEnemy.x >= Gdx.graphics.getWidth() - 50)
		{
			directionEnemy = -1;
			offsetEnemy.y -= enemies[0].spriteEnemy.getHeight()*enemies[0].spriteEnemy.getScaleY()*0.05f;
			//TODO increase speed over time?
			//speedEnemy += 10;
		}
		if(enemies[minXEnemy].positionEnemy.x <= 0)
		{
			directionEnemy = 1;
			offsetEnemy.y -= enemies[0].spriteEnemy.getHeight()*enemies[0].spriteEnemy.getScaleY()*0.05f;
			//speedEnemy += 10;
		}
		if(enemies[minYEnemy].positionEnemy.y <= 0)
		{
			Gdx.app.exit();
		}
		for(int i=0;i<enemies.length;i++)
		{
			enemies[i].positionEnemy = new Vector2(enemies[i].initialPositionEnemy.x + offsetEnemy.x, enemies[i].initialPositionEnemy.y + offsetEnemy.y);
			if(enemies[i].alive)
			{
				enemies[i].Draw(batch);
				if(enemies[i].spriteEnemy.getBoundingRectangle().overlaps(player.spriteRocket.getBoundingRectangle()))
				{
					Gdx.app.exit();
				}
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgRocket.dispose();
		imgEnemy.dispose();
		imgBullet.dispose();
		music.dispose();
	}
}

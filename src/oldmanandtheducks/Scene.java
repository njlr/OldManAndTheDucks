package oldmanandtheducks;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import nlib.components.BasicComponentRenderable;

public strictfp final class Scene extends BasicComponentRenderable {
	
	private Animation animation;
	private Animation animationCloud;
	
	private float x;
	
	public Scene(long id) {
		
		super(id);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.animation = new Animation(new SpriteSheet("gfx/Scene.png", 1280, 800), 100);
		this.animationCloud = new Animation(new SpriteSheet("gfx/Clouds.png", 1280, 96), 100);
		
		this.x = 0f;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.x -= 0.005f * delta;
		
		while (this.x < - this.animationCloud.getWidth()) {
			
			this.x += this.animation.getWidth();
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animationCloud, (int) this.x, 0f);
		graphics.drawAnimation(this.animationCloud, (int) this.x + this.animationCloud.getWidth(), 0f);
		
		graphics.drawAnimation(this.animation, 0f, 0f);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_SCENE;
	}
}

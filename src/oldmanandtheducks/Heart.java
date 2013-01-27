package oldmanandtheducks;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponentRenderable;

public strictfp final class Heart extends BasicComponentRenderable {
	
	private final Vector2f position;
	
	private Animation animation;
	
	private int life;
	
	public Heart(long id, Vector2f position) {
		
		super(id);
		
		this.position = new Vector2f(position);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.animation = new Animation(new SpriteSheet("gfx/HeartFrame2.png", 50, 60), 100);
		
		this.life = 1 * 1000;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.position.y -= 0.1f * delta;
		
		this.life -= delta;
		
		if (this.life <= 0) {
			
			this.destroy(gameContainer);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animation, this.position.getX() - 25, this.position.getY() - 60);
	}
	
	@Override
	public strictfp float getDepth() {
		
		return Constants.DEPTH_BEAT_MANAGER;
	}
}

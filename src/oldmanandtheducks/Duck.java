package oldmanandtheducks;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponentRenderable;

public strictfp final class Duck extends BasicComponentRenderable {
	
	private static final float SPEED = 0.1f;
	
	private final Vector2f position;
	
	private Animation animation;
	
	private float minY;
	private float maxY;
	
	private boolean direction;
	
	public Duck(long id, Vector2f position) {
		
		super(id);
		
		this.position = new Vector2f(position);
		
		this.minY = this.position.getY() - 4f;
		this.maxY = this.position.getY() + 4f;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.animation = new Animation(new SpriteSheet("gfx/Ducky.png", 80, 48), 100);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.direction) {
			
			this.position.y += SPEED * delta;
			
			if (this.position.y >= this.maxY) {
				
				this.position.y = this.maxY;
				
				this.direction = !this.direction;
			}
		}
		else {
			
			this.position.y -= SPEED * delta;
			
			if (this.position.y <= this.minY) {
				
				this.position.y = this.minY;
				
				this.direction = !this.direction;
			}
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animation, this.position.getX(), this.position.getY());
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_DUCK;
	}
}

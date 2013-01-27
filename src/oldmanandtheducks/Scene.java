package oldmanandtheducks;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import nlib.components.BasicComponentRenderable;

public strictfp final class Scene extends BasicComponentRenderable {
	
	private Animation animation;
	
	public Scene(long id) {
		
		super(id);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.animation = new Animation(new SpriteSheet("gfx/Scene.png", 1280, 800), 100);
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animation, 0f, 0f);
	}
}

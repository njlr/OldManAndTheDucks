package oldmanandtheducks;

import oldmanandtheducks.events.GameStartEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.google.common.eventbus.EventBus;

import nlib.components.BasicComponentRenderable;

public strictfp final class TitleBox extends BasicComponentRenderable implements KeyListener {
	
	private static final float SPEED = 1f;
	private static final float TARGET_Y = 128f;
	
	private final EventBus eventBus;
	
	private Animation animation;
	
	private Animation animationRed;
	private Animation animationBlue;
	private Animation animationYellow;
	private Animation animationGreen;
	
	private Animation animationRedControl;
	private Animation animationBlueControl;
	private Animation animationYellowControl;
	private Animation animationGreenControl;
	
	private boolean isClosing;
	
	private float x;
	private float y;
	
	private Input input;
	
	public TitleBox(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.animation = new Animation(new SpriteSheet("gfx/TitleBox.png", 640, 480), 100);
		
		this.animationRed = new Animation(new SpriteSheet("gfx/Red.png", 96, 96), 100);
		this.animationBlue = new Animation(new SpriteSheet("gfx/Blue.png", 96, 96), 100);
		this.animationYellow = new Animation(new SpriteSheet("gfx/Yellow.png", 96, 96), 100);
		this.animationGreen = new Animation(new SpriteSheet("gfx/Green.png", 96, 96), 100);
		
		this.animationRedControl = new Animation(new SpriteSheet("gfx/RedControl.png", 96, 48), 100);
		this.animationBlueControl = new Animation(new SpriteSheet("gfx/BlueControl.png", 96, 48), 100);
		this.animationYellowControl = new Animation(new SpriteSheet("gfx/YellowControl.png", 96, 48), 100);
		this.animationGreenControl = new Animation(new SpriteSheet("gfx/GreenControl.png", 96, 48), 100);
		
		this.x = 1280 / 2f - 640f / 2f;
		this.y = -770f;
		
		this.setInput(gameContainer.getInput());
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.isClosing) {
			
			this.y -= SPEED * delta;
			
			if (y < -480f) {
				
				this.eventBus.post(new GameStartEvent());
				
				this.destroy(gameContainer);
			}
		}
		else {
			
			if (this.y < TARGET_Y) {
				
				this.y += SPEED * delta;
				
				if (this.y > TARGET_Y) {
					
					this.y = TARGET_Y;
				}
			}
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animation, this.x, this.y);
		
		float w = 128f;
		
		float xx = 1280 / 2f - w * 2f;
		float yy = y + 296f;
		
		float hh = 96f;
		
		graphics.drawAnimation(this.animationRed, xx, yy);
		graphics.drawAnimation(this.animationRedControl, xx, yy + hh);
		
		xx += w;
		
		graphics.drawAnimation(this.animationBlue, xx, yy);
		graphics.drawAnimation(this.animationBlueControl, xx, yy + hh);
		
		xx += w;
		
		graphics.drawAnimation(this.animationYellow, xx, yy);
		graphics.drawAnimation(this.animationYellowControl, xx, yy + hh);
		
		xx += w;
		
		graphics.drawAnimation(this.animationGreen, xx, yy);
		graphics.drawAnimation(this.animationGreenControl, xx, yy + hh);
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.input.removeKeyListener(this);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_TITLE_BOX;
	}
	
	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		
		return this.isInitialized() && this.isAlive();
	}

	@Override
	public void setInput(Input input) {
		
		if (this.input != input) {
			
			if (this.input != null) {
				
				this.input.removeKeyListener(this);
			}
			
			this.input = input;
			
			this.input.addKeyListener(this);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		
		if ((key == Constants.KEY_RED) || (key == Constants.KEY_BLUE) || (key == Constants.KEY_YELLOW) || (key == Constants.KEY_GREEN)) {
			
			this.isClosing = true;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		
	}
}

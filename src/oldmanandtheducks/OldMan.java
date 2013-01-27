package oldmanandtheducks;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;
import oldmanandtheducks.events.GameOverEvent;
import oldmanandtheducks.events.SequenceDoneEvent;
import oldmanandtheducks.events.SequenceFailedEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.Subscribe;

public strictfp final class OldMan extends BasicComponentRenderable {
	
	private enum State { Healthy, Pain, Flying, Dead };
	
	private final ComponentManager componentManager;
	
	private final Vector2f startingPosition;
	private final Vector2f position;
	
	private State state;
	
	private Animation animation;
	private Animation animationPain;
	private Animation animationDead;
	private Animation animationFlying;
	// private Animation animationBoots;
	
	public OldMan(long id, ComponentManager componentManager, Vector2f position) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.startingPosition = new Vector2f(position);
		this.position = new Vector2f(position);
		
		this.componentManager.getEventBus().register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.state = State.Healthy;
		
		this.animation = new Animation(new SpriteSheet("gfx/OldMan.png", 164, 272), 100);
		this.animationPain = new Animation(new SpriteSheet("gfx/OldManPain.png", 164, 272), 100);
		this.animationDead = new Animation(new SpriteSheet("gfx/OldManDead.png", 272, 164), 100);
		this.animationFlying = new Animation(new SpriteSheet("gfx/OldManFlying.png", 164, 320), 100);
		// this.animationBoots = new Animation(new SpriteSheet("gfx/Boots.png", 164, 272), 100);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		switch (this.state) {
		
		case Flying:
			
			this.position.y -= 0.1f * delta;
			
			if (this.position.y < -2048f) {
				
				this.state = State.Dead;
			}
			
			break;
			
		case Dead:
			
			this.position.y += 1f * delta;
			
			if (this.position.y >= this.startingPosition.getY()) {
				
				this.position.y = this.startingPosition.getY();
			}
			
			break;
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		// graphics.drawAnimation(this.animationBoots, this.startingPosition.getX(), this.startingPosition.getY());
		
		switch (this.state) {
		
		case Healthy:
			
			graphics.drawAnimation(this.animation, this.position.getX(), this.position.getY());
			
			break;
			
		case Pain:
			
			graphics.drawAnimation(this.animationPain, this.position.getX(), this.position.getY());
			
			break;
			
		case Flying:
			
			graphics.drawAnimation(this.animationFlying, this.position.getX(), this.position.getY() - 48f);
			
			break;
			
		case Dead:
			
			graphics.drawAnimation(this.animationDead, this.position.getX() + 192f, this.position.getY() + 128f);
			
			break;
		}
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.componentManager.getEventBus().unregister(this);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_OLD_MAN;
	}
	
	@Subscribe
	public void handleSequenceDoneEvent(SequenceDoneEvent e) {
		
		switch (this.state) {
		
		case Pain:
			
			this.state = State.Healthy;
			
		case Healthy:
			
			this.componentManager.addComponent(
					new Heart(
							this.componentManager.takeId(), 
							new Vector2f(this.position.getX() + 82, this.position.getY())));
			
			break;
		}
	}
	
	@Subscribe
	public void handleSequenceFailedEvent(SequenceFailedEvent e) {
		
		switch (this.state) {
		
		case Healthy:
			
			this.state = State.Pain;
			
			break;

		case Pain:
			
			this.state = State.Flying;
			
			this.componentManager.getEventBus().post(new GameOverEvent());
			
			break;
		}
	}
}

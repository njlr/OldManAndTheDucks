package oldmanandtheducks;

import oldmanandtheducks.events.BeatMissedEvent;
import oldmanandtheducks.events.SequenceDoneEvent;
import oldmanandtheducks.events.SequenceFailedEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import nlib.components.BasicComponentRenderable;

public strictfp final class OldMan extends BasicComponentRenderable {
	
	private enum State { Healthy, Pain, Flying, Dead };
	
	private final EventBus eventBus;
	
	private final Vector2f startingPosition;
	private final Vector2f position;
	
	private State state;
	
	private Animation animation;
	private Animation animationDead;
	private Animation animationBoots;
	
	public OldMan(long id, EventBus eventBus, Vector2f position) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.startingPosition = new Vector2f(position);
		this.position = new Vector2f(position);
		
		this.eventBus.register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.state = State.Healthy;
		
		this.animation = new Animation(new SpriteSheet("gfx/OldMan.png", 164, 272), 100);
		this.animationDead = new Animation(new SpriteSheet("gfx/OldManDead.png", tw, th), 100);
		this.animationBoots = new Animation(new SpriteSheet("gfx/Boots.png", 164, 272), 100);
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		switch (this.state) {
		
		case Flying:
			
			this.position.y -= 1f * delta;
			
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
		
		graphics.drawAnimation(this.animationBoots, this.startingPosition.getX(), this.startingPosition.getY());
		
		switch (this.state) {
		
		case Healthy:
		case Pain:
		case Flying:
			
			graphics.drawAnimation(this.animation, this.position.getX(), this.position.getY());
			
			break;
			
		case Dead:
			
			graphics.drawAnimation(this.animationDead, this.position.getX(), this.position.getY());
			
			break;
		}
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.unregister(this);
	}
	
	@Subscribe
	public void handleSequenceDoneEvent(SequenceDoneEvent e) {
		
		switch (this.state) {
		
		case Pain:
			
			this.state = State.Healthy;
			
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
			
			break;
		}
	}
}

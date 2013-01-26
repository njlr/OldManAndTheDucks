package oldmanandtheducks;

import nlib.components.ComponentManager;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.google.common.eventbus.Subscribe;

public strictfp final class OldManAndTheDucksGame extends BasicGame {
	
	private ComponentManager componentManager;
	
	public OldManAndTheDucksGame() {
		
		super("The Old Man and the Ducks");
		
		this.componentManager = new ComponentManager();
		
		this.componentManager.getEventBus().register(this);
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		this.componentManager.addComponent(
				new BeatManager(
						this.componentManager.takeId(), 
						this.componentManager.getEventBus()));
		
		this.componentManager.addComponent(
				new Controller(
						this.componentManager.takeId(), 
						this.componentManager.getEventBus()));
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		this.componentManager.render(gameContainer, graphics);
	}
	
	@Subscribe
	public void handleEvent(Object e) {
		
		System.out.println(e.toString());
	}
}

package oldmanandtheducks;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import nlib.components.BasicComponent;
import nlib.components.ComponentManager;

public strictfp final class Leaves extends BasicComponent {
	
	private final ComponentManager componentManager;
	
	private final Random random;
	
	public Leaves(long id, ComponentManager componentManager) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.random = new Random();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		for (int i = 0; i < 8; i++) {
			
			this.spawn();
		}
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		for (int i = 0; i < delta; i++) {
			
			if (this.random.nextFloat() > 0.95f) {
				
				this.spawn();
			}
		}
	}
	
	private void spawn() {
		
		this.componentManager.addComponent(
				new Leaf(this.componentManager.takeId()));
	}
}

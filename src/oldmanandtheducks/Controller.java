package oldmanandtheducks;

import oldmanandtheducks.events.BeatPressedEvent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

import com.google.common.eventbus.EventBus;

import nlib.components.BasicComponent;

public strictfp final class Controller extends BasicComponent implements KeyListener {
	
	private final EventBus eventBus;
	
	private Input input;
	
	public Controller(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.input = null;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.setInput(gameContainer.getInput());
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.input.removeKeyListener(this);
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
		
		switch (key) {
		
		case Constants.KEY_RED:
			
			this.eventBus.post(new BeatPressedEvent(Beat.RED));
			
			break;
			
		case Constants.KEY_BLUE:
			
			this.eventBus.post(new BeatPressedEvent(Beat.BLUE));
			
			break;
			
		case Constants.KEY_YELLOW:
			
			this.eventBus.post(new BeatPressedEvent(Beat.YELLOW));
			
			break;
			
		case Constants.KEY_GREEN:
			
			this.eventBus.post(new BeatPressedEvent(Beat.GREEN));
			
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		
	}
}

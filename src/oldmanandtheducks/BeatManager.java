package oldmanandtheducks;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import nlib.components.BasicComponentRenderable;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import oldmanandtheducks.events.BeatMissedEvent;
import oldmanandtheducks.events.BeatPressedEvent;

public strictfp final class BeatManager extends BasicComponentRenderable {
	
	private final EventBus eventBus;
	
	private final Random random;
	private final Set<Beat> beatsRequired;
	
	private int timeTillNextSequence;
	
	public BeatManager(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.random = new Random();
		this.beatsRequired = new HashSet<Beat>();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.timeTillNextSequence = Constants.TIME_PER_SEQUENCE;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.timeTillNextSequence -= delta;
		
		if (this.timeTillNextSequence <= 0) {
			
			if (!this.beatsRequired.isEmpty()) {
				
				this.eventBus.post(new BeatMissedEvent());
			}
			
			this.timeTillNextSequence = Constants.TIME_PER_SEQUENCE;
			
			this.nextSequence();
		}
	}
	
	private void nextSequence() {
		
		this.beatsRequired.clear();
		
		int numberOfBeats = 1 + this.random.nextInt(3);
		
		while (this.beatsRequired.size() < numberOfBeats) {
			
			switch (this.random.nextInt(4)) {
			
			case 0:
				
				this.beatsRequired.add(Beat.RED);
				
				break;
				
			case 1:
				
				this.beatsRequired.add(Beat.BLUE);
				
				break;
				
			case 2:
				
				this.beatsRequired.add(Beat.YELLOW);
				
				break;
				
			case 3:
				
				this.beatsRequired.add(Beat.GREEN);
				
				break;

			default:
				
				this.beatsRequired.add(Beat.RED);
				
				break;
			}
		}
	}
	
	@Subscribe
	public void handleBeatPressedEvent(BeatPressedEvent e) {
		
		if (this.beatsRequired.contains(e.getBeat())) {
			
			this.beatsRequired.remove(e.getBeat());
		}
		else {
			
			this.eventBus.post(new BeatMissedEvent());
		}
	}
}

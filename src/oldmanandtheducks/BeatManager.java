package oldmanandtheducks;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import nlib.components.BasicComponentRenderable;
import oldmanandtheducks.events.BeatMissedEvent;
import oldmanandtheducks.events.BeatPressedEvent;
import oldmanandtheducks.events.GameOverEvent;
import oldmanandtheducks.events.SequenceDoneEvent;
import oldmanandtheducks.events.SequenceFailedEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public strictfp final class BeatManager extends BasicComponentRenderable {
	
	private final EventBus eventBus;
	
	private final Random random;
	private final Set<Beat> beatsRequired;
	
	private int timeBetweenSequences;
	private int timeTillNextSequence;
	
	private Animation animationRed;
	private Animation animationBlue;
	private Animation animationYellow;
	private Animation animationGreen;
	
	private Animation animationCross;
	
	private Animation animationBox;
	
	private Animation animationTimer;
	
	private boolean wrongRed;
	private boolean wrongBlue;
	private boolean wrongYellow;
	private boolean wrongGreen;
	
	private Sound soundRed;
	private Sound soundBlue;
	private Sound soundYellow;
	private Sound soundGreen;
	
	private boolean isRunning;
	
	public BeatManager(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.random = new Random();
		this.beatsRequired = new HashSet<Beat>();
		
		this.eventBus.register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.timeBetweenSequences = Constants.MAX_TIME_PER_SEQUENCE;
		
		this.timeTillNextSequence = this.timeBetweenSequences;
		
		this.animationRed = new Animation(new SpriteSheet("gfx/Red.png", 96, 96), 100);
		this.animationBlue = new Animation(new SpriteSheet("gfx/Blue.png", 96, 96), 100);
		this.animationYellow = new Animation(new SpriteSheet("gfx/Yellow.png", 96, 96), 100);
		this.animationGreen = new Animation(new SpriteSheet("gfx/Green.png", 96, 96), 100);
		
		this.animationRed.setLooping(false);
		this.animationBlue.setLooping(false);
		this.animationYellow.setLooping(false);
		this.animationGreen.setLooping(false);
		
		this.animationCross = new Animation(new SpriteSheet("gfx/Cross.png", 96, 96), 100);
		
		this.animationBox = new Animation(new SpriteSheet("gfx/Box.png", 96, 96), 100);
		
		this.animationTimer = new Animation(new SpriteSheet("gfx/Timer.png", 96, 96), 100);
		
		this.animationTimer.setAutoUpdate(false);
		
		this.wrongRed = false;
		this.wrongBlue = false;
		this.wrongYellow = false;
		this.wrongGreen = false;
		
		this.soundRed = new Sound("sfx/Beat1.ogg");
		this.soundBlue = new Sound("sfx/Beat2.ogg");
		this.soundYellow = new Sound("sfx/Beat3.ogg");
		this.soundGreen = new Sound("sfx/Beat4.ogg");
		
		this.isRunning = true;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
			
		this.timeTillNextSequence -= delta;
		
		if (this.timeTillNextSequence <= 0) {
			
			this.nextSequence();
		}
		
		if (!this.isRunning) {
			
			this.destroy(gameContainer);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		float w = 128f;
		
		float c = 1280 / 2f;
		
		float x = c - w * 2f;
		float y = 32f;
		
		// Red
		graphics.drawAnimation(this.animationBox, x, y);
		
		if (this.beatsRequired.contains(Beat.RED)) {
			
			graphics.drawAnimation(this.animationRed, x, y);
		}
		else if (this.wrongRed) {
			
			graphics.drawAnimation(this.animationCross, x, y);
		}
		
		x += w;
		
		// Blue
		graphics.drawAnimation(this.animationBox, x, y);
		
		if (this.beatsRequired.contains(Beat.BLUE)) {
			
			graphics.drawAnimation(this.animationBlue, x, y);
		}
		else if (this.wrongBlue) {
			
			graphics.drawAnimation(this.animationCross, x, y);
		}
		
		x += w;
		
		// Yellow
		graphics.drawAnimation(this.animationBox, x, y);
		
		if (this.beatsRequired.contains(Beat.YELLOW)) {
			
			graphics.drawAnimation(this.animationYellow, x, y);
		}
		else if (this.wrongYellow) {
			
			graphics.drawAnimation(this.animationCross, x, y);
		}
		
		x += w;
		
		// Green
		graphics.drawAnimation(this.animationBox, x, y);
		
		if (this.beatsRequired.contains(Beat.GREEN)) {
			
			graphics.drawAnimation(this.animationGreen, x, y);
		}
		else if (this.wrongGreen) {
			
			graphics.drawAnimation(this.animationCross, x, y);
		}
		
		x += w;
		
		// Timer
		int n = ((this.timeBetweenSequences - this.timeTillNextSequence) * 
				this.animationTimer.getFrameCount()) / this.timeBetweenSequences;
		
		this.animationTimer.setCurrentFrame(n);
		
		graphics.drawAnimation(this.animationTimer, x, y);
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.unregister(this);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_BEAT_MANAGER;
	}
	
	private void nextSequence() {
		
		if (this.timeBetweenSequences > Constants.MIN_TIME_PER_SEQUENCE) {
			
			this.timeBetweenSequences -= Constants.TIME_DROP;
			
			if (this.timeBetweenSequences < Constants.MIN_TIME_PER_SEQUENCE) {
				
				this.timeBetweenSequences = Constants.MIN_TIME_PER_SEQUENCE;
			}
		}
		
		this.timeTillNextSequence = timeBetweenSequences;
		
		if (this.beatsRequired.isEmpty()) {
			
			if ((this.wrongRed) || (this.wrongBlue) || (this.wrongYellow) || (this.wrongGreen)) {
				
				this.eventBus.post(new SequenceFailedEvent());
			}
			else {
				
				this.eventBus.post(new SequenceDoneEvent());
			}
		}
		else {
			
			this.eventBus.post(new BeatMissedEvent());
			
			this.eventBus.post(new SequenceFailedEvent());
		}
		
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
		
		this.wrongRed = false;
		this.wrongBlue = false;
		this.wrongYellow = false;
		this.wrongGreen = false;
		
		this.animationRed.restart();
		this.animationBlue.restart();
		this.animationYellow.restart();
		this.animationGreen.restart();
	}
	
	@Subscribe
	public void handleBeatPressedEvent(BeatPressedEvent e) {
		
		switch (e.getBeat()) {
		
		case RED:
			
			this.soundRed.play();
			
			break;
			
		case BLUE:
			
			this.soundBlue.play();
			
			break;
			
		case YELLOW:
			
			this.soundYellow.play();
			
			break;
			
		case GREEN:
			
			this.soundGreen.play();
			
			break;
		}
		
		if (this.beatsRequired.contains(e.getBeat())) {
			
			this.beatsRequired.remove(e.getBeat());
			
			if (this.beatsRequired.isEmpty()) {
				
				this.nextSequence();
			}
		}
		else {
			
			switch (e.getBeat()) {
			
			case RED:
				
				this.wrongRed = true;
				
				break;
				
			case BLUE:
				
				this.wrongBlue = true;
				
				break;
				
			case YELLOW:
				
				this.wrongYellow = true;
				
				break;
				
			case GREEN:
				
				this.wrongGreen = true;
				
				break;
			}
			
			this.eventBus.post(new BeatMissedEvent());
		}
	}
	
	@Subscribe
	public void handleGameOverEvent(GameOverEvent e) {
		
		this.isRunning = false;
	}
}

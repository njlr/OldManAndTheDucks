package oldmanandtheducks;

import nlib.components.ComponentManager;

import oldmanandtheducks.events.GameStartEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.Subscribe;

public strictfp final class GameStatePlaying extends BasicGameState {
	
	public static final int ID = 0;
	
	private ComponentManager componentManager;
	
	public GameStatePlaying() {
		
		super();
	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
	}
	
	@Override
	public void enter(GameContainer gameContainer, StateBasedGame game) throws SlickException {
		
		super.enter(gameContainer, game);
		
		this.componentManager = new ComponentManager();
		
		this.componentManager.getEventBus().register(this);
		
		this.componentManager.addComponent(
				new Scene(this.componentManager.takeId()));
		
		this.componentManager.addComponent(
				new OldMan(
						this.componentManager.takeId(), 
						this.componentManager, 
						new Vector2f(635f, 446f)));
		
		this.componentManager.addComponent(
				new Duck(
						this.componentManager.takeId(), 
						new Vector2f(252f, 528f)));
		
		this.componentManager.addComponent(
				new Duck(
						this.componentManager.takeId(), 
						new Vector2f(387f, 571f)));
		
		this.componentManager.addComponent(
				new Duck(
						this.componentManager.takeId(), 
						new Vector2f(341f, 665f)));
		
		this.componentManager.addComponent(
				new Leaves(this.componentManager.takeId(), this.componentManager));
		
		this.componentManager.addComponent(
				new TitleBox(
						this.componentManager.takeId(), 
						this.componentManager.getEventBus()));
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
		
		if (gameContainer.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			
			stateBasedGame.enterState(ID);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		
		graphics.setAntiAlias(false);
		graphics.setBackground(Color.white);
		graphics.clear();
		
		this.componentManager.render(gameContainer, graphics);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		
		super.leave(container, game);
		
		this.componentManager.destroy();
		
		this.componentManager.getEventBus().unregister(this);
		
		this.componentManager = null;
	}

	@Override
	public int getID() {
		
		return ID;
	}
	
	@Subscribe
	public void handleGameStartEvent(GameStartEvent e) {
		
		this.componentManager.addComponent(
				new BeatManager(
						this.componentManager.takeId(), 
						this.componentManager.getEventBus()));
		
		this.componentManager.addComponent(
				new Controller(
						this.componentManager.takeId(), 
						this.componentManager.getEventBus()));
	}
}

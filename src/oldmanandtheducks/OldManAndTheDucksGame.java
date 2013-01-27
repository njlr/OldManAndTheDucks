package oldmanandtheducks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public strictfp final class OldManAndTheDucksGame extends StateBasedGame {
	
	public OldManAndTheDucksGame() {
		
		super("The Old Man and The Ducks");
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		
		this.addState(new GameStatePlaying());
		
		this.enterState(GameStatePlaying.ID);
	}
}

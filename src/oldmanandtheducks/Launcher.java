package oldmanandtheducks;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public strictfp final class Launcher {
	
	private Launcher() {
		
		super();
	}
	
	public static void main(String[] args) throws SlickException {
		
		Utils.linkLwjgl();
		
		Game game = new OldManAndTheDucksGame();
		
		ScalableGame scalableGame = new ScalableGame(game, 1280, 800);
		
		AppGameContainer appGameContainer = new AppGameContainer(scalableGame);
		
		appGameContainer.setDisplayMode(960, 640, false);
		appGameContainer.setTargetFrameRate(60);
		appGameContainer.setVSync(true);
		
		appGameContainer.start();
	}
}

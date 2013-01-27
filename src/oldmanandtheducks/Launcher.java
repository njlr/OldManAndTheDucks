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
		
		int w;
		int h;
		
		boolean b;
		
		if (args.length == 3) {
			
			w = Integer.parseInt(args[0]);
			h = Integer.parseInt(args[1]);
			
			b = Boolean.parseBoolean(args[2]);
		}
		else {
			
			w = 1280;
			h = 800;
			
			b = false;
		}
		
		Game game = new OldManAndTheDucksGame();
		
		ScalableGame scalableGame = new ScalableGame(game, 1280, 800, true);
		
		AppGameContainer appGameContainer = new AppGameContainer(scalableGame);
		
		appGameContainer.setDisplayMode(w, h, b);
		appGameContainer.setTargetFrameRate(30);
		appGameContainer.setVSync(true);
		appGameContainer.setShowFPS(false);
		appGameContainer.setMouseGrabbed(true);
		
		appGameContainer.start();
	}
}

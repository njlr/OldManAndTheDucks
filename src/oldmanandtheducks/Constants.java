package oldmanandtheducks;

import org.newdawn.slick.Input;

public strictfp final class Constants {
	
	private Constants() {
		
		super();
	}
	
	public static final int MAX_TIME_PER_SEQUENCE = 3 * 1000;
	public static final int MIN_TIME_PER_SEQUENCE = 100;
	
	public static final int TIME_DROP = 10;
	
	public static final int KEY_RED = Input.KEY_LCONTROL;
	public static final int KEY_BLUE = Input.KEY_SPACE;
	public static final int KEY_YELLOW = Input.KEY_RCONTROL;
	public static final int KEY_GREEN = Input.KEY_RIGHT;
	
	public static final float DEPTH_SCENE = 0f;
	public static final float DEPTH_CLOUDS = -1f;
	public static final float DEPTH_DUCK = -2f;
	public static final float DEPTH_OLD_MAN = -2f;
	public static final float DEPTH_LEAF = -3f;
	public static final float DEPTH_BEAT_MANAGER = -4f;
	public static final float DEPTH_TITLE_BOX = -5f;
}

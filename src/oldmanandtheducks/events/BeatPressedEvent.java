package oldmanandtheducks.events;

import oldmanandtheducks.Beat;

public strictfp final class BeatPressedEvent extends OldManAndTheDucksEvent {
	
	private static final long serialVersionUID = -6669100755705563892L;
	
	private final Beat beat;
	
	public Beat getBeat() {
		
		return this.beat;
	}
	
	public BeatPressedEvent(Beat beat) {
		
		super();
		
		this.beat = beat;
	}
}

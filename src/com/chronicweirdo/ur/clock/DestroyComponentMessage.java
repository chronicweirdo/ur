package com.chronicweirdo.ur.clock;

import com.chronicweirdo.ur.clock.components.GameComponent;

public class DestroyComponentMessage implements GameMessage {

	private GameComponent component;

	public DestroyComponentMessage(GameComponent component) {
		super();
		this.component = component;
	}

	public GameComponent getComponent() {
		return component;
	}
	
	
}

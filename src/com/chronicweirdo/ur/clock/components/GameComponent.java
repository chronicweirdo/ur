package com.chronicweirdo.ur.clock.components;

import org.jbox2d.dynamics.Body;

public interface GameComponent {

	void render();
	
	Body getBody();
}

package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.Display;

public class Main {
	
	private static final String WINDOW_TITLE = "Physics in 2D!";
	private static final int[] WINDOW_DIMENSIONS = { 640, 480 };

	private World world;
	private Set<GameBody> bodies;
	private Set<InputHandler> handlers;
	
	private Main() {
		this.world = new World(new Vec2(0, -9.8f), false);
		this.bodies = new HashSet<GameBody>();
		this.handlers = new HashSet<InputHandler>();
	}
	
	private void startGame() {
		while (!Display.isCloseRequested()) {
			render();
			logic();
			input();
			update();
		}
	}
	
	private void input() {
		for (InputHandler handler: this.handlers) {
			handler.input();
		}
	}
	
	private void update() {
		Display.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (GameBody body : this.bodies) {
			body.render();
		}
		renderHUD();
	}

	private void renderHUD() {
		// draw on screen info
	}

	private void logic() {
		this.world.step(1 / 60f, 8, 3);
	}
	
	public static void main(String[] args) {
		
	}

}

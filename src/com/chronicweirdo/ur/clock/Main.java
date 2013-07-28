package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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
		
		this.setUp();
	}
	
	private void setUp() {
		setUpDisplay();
		setUpObjects();
		setUpMatrices();
	}
	
	private void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WINDOW_DIMENSIONS[0],
					WINDOW_DIMENSIONS[1]));
			Display.setVSyncEnabled(true);
			Display.setTitle(WINDOW_TITLE);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			cleanUp(true);
		}
	}
	
	private void setUpMatrices() {
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, 320, 0, 240, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void setUpObjects() {
		// set up walls
		this.addBody(new Wall(this.world, 0f, 0f, 1000f, 0f)); // ground
		this.addBody(new Wall(this.world, 0f, 0f, 0f, 1000f)); // left wall
		this.addBody(new Wall(this.world, 320f / 30, 0f, 0f, 1000f)); // right wall
		this.addBody(new Wall(this.world, 0, 240f / 30, 1000f, 0f)); // ceiling
		
		// add a box
		this.addBody(new Box(this.world, 50f, 50f, 20f, 20f));
	}
	
	private static void cleanUp(boolean asCrash) {
		Display.destroy();
		System.exit(asCrash ? 1 : 0);
	}
	
	private void addBody(GameBody body) {
		this.bodies.add(body);
		if (body instanceof InputHandler) {
			this.handlers.add((InputHandler) body);
		}
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
		Main main = new Main();
		main.startGame();
		main.cleanUp(false);
	}

}

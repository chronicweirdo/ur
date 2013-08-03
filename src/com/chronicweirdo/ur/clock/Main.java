package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Main {

	@Autowired
	private Globals globals;

	@Autowired
	private GameComponentFactory factory;

	private static final String WINDOW_TITLE = "Clock Glock";
	private static final int[] WINDOW_DIMENSIONS = { 640, 480 };

	private Set<GameComponent> components;
	private Set<InputHandler> handlers;

	private Main() {
		this.components = new HashSet<GameComponent>();
		this.handlers = new HashSet<InputHandler>();
	}

	@PostConstruct
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
		this.addBody(factory.wall(0f, 0f, 1000f, 0f)); // ground
		this.addBody(factory.wall(0f, 0f, 0f, 1000f)); // left wall
		this.addBody(factory.wall(320f / globals.d(), 0f, 0f, 1000f)); // right
																		// wall
		this.addBody(factory.wall(0, 240f / globals.d(), 1000f, 0f)); // ceiling

		// add a box
		// this.addBody(new ControllableBox(this.world, 3f, 3f, 0.2f, 2f));
		// this.addBody(new Box(this.world, 6f, 3f, 1f, 2f));
		// this.addBody(new Ball(this.world, 5f, 15f, 1f));
		this.addBody(factory.cannon(WINDOW_DIMENSIONS[0] / 4 / globals.d(),
				WINDOW_DIMENSIONS[1] / 4 / globals.d()));
	}

	private static void cleanUp(boolean asCrash) {
		Display.destroy();
		System.exit(asCrash ? 1 : 0);
	}

	private void addBody(GameComponent body) {
		this.components.add(body);
		if (body instanceof InputHandler) {
			System.out.println("adding input handler");
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
		for (InputHandler handler : this.handlers) {
			handler.input();
		}
	}

	private void update() {
		Display.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (GameComponent component : this.components) {
			component.render();
		}
		renderHUD();
	}

	private void renderHUD() {
		// draw on screen info
	}

	private void logic() {
		globals.world().step(1 / 30f, 8, 3);
	}

	public static void main(String[] args) {
		/* Main main = new Main(); */
		String path = "clockContext.xml";
		// File context = new File("conf\\clockContext.xml");
		// System.out.println(context.exists());
		ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
		Main main = (Main) ctx.getBean(Main.class);
		main.startGame();
		main.cleanUp(false);
		/*
		 * GameComponentFactory factory =
		 * ctx.getBean(GameComponentFactory.class); Ball ball =
		 * factory.ball(10f, 10f, 10f); System.out.println(ball.getGlobals());
		 */
	}

}

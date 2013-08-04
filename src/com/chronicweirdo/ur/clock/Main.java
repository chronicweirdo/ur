package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import sun.security.krb5.internal.crypto.Des;

import com.chronicweirdo.ur.clock.Hour.HourSet;
import com.chronicweirdo.ur.clock.components.ComponentFactory;
import com.chronicweirdo.ur.clock.components.GameComponent;
import com.chronicweirdo.ur.clock.components.InputHandler;

@Service
public class Main {

	@Autowired
	private Globals globals;

	@Autowired
	private ComponentFactory factory;

	private static final String WINDOW_TITLE = "Clock Glock";
	private static final int[] WINDOW_DIMENSIONS = { 640, 640 };
	
	private long switchHourTimeout = 10000;
	private long lastHourSwitch = 0l;
	
	private Random random = new Random(System.currentTimeMillis());
	
	private List<GameMessage> messages = new ArrayList<GameMessage>();
	
	private HourSet currentHour;
	
	public void addMessage(GameMessage m) {
		this.messages.add(m);
	}

	public HourSet getCurrentHour() {
		return currentHour;
	}

	public void setCurrentHour(HourSet currentHour) {
		this.currentHour = currentHour;
	}

	private Main() {
	}

	@PostConstruct
	private void setUp() {
		setUpDisplay();
		setUpObjects();
		setUpMatrices();
		globals.setContactListener(new MyContactListener(this));
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
		glOrtho(0, WINDOW_DIMENSIONS[0]/2, 0, WINDOW_DIMENSIONS[1]/2, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private void setUpObjects() {
		// set up walls
		factory.wall(0f, 0f, 1000f, 0f); // ground
		factory.wall(0f, 0f, 0f, 1000f); // left wall
		factory.wall(WINDOW_DIMENSIONS[1] / 2 / globals.d(), 0f, 0f, 1000f); // right wall
		factory.wall(0, WINDOW_DIMENSIONS[0] / 2 / globals.d(), 1000f, 0f); // ceiling

		Vec2 center = new Vec2(WINDOW_DIMENSIONS[0] / 4 / globals.d(),
				WINDOW_DIMENSIONS[1] / 4 / globals.d()+5);
		factory.cannon(center.x, center.y);
		
		// set hours
		float radius = 10f;
		for (int i = 0; i < HourSet.values().length; i++) {
			HourSet h = HourSet.values()[i];
			double degInRad = Math.toRadians(i);
			double angle = i * 2 * Math.PI / HourSet.values().length;
			double x = center.x + (Math.cos(angle) * radius);
			double y = center.y + (Math.sin(angle) * radius);
			factory.hourPin(new Hour(h, true), (float) x, (float) y);
		}
		
		setCurrentHour(HourSet.ONE);
		//factory.box(10, 20, 1, 2);
		//factory.box(20, 20, 1, 2);
		
	}

	private void cleanUp(boolean asCrash) {
		Display.destroy();
		System.exit(asCrash ? 1 : 0);
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
		for (InputHandler handler : this.factory.handlers()) {
			handler.input();
		}
	}

	private void update() {
		Display.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (GameComponent component : this.factory.components()) {
			component.render();
		}
		renderHUD();
	}

	private void renderHUD() {
		// draw on screen info
	}

	private void logic() {
		// add new bodies
		for (GameMessage gm: messages) {
			if (gm instanceof CreateBoxMessage) {
				CreateBoxMessage m = (CreateBoxMessage) gm;
				factory.box(m.x, m.y, m.w, m.h);
			}
			if (gm instanceof CreateBombMessage) {
				CreateBombMessage m = (CreateBombMessage) gm;
				factory.bomb(m.x, m.y, m.r, 3.5f, 5000);
			}
			if (gm instanceof DestroyBoxMessage) {
				factory.destroyBox();
			}
			if (gm instanceof DestroyComponentMessage) {
				DestroyComponentMessage m = (DestroyComponentMessage) gm;
				factory.destroyComponent(m.getComponent());
			}
		}
		messages = new ArrayList<GameMessage>();
		// switch hour
		if (System.currentTimeMillis() - lastHourSwitch > switchHourTimeout) {
			currentHour = HourSet.values()[random.nextInt(HourSet.values().length)];
			lastHourSwitch = System.currentTimeMillis();
		}
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

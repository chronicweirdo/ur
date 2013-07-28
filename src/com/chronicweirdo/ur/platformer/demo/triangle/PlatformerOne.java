package com.chronicweirdo.ur.platformer.demo.triangle;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * 30 pixels = 1 metre
 * 
 */
public class PlatformerOne {

	private static final String WINDOW_TITLE = "Physics in 2D!";
	private static final int[] WINDOW_DIMENSIONS = { 640, 480 };

	private static final World world = new World(new Vec2(0, -9.8f), false);
	private static final Set<Body> bodies = new HashSet<Body>();
	private static Body player;
	private static double sightRotation = 100;
	private static double sightX = 0;
	private static double sightY = 0;
	private static float projectileSpeed = 1;

	private static class Player extends Body {

		public Player(BodyDef bd, World world) {
			super(bd, world);
		}
	}

	private static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (Body body : bodies) {
			if (body.getType() == BodyType.DYNAMIC) {
				drawBody(body);
			}
		}
		drawPlayer();
		drawAimsight();
	}

	private static void drawAimsight() {
		glPushMatrix();
		glColor3f(1, 0, 0);
		Vec2 position = player.getWorldCenter().mul(30);
		glTranslatef(position.x, position.y, 0);
		/*System.out.println((Mouse.getX()/2) + " " + position.x + " "
				+ " " + (Mouse.getY()/2) + " " + position.y);*/
		float dx = Mouse.getX()/2 - position.x;
		float dy = Mouse.getY()/2 - position.y;
		float l = (float) Math.sqrt(dx*dx + dy*dy);
		dx = dx / l * 10;
		dy = dy / l * 10;
		/*sightRotation = Math.atan2(Mouse.getY() - position.y, -Mouse.getX()
				+ position.x);
		double deg = Math.toDegrees(sightRotation);
		// System.out.println(sightRotation + " " + deg);
		glRotated(90 - deg, 0, 0, 1);*/
		glLineWidth(projectileSpeed);
		glBegin(GL_LINES);
		glVertex2f(0, 0);
		glVertex2f(dx, dy);
		glEnd();
		
		glPopMatrix();
	}

	private static void drawBody(Body body) {
		glPushMatrix();

		Vec2 bodyPosition = body.getPosition().mul(30);
		glTranslatef(bodyPosition.x, bodyPosition.y, 0);
		glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
		if (body.getFixtureList().getShape() instanceof PolygonShape) {
			PolygonShape shape = (PolygonShape) body.getFixtureList()
					.getShape();
			Vec2[] vertices = shape.getVertices();
			glColor3f(1, 1, 1);
			glBegin(GL_QUADS);
			for (Vec2 v : vertices) {
				glVertex2f(v.x * 30, v.y * 30);
			}
			glEnd();
		} else if (body.getFixtureList().getShape() instanceof CircleShape) {
			CircleShape shape = (CircleShape) body.getFixtureList().getShape();
			glColor3f(0, 1, 0);
			glLineWidth(1);
			glBegin(GL_LINE_LOOP);

			for (int i = 0; i <= 100; i++) {
				double degInRad = Math.toRadians(i);
				double angle = i * 2 * Math.PI / 100;
				double x = (Math.cos(angle)
						* shape.m_radius) * 30;
				double y = (Math.sin(angle)
								* shape.m_radius) * 30;
				glVertex2f((float) x, (float) y);
			}

			/*
			 * for (int i = 0; i < 100; i++) { double angle = i * 2 * Math.PI /
			 * 100; glVertex2f(x + (cos(angle) * radius), y + (sin(angle) *
			 * radius)); }
			 */
			glEnd();
		}
		// glRectf(-0.75f * 30, -0.75f * 30, 0.75f * 30, 0.75f * 30);
		glPopMatrix();
	}
	private static void drawPlayer() {
		Body body = player;
		glPushMatrix();
		
		Vec2 bodyPosition = body.getPosition().mul(30);
		glTranslatef(bodyPosition.x, bodyPosition.y, 0);
		glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
		
			PolygonShape shape = (PolygonShape) body.getFixtureList()
					.getShape();
			Vec2[] vertices = shape.getVertices();
			glColor3f(.5f, 1, .5f);
			glBegin(GL_QUADS);
			for (Vec2 v : vertices) {
				glVertex2f(v.x * 30, v.y * 30);
			}
			glEnd();
		
		// glRectf(-0.75f * 30, -0.75f * 30, 0.75f * 30, 0.75f * 30);
		glPopMatrix();
	}

	private static void logic() {
		world.step(1 / 60f, 8, 3);
	}

	private static void createBall() {
		Vec2 position = player.getWorldCenter().mul(30);
		Float dx = Mouse.getX()/2 - position.x;
		Float dy = Mouse.getY()/2 - position.y;
		float l = (float) Math.sqrt(dx*dx + dy*dy);
		dx = dx / l;
		dy = dy / l;
		
		BodyDef ballDef = new BodyDef();
		float px = (position.x + dx*10)/30;
		float py = (position.y + dy*10)/30;
		System.out.println("--- " + dx + " " + dy + " " + px + " " + py);
		ballDef.position.set(px, py);
		ballDef.type = BodyType.DYNAMIC;

		CircleShape ballShape = new CircleShape();
		ballShape.m_radius = 0.1f;

		Body ball = world.createBody(ballDef);
		ball.setBullet(true);
		FixtureDef ballFixture = new FixtureDef();
		ballFixture.density = 0.1f;
		ballFixture.shape = ballShape;
		ball.createFixture(ballFixture);
		bodies.add(ball);
		
		// apply force on ball
		// get directional vector
		dx = dx * projectileSpeed;
		dy = dy * projectileSpeed;
		ball.applyForce(new Vec2(dx.intValue(), dy.intValue()), ball.getWorldCenter());
	}

	private static void input() {
		// create new object
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_C:
					Vec2 bodyPosition = new Vec2(Mouse.getX(), Mouse.getY())
							.mul(0.5f).mul(1 / 30f);
					BodyDef boxDef = new BodyDef();
					boxDef.position.set(bodyPosition);
					boxDef.type = BodyType.DYNAMIC;
					PolygonShape boxShape = new PolygonShape();
					boxShape.setAsBox(0.25f, 0.75f);
					Body box = world.createBody(boxDef);
					FixtureDef boxFixture = new FixtureDef();
					boxFixture.density = 0.1f;
					boxFixture.shape = boxShape;
					box.createFixture(boxFixture);
					bodies.add(box);
					break;
				}
			}
		}
		/*
		 * sightX = Mouse.getX(); sightY = Mouse.getY(); int mdx =
		 * Mouse.getDX(); int mdy = Mouse.getDY(); System.out.println("mouse " +
		 * mdx + " " + mdy); if (mdx != 0 && mdy != 0) { sightRotation -= mdx *
		 * 0.01; //Math.atan((double) mdx / mdy); }
		 */
		if (Mouse.isButtonDown(0)) {
			if (projectileSpeed < 10) {
				projectileSpeed += 0.1;
			}
		} else {
			if (projectileSpeed > 1) {
				// fire projectile
				createBall();
				projectileSpeed = 1;
			}
		}
		// move existing objects
		/*
		 * for (Body body : bodies) { if (body.getType() == BodyType.DYNAMIC) {
		 * if (Keyboard.isKeyDown(Keyboard.KEY_A) &&
		 * !Keyboard.isKeyDown(Keyboard.KEY_D)) {
		 * body.applyAngularImpulse(+0.01f); } else if
		 * (Keyboard.isKeyDown(Keyboard.KEY_D) &&
		 * !Keyboard.isKeyDown(Keyboard.KEY_A)) {
		 * body.applyAngularImpulse(-0.01f); } if (Mouse.isButtonDown(0)) { Vec2
		 * mousePosition = new Vec2(Mouse.getX(), Mouse.getY()).mul(0.5f).mul(1
		 * / 30f); Vec2 bodyPosition = body.getPosition(); Vec2 force =
		 * mousePosition.sub(bodyPosition); body.applyForce(force,
		 * body.getPosition()); } } }
		 */
		// move player
		if (Keyboard.isKeyDown(Keyboard.KEY_A)
				&& !Keyboard.isKeyDown(Keyboard.KEY_D)) {
			player.applyForce(new Vec2(-1, 0), player.getWorldCenter());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)
				&& !Keyboard.isKeyDown(Keyboard.KEY_A)) {
			player.applyForce(new Vec2(1, 0), player.getWorldCenter());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			player.applyForce(new Vec2(0, 1), player.getWorldCenter());
		}
	}

	private static void cleanUp(boolean asCrash) {
		Display.destroy();
		System.exit(asCrash ? 1 : 0);
	}

	private static void setUpMatrices() {
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, 320, 0, 240, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private static void setUpObjects() {
		setUpWalls();
		setUpPlayer();
	}

	private static void setUpPlayer() {
		// Vec2 bodyPosition = new Vec2(Mouse.getX(),
		// Mouse.getY()).mul(0.5f).mul(1 / 30f);
		// System.out.println("setting up player");
		BodyDef boxDef = new BodyDef();
		boxDef.position.set(1, 1);
		boxDef.type = BodyType.DYNAMIC;
		boxDef.linearDamping = 0.5f;

		PolygonShape shape = new PolygonShape();
		Vec2[] points = new Vec2[3];
		points[0] = new Vec2(0f, 0f);
		points[1] = new Vec2(1f, 0f);
		points[2] = new Vec2(0.5f, 0.866f);
		shape.set(points, points.length);
		// shape.setAsBox(0.25f, 0.75f);

		Body box = world.createBody(boxDef);
		FixtureDef boxFixture = new FixtureDef();
		boxFixture.density = 0.1f;
		boxFixture.shape = shape;
		box.createFixture(boxFixture);
		// bodies.add(box);
		player = box;
	}

	private static void setUpWalls() {
		createStaticObject(0d, 0d, 1000d, 0d); // ground
		createStaticObject(0d, 0d, 0d, 1000d); // left wall
		createStaticObject(320d / 30, 0d, 0d, 1000d); // right wall
		createStaticObject(0d, 240d / 30, 1000d, 0d); // ceiling
	}

	private static void createStaticObject(Double positionX, Double positionY,
			Double width, Double height) {
		BodyDef definition = new BodyDef();
		definition.position.set(positionX.floatValue(), positionY.floatValue());
		definition.type = BodyType.STATIC;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width.floatValue(), height.floatValue());

		Body object = world.createBody(definition);

		FixtureDef fixture = new FixtureDef();
		fixture.density = 1;
		fixture.restitution = 0.3f;
		fixture.shape = shape;

		object.createFixture(fixture);
	}

	private static void update() {
		Display.update();
	}

	private static void enterGameLoop() {
		while (!Display.isCloseRequested()) {
			render();
			logic();
			input();
			update();
		}
	}

	private static void setUpDisplay() {
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

	public static void main(String[] args) {
		setUpDisplay();
		setUpObjects();
		setUpMatrices();
		enterGameLoop();
		cleanUp(false);
	}
}

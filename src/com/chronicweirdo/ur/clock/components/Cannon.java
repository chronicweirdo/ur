package com.chronicweirdo.ur.clock.components;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.springframework.beans.factory.annotation.Autowired;

import com.chronicweirdo.ur.clock.Globals;
import com.chronicweirdo.ur.clock.Main;

public class Cannon implements GameComponent, InputHandler {

	@Autowired
	private Globals globals;

	@Autowired
	private ComponentFactory factory;

	@Autowired
	private Main main;

	private float x;
	private float y;
	private float speed = 1;
	private float size = .5f;
	private long lastShoot = 0;
	private long timeout = 200;

	public Cannon(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void render() {
		glPushMatrix();
		glColor3f(1, 0, 0);
		Vec2 position = new Vec2(this.x, this.y).mul(globals.d());
		glTranslatef(position.x, position.y, 0);

		Vec2 nv = getNormalizedDirectionVector().mul(speed/2);
		// draw cannon
		glLineWidth(this.size * 10);
		glBegin(GL_LINES);
		glVertex2f(0, 0);
		glVertex2f(nv.x, nv.y);
		glEnd();

		glPopMatrix();
	}

	private Vec2 getNormalizedDirectionVector() {
		Vec2 position = new Vec2(this.x, this.y).mul(globals.d());

		// get direction vector
		float dx = Mouse.getX() / 2 - position.x;
		float dy = Mouse.getY() / 2 - position.y;

		// normalize vector
		float l = (float) Math.sqrt(dx * dx + dy * dy);
		dx = dx / l * 10;
		dy = dy / l * 10;

		return new Vec2(dx, dy);
	}

	@Override
	public void input() {
		if (System.currentTimeMillis() > lastShoot + timeout) {
			/*
			 * if (Keyboard.isKeyDown(Keyboard.KEY_E)) { fire(); }
			 */

			if (Mouse.isButtonDown(0)) {
				if (speed < 10) {
					speed += 0.1;
				}
			} else {
				if (speed > 1) {
					// fire projectile
					fire();
					speed = 1;
				}
			}
			if (Mouse.isButtonDown(1)) {
				if (size < 2) {
					size += 0.1;
				}
			} else {
				if (size > .5f) {
					size -= 0.1;
				}
			}
		}
	}

	private void fire() {
		// create a ball and send it in the direction of the current
		// vector
		Ball ball = factory.ball(main.getCurrentHour(), this.x, this.y, size);
		Vec2 nv = getNormalizedDirectionVector();
		nv = nv.mul(speed);
		ball.getBody().applyForce(nv, ball.getBody().getWorldCenter());
		lastShoot = System.currentTimeMillis();
	}

}

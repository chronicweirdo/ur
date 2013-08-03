package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Mouse;
import org.springframework.beans.factory.annotation.Autowired;

public class Cannon implements GameComponent, InputHandler {

	@Autowired
	private Globals globals;
	private float x;
	private float y;
	private float projectileSpeed = 1;
	
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

		// get direction vector
		float dx = Mouse.getX()/2 - position.x;
		float dy = Mouse.getY()/2 - position.y;
		
		// normalize vector
		float l = (float) Math.sqrt(dx*dx + dy*dy);
		dx = dx / l * 10;
		dy = dy / l * 10;
		
		// draw cannon
		glLineWidth(this.projectileSpeed);
		glBegin(GL_LINES);
		glVertex2f(0, 0);
		glVertex2f(dx, dy);
		glEnd();
		
		glPopMatrix();
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}

}

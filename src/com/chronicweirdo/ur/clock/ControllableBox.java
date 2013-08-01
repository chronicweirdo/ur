package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ControllableBox extends Box implements InputHandler {

	public ControllableBox(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
	}

	@Override
	public void input() {
		//this.body.setActive(true);
		//System.out.println(this.body.isActive());
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			//System.out.println("space");
			//System.out.println(this.body.getWorldCenter());
			System.out.println(this.body.getAngle());
			this.body.setAngularDamping(Float.MAX_VALUE);
			//if (Math.abs(this.body.getAngle()) > 0.1) this.body.applyAngularImpulse(10);
			this.body.applyLinearImpulse(new Vec2(0, .5f), this.body.getWorldCenter());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			//System.out.println("a");
			this.body.applyLinearImpulse(new Vec2(-0.5f, 0), this.body.getWorldCenter());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			//System.out.println("d");
			this.body.applyLinearImpulse(new Vec2(0.5f, 0), this.body.getWorldCenter());
		}
	}
}

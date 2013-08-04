package com.chronicweirdo.ur.clock.components;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.common.Settings;


public class Bizkit extends DynamicBody {

	protected PolygonShape shape;
	protected FixtureDef fixture;

	protected Bizkit(float x, float y) {
		super(x, y);
		
		Settings.maxPolygonVertices = 128;

		shape = new PolygonShape();
		Vec2[] points = new Vec2[46];
		points[0] = new Vec2(89.5625f, 596.03125f);
		points[1] = new Vec2(83.3125f, 596.53125f);
		points[2] = new Vec2(77.15625f, 597.84375f);
		points[3] = new Vec2(71.21875f, 599.90625f);
		points[4] = new Vec2(65.59375f, 602.71875f);
		points[5] = new Vec2(60.34375f, 606.1875f);
		points[6] = new Vec2(55.59375f, 610.28125f);
		points[7] = new Vec2(51.375f, 614.96875f);
		points[8] = new Vec2(47.8125f, 620.125f);
		points[9] = new Vec2(44.875f, 625.6875f);
		points[10] = new Vec2(42.6875f, 631.59375f);
		points[11] = new Vec2(41.25f, 637.71875f);
		points[12] = new Vec2(40.59375f, 643.96875f);
		points[13] = new Vec2(40.75f, 650.25f);
		points[14] = new Vec2(41.65625f, 656.46875f);
		points[15] = new Vec2(43.34375f, 662.5f);
		points[16] = new Vec2(45.78125f, 668.3125f);
		points[17] = new Vec2(48.9375f, 673.75f);
		points[18] = new Vec2(52.71875f, 678.75f);
		points[19] = new Vec2(57.125f, 683.25f);
		points[20] = new Vec2(62.0625f, 687.15625f);
		points[21] = new Vec2(67.4375f, 690.40625f);
		points[22] = new Vec2(73.15625f, 692.96875f);
		points[23] = new Vec2(79.1875f, 694.78125f);
		points[24] = new Vec2(85.375f, 695.8125f);
		points[25] = new Vec2(91.65625f, 696.09375f);
		points[26] = new Vec2(92.84375f, 696f);
		points[27] = new Vec2(92.84375f, 762.375f);
		points[28] = new Vec2(187.15625f, 762.375f);
		points[29] = new Vec2(187.15625f, 655.21875f);
		points[30] = new Vec2(139.75f, 655.21875f);
		points[31] = new Vec2(139.9375f, 654.40625f);
		points[32] = new Vec2(140.59375f, 648.15625f);
		points[33] = new Vec2(140.46875f, 641.875f);
		points[34] = new Vec2(139.5625f, 635.65625f);
		points[35] = new Vec2(137.875f, 629.59375f);
		points[36] = new Vec2(135.4375f, 623.8125f);
		points[37] = new Vec2(132.28125f, 618.34375f);
		points[38] = new Vec2(128.5f, 613.34375f);
		points[39] = new Vec2(124.09375f, 608.875f);
		points[40] = new Vec2(119.15625f, 604.96875f);
		points[41] = new Vec2(113.78125f, 601.6875f);
		points[42] = new Vec2(108.0625f, 599.15625f);
		points[43] = new Vec2(102.03125f, 597.34375f);
		points[44] = new Vec2(95.84375f, 596.28125f);
		points[45] = new Vec2(89.5625f, 596.03125f);
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i].mul(1/60);
		}
		System.out.println(points.length);
		shape.set(points, points.length);
	}

	@Override
	protected void init() {
		this.body = globals.world().createBody(this.definition);

		this.fixture = new FixtureDef();
		this.fixture.density = 0.1f;
		this.fixture.restitution = .95f;
		this.fixture.shape = this.shape;
		this.body.createFixture(this.fixture);
	}

	@Override
	public void render() {
		if (globals != null) {
			glPushMatrix();

			Vec2 bodyPosition = this.body.getPosition().mul(globals.d());
			glTranslatef(bodyPosition.x, bodyPosition.y, 0);
			glRotated(Math.toDegrees(this.body.getAngle()), 0, 0, 1);
			
			Vec2[] vertices = this.shape.getVertices();
			glColor3f(1, 1, 1);
			glBegin(GL_QUADS);
			for (Vec2 v : vertices) {
				glVertex2f(v.x * globals.d(), v.y * globals.d());
			}
			glEnd();
			glPopMatrix();
		}
	}

}

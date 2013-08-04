package com.chronicweirdo.ur.clock;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chronicweirdo.ur.clock.components.ComponentFactory;
import com.chronicweirdo.ur.clock.components.Hour.HourSet;

public class MyContactListener implements ContactListener {

	private ComponentFactory factory;
	
	public MyContactListener(ComponentFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public void beginContact(Contact contact) {
		System.out.println("contact detected");
		Vec2 position = contact.getFixtureA().getBody().getPosition();
		HourSet uda = null, udb = null;
		if (contact.getFixtureA().getUserData() != null) {
			if (contact.getFixtureA().getUserData() instanceof HourSet) {
				uda = (HourSet) contact.getFixtureA().getUserData();
			}
		}
		if (contact.getFixtureB().getUserData() != null) {
			if (contact.getFixtureB().getUserData() instanceof HourSet) {
				udb = (HourSet) contact.getFixtureB().getUserData();
			}
		}
		if (uda != null && udb != null) {
			if (uda.equals(udb)) {
				factory.box(position.x, position.y, .5f, 1f);
			}
		}
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}

}
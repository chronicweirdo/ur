package com.chronicweirdo.ur.clock.components;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.chronicweirdo.ur.clock.Globals;
import com.chronicweirdo.ur.clock.Hour;

@Component
public class ComponentFactory implements ApplicationContextAware {

	@Autowired
	private Globals globals;
	
	private ApplicationContext context;
	
	private Set<GameComponent> components;
	private Set<InputHandler> handlers;
	
	private ComponentFactory() {
		this.components = new HashSet<GameComponent>();
		this.handlers = new HashSet<InputHandler>();
	}

	public Set<GameComponent> components() {
		return this.components;
	}
	
	public Set<InputHandler> handlers() {
		return this.handlers;
	}
	
	private void add(GameComponent component) {
		this.components.add(component);
		if (component instanceof InputHandler) {
			this.handlers.add((InputHandler) component);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	
	public Ball ball(Hour hour, float x, float y, float radius) {
		return (Ball) autowire(new Ball(hour, x, y, radius));
	}
	
	public Box box(float x, float y, float width, float height) {
		return (Box) autowire(new Box(x, y, width, height));
	}
	
	public Cannon cannon(float x, float y) {
		return (Cannon) autowire(new Cannon(x, y));
	}
	
	public HourPin hourPin(Hour hour, float x, float y) {
		return (HourPin) autowire(new HourPin(hour, x, y));
	}
	public Bizkit bizkit(float x, float y) {
		return (Bizkit) autowire(new Bizkit(x, y));
	}
	
	public ControllableBox controllableBox(float x, float y, float width, float height) {
		return (ControllableBox) autowire(new ControllableBox(x, y, width, height));
	}
	
	private Object autowire(GameComponent bean) {
		this.context.getAutowireCapableBeanFactory().autowireBean(bean);
		this.context.getAutowireCapableBeanFactory().initializeBean(bean, UUID.randomUUID().toString());
		add(bean);
		return bean;
	}
	
	public Bomb bomb(float x, float y, float radius, float blastRadius,
			long blastTimeout) {
		return (Bomb) autowire(new Bomb(x, y, radius, blastRadius, blastTimeout));
	}
	
	public Wall wall(float x, float y, float width, float height) {
		return (Wall) autowire(new Wall(x, y, width, height));
	}

	/*public void destroyByBomb(float x, float y, float r) {
		for (GameComponent c: this.components) {
			if (c instanceof Destroyable) {
				Body body = c.getBody();
				Vec2 p = body.getPosition();
				double d = Math.sqrt(Math.pow(Math.abs(p.x-x), 2) + Math.pow(Math.abs(p.y-y),2));
				System.out.println(d + " " + r);
				if (d <= r) {
					System.out.println(c.getBody());
					globals.world().destroyBody(c.getBody());
					components.remove(c);
				}
			}
		}
	}*/
	public void destroyComponent(GameComponent component) {
		System.out.println(component.getBody());
		globals.world().destroyBody(component.getBody());
		components.remove(component);
	}
	public void destroyBox() {
		for (GameComponent c: this.components) {
			if (c instanceof Box) {
				globals.world().destroyBody(((Box) c).getBody());
				components.remove(c);
				return;
			}
		}
	}
}

package com.chronicweirdo.ur.clock;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GameComponentFactory implements ApplicationContextAware {

	@Autowired
	private Globals globals;
	
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	
	public Ball ball(float x, float y, float radius) {
		return (Ball) autowire(new Ball(x, y, radius));
	}
	
	public Box box(float x, float y, float width, float height) {
		return (Box) autowire(new Box(x, y, width, height));
	}
	
	public Cannon cannon(float x, float y) {
		return (Cannon) autowire(new Cannon(x, y));
	}
	
	public ControllableBox controllableBox(float x, float y, float width, float height) {
		return (ControllableBox) autowire(new ControllableBox(x, y, width, height));
	}
	
	private Object autowire(Object bean) {
		this.context.getAutowireCapableBeanFactory().autowireBean(bean);
		return bean;
	}
	
	public Wall wall(float x, float y, float width, float height) {
		return (Wall) autowire(new Wall(x, y, width, height));
	}
}

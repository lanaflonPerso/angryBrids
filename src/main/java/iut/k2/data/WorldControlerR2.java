package iut.k2.data;

import iut.k2.data.objects.Entity;
import iut.k2.data.objects.Obstacle;
import iut.k2.data.objects.Pecker;
import iut.k2.data.objects.Rocher;
import iut.k2.data.objects.Shapes.Circle;
import iut.k2.physics.Coordinate2D;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nicolas Beaussart on 13/10/15 for angryBrids.
 */
public class WorldControlerR2 extends AbstractWorldControler {
	private final static Logger LOG = LoggerFactory
			.getLogger(WorldControlerR2.class);
	private final static int NMB_RUNS = 10;
	private final long TIME_MAX_MS = 15000;
	private long timeBreak = 1000;
	private boolean endingGame = false;
    private boolean birdgrap = false;
    private Coordinate2D mouseCoordinate = new Coordinate2D(70,0);
	
	public WorldControlerR2(@Nonnull Level level) {
		super(level);
	}

    /**
     * Set the velocity of the bird
     */
	public void bouncyBird() {

		for (Entity e : getLevel().getLsEntitys()) {
			if (e instanceof Pecker) {
				LOG.trace("pecker pos : {}", e.getCoordinate());
				if (e.getCoordinate().getY() < 0) {
					e.getCoordinate().setY(0);
					if (e.getVelocity().getY() >= -10) {
						e.getAcceleration().setX(0);
						e.getAcceleration().setY(0);
						e.getVelocity().setX(0);
						e.getVelocity().setY(0);
					} else {
						e.getVelocity().setY(-e.getVelocity().getY() / 2);
					}
					e.getVelocity().setX(e.getVelocity().getX() / 1.2);
				}
			}
		}
	}

    /**
     * Check if there is any collisions between entity
     */
	public void checkColisions() {

		List<Entity> lsEntity = getLevel().getLsEntitys();
		for (Entity e : lsEntity) {
			for (Entity e2 : lsEntity) {
				if (e == e2) {
					continue;
				}
				if (e.getClass() == e2.getClass()) {
					continue;
				}
				if (e.overlap(e2) /*&& (e instanceof Pecker || e2 instanceof Pecker)*/ ) {
					LOG.debug("Found colision ! e1 : {} ; e2 : {}", e, e2);
					e.setColor(Color.GREEN);
					e2.setColor(Color.GREEN);
					if(e instanceof Rocher || e2 instanceof Rocher){
						if(e instanceof Pecker){
							e.setVelocity(new Coordinate2D(0, 0));
							e.setTerminalVelocity(new Coordinate2D(10000.0f, 10000.0f));
						}
						else if(e2 instanceof Pecker){
							e2.setVelocity(new Coordinate2D(0, 0));
							e2.setTerminalVelocity(new Coordinate2D(10000.0f, 10000.0f));
						}
					}
					else if(e instanceof Obstacle){
						e.setTerminalVelocity(new Coordinate2D(1000.0f, 1000.0f));
						e.setFriction(new Coordinate2D(.005, 0));
						e.setAcceleration(new Coordinate2D(0.0f, -0.25f));
			        e.setVelocity(new Coordinate2D(10, 10));
					}
					else if (e2 instanceof Obstacle){
						e2.setTerminalVelocity(new Coordinate2D(1000.0f, 1000.0f));
						e2.setFriction(new Coordinate2D(.005, 0));
						e2.setAcceleration(new Coordinate2D(0.0f, -0.25f));
						e2.setVelocity(new Coordinate2D(10, 10));					}

					//endingGame = true;
					//timeBreak = 2000;
				}
			}
		}
		bouncyBird();
	}

	@Override
	public Coordinate2D getMouseCoordinate() {
		// TODO Auto-generated method stub
		return mouseCoordinate;
	}

	public void handleDebugInput() {
		if (getKeyMap().getKey(KeyEvent.VK_D)) {
			for (Entity entity : getLevel().getLsEntitys()) {
				LOG.trace("Entity : {}", entity.getCoordinate());
			}
		}
	}

	public void handleInput() {
		handleDebugInput();
	}

	@Override
	public boolean isBirdGrabbed() {
		return birdgrap;
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		// TODO Auto-generated method stub
	}

	/**
	 * Check if the bird have been dragged from the starting position
	 *
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

		mouseCoordinate = new Coordinate2D(e.getPoint().getX(), e.getPoint().getY());
		if (new Ellipse2D.Double(24, 554, 46, 46).contains(e.getX(), e.getY()))
			birdgrap = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

    /**
     * Wait for a drop when the bird has been dragged successfully and set the
     * velocity of the bird which will start moving
     *
     * @param e
     */
	@Override
	public void mouseReleased(MouseEvent e) {

		if (birdgrap) {
			double x = e.getX();
			double y = (590 - e.getY()) * 1.75;
			for (Entity en : getLevel().getLsEntitys()) {
				if(en instanceof Pecker){
				en.setTerminalVelocity(new Coordinate2D(1000.0f, 1000.0f));
				en.setFriction(new Coordinate2D(.005, 0));
				en.setAcceleration(new Coordinate2D(0.0f, -0.25f));
				en.setVelocity(new Coordinate2D(x, y));
				}
			}
			birdgrap = false;
		}
	}

	/**
	 * render on all renderer
	 */
	public void render() {
		for (WorldRenderer worldRenderer : getWorldRenderers()) {
			worldRenderer.render();
		}
	}

	@Override
	public void run() {
		int nmbRuns = 0;
		Timer timer = new Timer("loop");
		while (nmbRuns < NMB_RUNS) {
			getLevel().init();

			// keep looping round till the game ends
			timer.scheduleAtFixedRate(new RunTimer(), 0, 10);
			while (isGameRunning()) {
			}
			timer.purge();
			render();

			LOG.debug("Timer is over !");
			nmbRuns++;
			setGameRunning(true);

			try {
				Thread.sleep(timeBreak);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeBreak = 1000;
		}
		timer.cancel();
	}

	/**
	 * update the level
	 *
	 * @param deltaTime
	 *            the time between two frames
	 */
	public void update(float deltaTime) {
		getLevel().update(deltaTime);
	}

	private class RunTimer extends TimerTask {
		long lastLoopTime = System.currentTimeMillis();
		boolean isRuning = true;
		long cumul = 0;

		@Override
		public void run() {
			if (!isRuning) {
				return;
			}
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			cumul += delta;

			LOG.trace("Handling input");
			handleInput();
			LOG.trace("Updating the game with {} of delta time", delta);
			update(delta);
			LOG.trace("Updating collisions");
			checkColisions();
			LOG.trace("Rendering !");
			render();
			if (cumul >= TIME_MAX_MS) {
				endingGame = true;
			}
			if (endingGame) {
				LOG.debug("Found out the game is over, purging the timer");
				endingGame = false;
				isRuning = false;
				setGameRunning(false);
				cancel();
			}
		}
	}

}

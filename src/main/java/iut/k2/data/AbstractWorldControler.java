package iut.k2.data;

import iut.k2.util.KeyMap;
import iut.k2.util.loggin.UtilLog;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Nicolas Beaussart on 13/10/15 for angryBrids.
 */
public abstract class AbstractWorldControler implements Runnable {
    private final static Logger LOG = UtilLog.getLog(AbstractWorldControler.class.getName());
    private final KeyMap keyMap;
    private final Level level;
    private final List<WorldRenderer> worldRenderers;
    volatile private boolean gameRunning = true;

    public AbstractWorldControler(@Nonnull Level level) {
        this.keyMap = new KeyMap();
        this.level = checkNotNull(level);
        worldRenderers = new ArrayList<>();
    }

    public boolean addAllRenderer(Collection<? extends WorldRenderer> c) {
        return worldRenderers.addAll(c);
    }

    public boolean addRenderer(WorldRenderer worldRenderer) {
        return worldRenderers.add(worldRenderer);
    }

    public boolean containsAllRenderer(Collection<?> c) {
        return worldRenderers.containsAll(c);
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }

    public Level getLevel() {
        return level;
    }

    public List<WorldRenderer> getWorldRenderers() {
        return worldRenderers;
    }

    abstract public void handleInput();

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
        LOG.finest("Setting game runing to " + gameRunning);
    }

    public boolean removeRenderer(WorldRenderer o) {
        return worldRenderers.remove(o);
    }

    abstract public void render();

    public void update(float deltaTime) {
        level.update(deltaTime);
    }
}

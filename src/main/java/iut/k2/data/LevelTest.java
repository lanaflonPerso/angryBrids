package iut.k2.data;

import iut.k2.CercleVisitor;
import iut.k2.Constants;
import iut.k2.ObstacleFactory;
import iut.k2.SquareVisitor;
import iut.k2.data.objects.AbstractGameObject;
import iut.k2.data.objects.Entity;
import iut.k2.data.objects.Montain;
import iut.k2.data.objects.Obstacle;
import iut.k2.data.objects.Pecker;
import iut.k2.physics.Coordinate2D;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelTest extends Level {
    private final static Logger LOG = LoggerFactory.getLogger(LevelTest.class);
    private Coordinate2D startPos = new Coordinate2D(10, 10);

    public LevelTest() {
        this(new Coordinate2D(70, 0));
    }
    public LevelTest(Coordinate2D source) {
        super();
        LOG.debug("Creating level test");
        startPos = source;
        LOG.debug("Start pos = {}", startPos);
        init();
    }

    /**
     * Getter for property 'startPos'.
     *
     * @return Value for property 'startPos'.
     */
    public Coordinate2D getStartPos() {
        return startPos;
    }

    /**
     * Le level de test contiendra 1 Pecker et 5 à 10 obstacles
     */
    public void init() {
        LOG.debug("Inint test level");
        LOG.debug("Start pos = {}", startPos);
        getLsEntitys().clear();
        getLsObjects().clear();

        Random r = new Random();
        int nbObstacles = r.nextInt(6) + 5;


        addRenderObject(new Montain("sprites/mountain_left.png", new Coordinate2D(-20, -60), 200, 200, 200), -1);
        addRenderObject(new Montain("sprites/mountain_right.png", new Coordinate2D(-90, -30), 150, 150, 150), -2);
        addRenderObject(new Montain("sprites/mountain_left.png", new Coordinate2D(-60, -15), 100, 100, 100), -3);

        //Création de l'oiseau
        //Pecker p = new Pecker(new Coordinate2D(0, 0));

        //Attention à ne pas trop augmenter la pente
        Pecker p = new Pecker(new Coordinate2D(startPos));

        // ajout du piaf dans les données au plan 1
        addRenderObject(p, 1);

        List<AbstractGameObject> listeObstacle = new ArrayList<>();
        for (int i = 0; i < nbObstacles; i++) {
            boolean renew = false;
            Obstacle o = null;            

        	do{
        	renew=false;
            //Coordonnées de l'obstacle
            int x = r.nextInt(Constants.SIZE_WIDE / 2) + Constants.SIZE_WIDE / 2;
            int y = r.nextInt(Constants.SIZE_HEIGHT);
            int forme = new Random().nextInt(3);
            //Création d'un obstacle
            
            try{
            if(forme==0)
            	o = ObstacleFactory.getObstacle("cadre",new Coordinate2D(x, y));
            else if(forme==1)
            	o = ObstacleFactory.getObstacle("balloon",new Coordinate2D(x, y));
            else if(forme==2)
            	o = ObstacleFactory.getObstacle("rocher",new Coordinate2D(x, y));
            
            if(getLsObjects().get(2) != null){
            for(AbstractGameObject obstacle : getLsObjects().get(2)){
            	if(o.overlap((Entity) obstacle)){
            		getLsObjects().get(2).remove(o);
            		renew = true;
            	}
            }
            }
            
 
            
            }catch(Exception e){e.printStackTrace();};
        	}while(renew);
            //Obstacle o = new Obstacle(new Coordinate2D(x, y)/*, moveX, moveY, directionX, directionY*/);
            addRenderObject(o, 2);

        }
        	accept(new CercleVisitor());
        	accept(new SquareVisitor());
    }
}

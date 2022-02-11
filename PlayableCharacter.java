import java.awt.geom.Point2D;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PlayableCharacter {

    private Point2D position2d;
    private Rectangle2D rect2d;

    //Creates character with rectangle that has position (0,0) and size (0,0)
    public PlayableCharacter(){
        this.rect2d = new Rectangle2D.Double();
    }

    //Creates character that is a rectangle with width and height
    //that has its top left corner in point2D
    public PlayableCharacter(double width, double height, Point2D point2D){
        this.position2d = new Point2D.Double(point2D.getX(), point2D.getY());
        this.rect2d = new Rectangle2D.Double(point2D.getX(), point2D.getY(),width,height);
    }
    public void setPosition2d(Point2D p){
        this.position2d = p;
    }

    public Point2D getPosition2d() {
        return position2d;
    }

    public Rectangle2D getRect2d() {
        return rect2d;
    }

    @Override
    public String toString() {
        return "Character{" +
                "rect2d=" + rect2d +
                ", position2d=" + position2d +
                '}';
    }
}


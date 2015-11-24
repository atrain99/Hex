package hex;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import static java.awt.geom.PathIterator.SEG_CLOSE;
import static java.awt.geom.PathIterator.SEG_LINETO;
import static java.awt.geom.PathIterator.SEG_MOVETO;
import static java.awt.geom.PathIterator.WIND_EVEN_ODD;
import java.util.NoSuchElementException;

public class HexagonalPathIterator implements PathIterator {

    private double x, y, r, a;
    
    private int index;
    
    private AffineTransform transformer;

    public HexagonalPathIterator(Hexagon h, AffineTransform at) {
        this.x = h.getXCenter();
        this.y = h.getYCenter();
        this.a = h.getApothem();
        this.r = h.getRadius();
        this.transformer = at;
        if (this.a < 0 || r < 0) {
            index = 7;
        }
    }

    @Override
    public int getWindingRule() {
        return WIND_EVEN_ODD;
    }

    @Override
    public boolean isDone() {
        return this.index > 7;
    }

    @Override
    public void next() {
        index++;
    }

    @Override
    public int currentSegment(float[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("hex iterator out of bounds");
        }
        if (index == 6) {
            return SEG_CLOSE;
        }
        coords[0] = (float) (this.x - this.r);
        coords[1] = (float) (this.y);
        switch(index){
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        if (transformer != null) {
            transformer.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_LINETO);
    }

    @Override
    public int currentSegment(double[] coords) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}

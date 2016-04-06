package segmentos;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.LinkedList;

public class MyLine
{
    public Line2D line;
    public LinkedList<Point> ps=new LinkedList<>();
    
    public MyLine(int x1,int y1, int x2, int y2) 
    {
        line=new Line2D.Float(x1, y1, x2, y2);
        ps.addLast(new Point(x1, y1));
        bresenham();
    }
    
    public void bresenham()
    {
        int x0=(int)line.getX1();
        int y0=(int)line.getY1();
        int x1=(int)line.getX2();
        int y1=(int)line.getY2();
        int x, y, dx, dy, p, incE, incNE, stepx, stepy;
        dx = (x1 - x0);
        dy = (y1 - y0);
       /* determinar que punto usar para empezar, cual para terminar */
        if (dy < 0) { 
          dy = -dy; 
          stepy = -1; 
        } 
        else {
          stepy = 1;
        }

        if (dx < 0) {  
          dx = -dx;  
          stepx = -1; 
        } 
        else {
          stepx = 1;
        }
        x = x0;
        y = y0;
       /* se cicla hasta llegar al extremo de la línea */
        if(dx>dy){
          p = 2*dy - dx;
          incE = 2*dy;
          incNE = 2*(dy-dx);
          while (x != x1){
            x = x + stepx;
            if (p < 0){
              p = p + incE;
            }
            else {
              y = y + stepy;
              p = p + incNE;
            }
            ps.addLast(new Point(x,y));
          }
        }
        else{
          p = 2*dx - dy;
          incE = 2*dx;
          incNE = 2*(dx-dy);
          while (y != y1){
            y = y + stepy;
            if (p < 0){
              p = p + incE;
            }
            else {
              x = x + stepx;
              p = p + incNE;
            }
            ps.addLast(new Point(x,y));
          }
        }
    }
}

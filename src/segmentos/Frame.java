package segmentos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame extends JFrame
{
    private File in=new File("./in.txt");
    private File out=new File("./out.txt");
    private LinkedList<MyLine> conjunto=new LinkedList<>();
    private int index[],max=0;
    private Line2D DAONE=new Line2D.Float();
    public int size;
    
    public Frame() throws HeadlessException, FileNotFoundException, IOException 
    {
        setTitle("Segmentos");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        fill();
        index=new int[conjunto.size()];
        stuff();
        ans();
        setVisible(true);
    }
    //leer el archivo de entrada
    private void fill() throws FileNotFoundException, IOException
    {
        FileReader fr=new FileReader(in);
        BufferedReader bfr=new BufferedReader(fr);
        int sizex=220, sizey=220;
        bfr.readLine();//No es nesesario ingresar el numero de segmentos en el archivo, pero se coloco esta sentencia para mantener la convencion de entrada
        for(String s=bfr.readLine();s!=null;s=bfr.readLine())
        {
            int x1,y1,x2,y2;
            x1= Integer.parseInt(s.substring(0,s.indexOf(" ")));
            if(x1>sizex)    
                sizex=x1;
            s=s.substring(s.indexOf(" ")+1);
            y1= Integer.parseInt(s.substring(0,s.indexOf(" ")));
            if(y1>sizex)    
                sizey=y1;
            s=s.substring(s.indexOf(" ")+1);
            x2= Integer.parseInt(s.substring(0,s.indexOf(" ")));
            if(x2>sizex)    
                sizex=x2;
            s=s.substring(s.indexOf(" ")+1);
            y2= Integer.parseInt(s);
            if(y2>sizex)    
                sizey=y2;
            conjunto.add(new MyLine(x1,y1,x2,y2));
        }
        bfr.close();
        if(conjunto.size()==0)
        {
            JOptionPane.showMessageDialog(this, "No se ingreso ningun segmento","ERROR",NORMAL);
            System.exit(0);
        }
        setSize(sizex+10, sizey+35);//el tamaño del frame varia segun el mayor componente
        size=(int)Math.sqrt((sizex*sizex)+(sizey*sizey));//hipotenusa del campo
    }
    //hallar la linea
    private void stuff()
    {
        Line2D f2=new Line2D.Float();
        //recorrer y dibujar todos los segmentos
        for(int i=0;i<conjunto.size();i++)
        {
            //moverse por todos los puntos de un segmento i
            Point p=conjunto.get(i).ps.get(0);
            for(int k=0;k<conjunto.get(i).ps.size();p=conjunto.get(i).ps.get(k),k++)
            {
                //hallar todas las rectas que parten de un punto dado de un segmento dado hasta los bordes del frame  
                for(int a=0;a<180;a++)
                {
                    f2.setLine(p.x+(float)(size*Math.cos(Math.toRadians(a))),p.y+(float)(size*Math.sin(Math.toRadians(a))),p.x+(float)(size*Math.cos(Math.toRadians(a+180))),p.y+(float)(size*Math.sin(Math.toRadians(a+180))));
                    //hallar con que segmentos corta la recta dada
                    int cc=0,aux[]=new int[conjunto.size()];
                    for(int j=0;j<conjunto.size();j++)
                        if(conjunto.get(j).line.intersectsLine(f2))
                            aux[cc++]=j;
                    if(cc>max)
                    {
                        index=aux;
                        max=cc;
                        DAONE.setLine(f2);
                    }
                }
            }
        }
    }
    //rellenar archivo de salida y mostrar lista
    private void ans() throws IOException
    {
        FileWriter fr=new FileWriter(out);
        BufferedWriter br=new BufferedWriter(fr);
        String s="";
        for(int i=0;i<max;i++)
        {
            s=s+"("+(int)conjunto.get(index[i]).line.getX1()+", "+(int)conjunto.get(index[i]).line.getY1()+"), ("+(int)conjunto.get(index[i]).line.getX2()+", "+(int)conjunto.get(index[i]).line.getY2()+")  Linea:"+index[i]+"\r\n";  
        }
        s=s+"\r\n"+max+" de "+conjunto.size()+" lineas conectadas";
        Lista B=new Lista(s, max, 230,getSize().height);
        B.setLocationRelativeTo(rootPane);
        br.append(s);
        br.close();
    }
    //pintar los puntos y la linea
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        g.translate(0, 25);
        //fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getSize().width,getSize().height);
        //linea central
        g.setColor(Color.red);
        g.drawLine((int)DAONE.getX1(), (int)DAONE.getY1(), (int)DAONE.getX2(), (int)DAONE.getY2());
        g.setColor(Color.GRAY);
        for(int i=0;i<conjunto.size();i++,g.setColor(Color.GRAY))
        {
            g.drawString(""+i, (int)conjunto.get(i).line.getX1()-4, (int)conjunto.get(i).line.getY1()-3);
            //punto de partida
            g.setColor(Color.cyan);
            g.fillOval((int)conjunto.get(i).line.getX1()-2, (int)conjunto.get(i).line.getY1()-1, 4, 4);
            //segmento
            g.setColor(Color.BLACK);
            g.drawLine((int)conjunto.get(i).line.getX1(), (int)conjunto.get(i).line.getY1(), (int)conjunto.get(i).line.getX2(), (int)conjunto.get(i).line.getY2());
            //punto de llegada
            g.setColor(Color.blue);
            g.fillOval((int)conjunto.get(i).line.getX2()-2, (int)conjunto.get(i).line.getY2()-1, 4, 4);
        }
        g.setColor(Color.orange);
        //bordes de la linea
        g.drawLine((int)DAONE.getX1()+1, (int)DAONE.getY1(), (int)DAONE.getX2()+1, (int)DAONE.getY2());
        g.drawLine((int)DAONE.getX1()-1, (int)DAONE.getY1(), (int)DAONE.getX2()-1, (int)DAONE.getY2());
    } 
}
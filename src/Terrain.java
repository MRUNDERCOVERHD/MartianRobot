/**
 * Created by Dan on 14/12/2015.
 */


public class Terrain {

    String name;
    int size;
    int bottomleft; //coordiante (0,0)
    int bottomRight; //to set as max for X  so (0,X)
    int topRight; // to set as max value for Y so (X,Y)
    int topLeft; // to set the max value for y so (0,Y)


    public Terrain( int topLeft, int topRight){


        this.topLeft = topLeft;
        this.topRight = topRight;


    }

    public void getTerrainSize(int x, int y){

        size= x * y;
    }
}

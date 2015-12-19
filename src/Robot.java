/**
 * Created by Dan on 14/12/2015.
 */
public class Robot {


    String name;
    int lifetime;
    String currentDirection;
    boolean ping;
    boolean destroyed;
    int coOrdinateX;
    int coOedianteY;
    char direction;
    boolean isLost;
    int terrrainSizeX;
    int terrrainSizeY;

    public Robot (String name, int coOrdinateX, int coOrdinateY,char direction, int terrainx, int terrainy){

        this.name = name;
        this.coOrdinateX =coOrdinateX;
        this.coOedianteY=coOrdinateY;
        this.direction = direction;
        this.terrrainSizeX = terrainx;
        this.terrrainSizeY = terrainy;
    }

    public String initiatecommand(String commandSequence){

        char[] movementChars = commandSequence.toCharArray();

        //(c == 'L' || c == 'F' || c == 'R'
        for (int i = 0; i < commandSequence.length(); i++) {


                char c = commandSequence.charAt(i);
                //Process char

            if (c =='L') {

                if( this.direction=='N'){
                    direction = 'W';
                }else if(this.direction =='E'){
                    direction = 'N';
                }else if(this.direction == 'S'){
                    direction = 'E';
                }else if( this.direction == 'W'){
                    direction = 'S';
                }
                this.lifetime++;

            }else if(c=='R') {

                if (this.direction == 'N') {
                    direction = 'W';
                } else if (this.direction == 'E') {
                    direction = 'S';
                } else if (this.direction == 'S') {
                    direction = 'W';
                } else if (this.direction == 'W') {
                    direction = 'N';
                }

                this.lifetime++;

            }else if(c=='F'){

                    System.out.println("testing 2");
                    if( this.direction=='N'){
                        direction = 'N';
                        coOedianteY++;
                    }else if(this.direction =='E'){
                        direction = 'E';
                        this.coOrdinateX++;
                    }else if(this.direction == 'S'){
                        direction = 'S';
                        this.coOedianteY--;
                    }else if( this.direction == 'W'){
                        direction = 'W';
                        coOrdinateX--;
                    }
                this.lifetime++;

            }
            }

            String finalX = Integer.toString(coOrdinateX);
            String finalY = Integer.toString(coOedianteY);

        if(coOrdinateX<0  || coOedianteY<0 || coOedianteY>terrrainSizeY || coOrdinateX>terrrainSizeX){

            isLost=true;
            String ans = "The Robot is at co ordinates: " + finalX + " for X "+ "and " + finalY + " for Y  and robot is facing " + direction + "the robot is LOST"  ;
            return ans;

        }else {

            String ans = "The Robot is at co ordinates: " + finalX + " for X " + "and " + finalY + " for Y  and robot is facing " + direction;
            return ans;
        }

    }

    public void returnLocationUpdate(){

        String ans = "The Robot is at co ordinates: " + this.coOrdinateX + " for X "+ "and " + this.coOedianteY + " for Y  and robot is facing " + direction  ;


    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifetime() {
        return lifetime;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isPing() {
        return ping;
    }

    public void setPing(boolean ping) {
        this.ping = ping;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}

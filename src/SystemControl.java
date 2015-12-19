import java.util.*;

public class SystemControl {

    boolean end = true;
    Scanner user_input = new Scanner(System.in);
    Scanner string_Input = new Scanner(System.in);
    Map<String, Robot> robotCollection = new HashMap<>();
    Terrain terrain;
    int size_terrainX;
    int size_terrainY;
    char direction;
    ArrayList<String> lostCommads = new ArrayList<>();

    public SystemControl() {

        beginMission();
    }
    public void beginMission() {

        while (end) {

            callSequence();
        }
    }
    public void callSequence() {

        int selection;
        System.out.println("Welcome to the control function, please select an option from the list below");
        System.out.println("1)Enter Terrain Size");
        System.out.println("2)Deploy Robot");
        System.out.println("3)Move Robot");
        System.out.println("4)Get Robot location");
        System.out.println("5)Get Logs");
        System.out.println("6)Destroy Robot");
        System.out.println("7)Exit System");

        try {
            selection = user_input.nextInt();

            String result = "";

            switch (selection) {
                case 1:
                    user_input.nextLine();
                    System.out.println("");
                    result = "Please now enter the (X,Y) values for the terrain size, max value size is 50";//header
                    System.out.println(result);
                    size_terrainX = user_input.nextInt();
                    user_input.nextLine();
                    size_terrainY = user_input.nextInt();
                    user_input.nextLine();

                    if (size_terrainX > 50 || size_terrainY > 50 || size_terrainX < 0 || size_terrainY < 0) {

                        System.out.println("Invalid terrain size please try again");
                        System.out.println("");

                    } else {
                        terrain = new Terrain(size_terrainX, size_terrainY);
                        System.out.println("Values Inserted are: " + terrain.topLeft + "  " + terrain.topRight);
                        System.out.println(" ");
                    }
                    break;
                case 2:
                    if (size_terrainX == 0 || size_terrainY == 0) {
                        System.out.println("Cannot Deploy Robot no terrain has been found");
                        break;
                    } else {
                        while (true) {
                            System.out.println("Please Name your Robot");
                            String roboname = string_Input.nextLine();
                            while(validateName(roboname)==false){
                                System.out.println("Robot name Already exists please re-name your robot");
                                 roboname = string_Input.nextLine();
                            }
                            System.out.println(" ");
                            System.out.println("Please set your co-ordinates for deployment");
                            int deplymentX = user_input.nextInt();
                            int deplymentY = user_input.nextInt();
                            System.out.println("Please enter facing direction (N,E,S,W)");
                            direction = string_Input.next().charAt(0);
                            string_Input.nextLine();

                            while(overLapfound(deplymentX, deplymentY)==true || validateDirection(direction)==false){

                                System.out.println("Robot Overlap has been found or invalid direction");
                                System.out.println("Please enter the correct values again");
                                deplymentX = user_input.nextInt();
                                deplymentY = user_input.nextInt();
                                System.out.println("Please enter facing direction (N,E,S,W)");
                                direction = string_Input.next().charAt(0);
                                string_Input.nextLine();
                            }
                            //check here
                            if (validateCoOrdinats(deplymentX, deplymentY) == false) {

                                while (validateCoOrdinats(deplymentX, deplymentY) == false) {

                                    System.out.println("You have Deployed the robot outside the map region size or invalid direction please try again");
                                    System.out.println("Please set your co-ordinates for deployment");
                                    deplymentX = user_input.nextInt();
                                    deplymentY = user_input.nextInt();
                                    System.out.println("Please enter facing direction (N,E,S,W)");
                                    direction = string_Input.next().charAt(0);
                                }
                            }
                            robotCollection.put(roboname, new Robot(roboname, deplymentX, deplymentY,direction,size_terrainX,size_terrainY));
                            System.out.println("You have entered: " + roboname + " " + "at co-ordinate: " + deplymentX + " " + deplymentY + "facing: " + direction);
                            System.out.println(" ");
                            System.out.println("Do you want to enter another robot?");
                            System.out.println("1) = Yes");
                            System.out.println("2) = No");
                            int roboselection = user_input.nextInt();

                            if (roboselection == 2) {
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    if (robotCollection.isEmpty()) {

                        System.out.println("Cannot move any robots as none have been yet deployed");
                        System.out.println("Please select an option and create a robot for deployment");
                    } else {
                        boolean ext = true;
                        while (ext) {
                            int i = 1;
                            String key="";
                            System.out.println("Please select your robot name from the list:");
                            for (Map.Entry entry : robotCollection.entrySet()) {

                                 key = entry.getKey().toString();
                                System.out.println(Integer.toString(i) + ") " + key);
                                ++i;
                            }
                            String roboChoice = user_input.next();
                            if (checkChoice(roboChoice) == false) {

                                System.out.println("your selection is invalid please re-select your robot name");
                            } else {
                                System.out.println("Please enter the movement commands: L = Left, F = Forward, R = Right");
                                String command = string_Input.next();

                                while (true) {
                                    if (checkMoveCommand(command) == false) {

                                        System.out.println("Invalid command please re-enter: L = Left, F = Forward, R = Right");
                                        command = string_Input.next();
                                    } else {

                                       Robot marks = (Robot) robotCollection.get(key);
                                        String currentx = Integer.toString(marks.coOrdinateX);
                                        String currenty = Integer.toString(marks.coOedianteY);

                                        if(checkLost(command, currentx, currenty)==false){
                                            System.out.println("Cannot execute command as previous robot has been lost by this command");
                                            break;
                                        }
                                        System.out.println( marks.initiatecommand(command));

                                        if(marks.isLost==true){
                                            System.out.println( "The robot is now lost, please create another robot");
                                            lostCommads.add(command);
                                            String lostCoOrdinate =currentx+currenty;
                                            lostCommads.add(lostCoOrdinate);
                                            robotCollection.remove(key);
                                        }

                                    }
                                    break;
                                }

                                break;
                            }

                        }
                        break;
                    }
                    break;
                case 4:
                    if (robotCollection.isEmpty()) {
                        System.out.println("Cannot get any robot information as none have been yet deployed");
                        System.out.println("Please select an option and create a robot for deployment");

                    } else {
                    while (true) {
                        int i = 1;
                        String key = "";
                        System.out.println("Please select your robot name from the list:");
                        for (Map.Entry entry : robotCollection.entrySet()) {
                            key = entry.getKey().toString();
                            System.out.println(Integer.toString(i) + ") " + key);
                            ++i;
                        }
                        String roboChoice = user_input.next();
                        if (checkChoice(roboChoice) == false) {
                            System.out.println("your selection is invalid please re-select your robot name");
                        }else{
                            Robot location = robotCollection.get(roboChoice);
                            int co_ordinatex=location.coOrdinateX;
                            int co_ordinatey=location.coOedianteY;
                            int lifetime = location.lifetime;
                            char face = location.direction;
                            System.out.println(roboChoice + " is currently at co-ordinates: " + co_ordinatex + " for x and " +co_ordinatey+ " for y and is currently facing: " +face );
                            System.out.println(roboChoice + "has a current lifetime of " +lifetime);
                            break;
                        }
                    }
                    }
                    break;
                case 5:
                    System.out.println("The size of the Terrain is: " + terrain);
                    System.out.println("The current Robots available are:");
                    int i =1;
                    for (Map.Entry entry : robotCollection.entrySet()) {
                        String key = entry.getKey().toString();
                        Robot log = robotCollection.get(key);
                        System.out.println(Integer.toString(i) + ") " + key + " , " + "with a lifetime of: " + log.lifetime + " is facing: " + log.direction);
                        ++i;
                    }
                    break;
                case 6:
                    if (robotCollection.isEmpty()) {
                        System.out.println("Cannot delete any robots as none have been yet deployed");
                        System.out.println("Please create a robot for deployment");
                    } else {
                    System.out.println("Please select the Robot to destroy");
                    int j =1;
                        String key="";
                    for (Map.Entry entry : robotCollection.entrySet()) {
                         key = entry.getKey().toString();
                        System.out.println(Integer.toString(j) + ") " + key);
                        j++;

                    }
                        String delChice = user_input.next();
                        if (checkChoice(delChice) == false) {
                            System.out.println("your selection is invalid please re-select your robot name");
                        }else {
                            System.out.println(delChice + ": has been destroyed");
                            robotCollection.remove(delChice);
                        }
                    }
                    break;
                case 7:
                    end = false;
                    break;
            }

        } catch (InputMismatchException e) {
            System.out.println("Incorrect entry please try again.... ");
            System.out.println(" ");
            user_input.nextLine();
        }
    }

    public boolean checkLost(String command, String X, String Y){

        String comb = X+Y;
        for(int i=0; i<lostCommads.size();i++){
            if(lostCommads.get(i).equals(command)){
                if(lostCommads.get(i+1).equals(comb)){
                    return false;
                }
            }
        }
        return true;

    }

    public boolean validateName(String roboname){

        for (Map.Entry entry : robotCollection.entrySet()) {

          String  key = entry.getKey().toString();

            if( roboname.equals(key)){

                return false;
            }

        }

        return true;
    }

    public boolean overLapfound(int a, int b) {

        if (robotCollection.isEmpty() == true) {
            return false; //no overlap
        } else {
            for (Map.Entry entry : robotCollection.entrySet()) {
                String key = entry.getKey().toString();
                Robot values = (Robot) entry.getValue();
                int show1 = values.coOedianteY;
                int show2 = values.coOrdinateX;

                if (show2 == a && show1 == b) {

                    return true; //overlap
                }
            }
            return false;
        }
    }

    public boolean checkMoveCommand(String comand){

        char[] movementChars = comand.toCharArray();
        for (char c : movementChars) {
            if (c == 'L' || c == 'F' || c == 'R' ) {

            }else{
                return false;
            }
        }
        return true;
    }

    public boolean checkChoice(String sel){

        String key;
        for (Map.Entry entry : robotCollection.entrySet()) {
            key = entry.getKey().toString();

            if(sel.equals(key)){
                return true;
            }
        }
        return false;
    }

    public boolean validateDirection(char direction){

        if(direction=='N' || direction=='E' || direction=='S'||direction=='W' ){
            return true;
        }
        return false;
    }


    public boolean validateCoOrdinats(int x, int y){

        if(x > size_terrainX || y > size_terrainY ){
            return false;
        }
        return true;

    }


    public static void main(String[] args) {
        SystemControl mySystem = new SystemControl();
    }
}

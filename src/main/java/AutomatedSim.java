public class AutomatedSim {
    private final Vehicle vehicle;

    static String version = "3.0";

    int burnArray[] = {200, 200, 200, 200, 200, 100, 100, 100, 100, 100, 100, 100, 100, 100, 200,
            200, 200, 200, 100, 100, 100, 100, 100, 100, 100, 100, 100, 199, 99, 9, 200,
            93, 99, 99, 99, 102, 100, 100, 100, 100};//10000ft Altitude


    public AutomatedSim(Vehicle v) {
        this.vehicle = v;
    }


    public String gameHeader() {
        String s = "";
        s = s + "\nMars Simulation - Version " + version + "\n";
        s = s + "Elon Musk has sent a really expensive Starship to land on Mars.\n";
        s = s + "The on-board computer has failed! You have to land the spacecraft manually.\n";
        s = s + "Set burn rate of retro rockets to any value between 0 (free fall) and 200\n";
        s = s + "(maximum burn) kilo per second. Set burn rate every 10 seconds.\n"; /* That's why we have to go with 10 second-steps. */
        s = s + "You must land at a speed of 2 or 1. Good Luck!\n\n";
        return s;
    }

    public String getHeader() {
        String s = "";
        s = s + "\nTime\t";
        s = s + "Velocity\t\t"; s = s + "Fuel\t\t";
        s = s + "Altitude\t\t"; s = s + "Burn\n";
        s = s + "----\t";
        s = s + "-----\t\t";
        s = s + "----\t\t";
        s = s + "------\t\t"; s = s + "----\n";
        return s;
    }

    public void printString(String string) {
// print long strings with new lines the them.
        String[] a = string.split("\r?\n");
        for (String s : a) {
            System.out.println(s);
        }
    }

    public int runAutomatedSim(BurnStream burnSource) {
        DescentEvent status = null;
        int burnInterval = 0;
        printString(gameHeader());
        printString(getHeader());
        while (vehicle.stillFlying()) {
            status = vehicle.getStatus(burnInterval);
            System.out.print(status.toString()+"\t\t");
            vehicle.adjustForBurn(burnSource.getNextBurn(status));
            if (vehicle.outOfFuel()) {
                break;
            }
            burnInterval++;
            if (burnInterval % 9 == 0) {
                printString(getHeader());
            }
        }
        printString(vehicle.checkFinalStatus());
        if (status != null) {
            return status.getStatus();
        }
        return -1;
    }

    public static void main(String[] args) {
        AutomatedSim as = new AutomatedSim(new Vehicle(10000));
        BurnDataStream burnDataStream = new BurnDataStream(as.burnArray);
        as.runAutomatedSim(burnDataStream);
    }

}

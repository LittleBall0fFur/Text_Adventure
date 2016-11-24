/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game
{
    private Parser parser;
    private Player pl;
    private int floor;
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        parser = new Parser();
        pl = Player.getInstance();
        floor = 0;
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("up", lab);

        theatre.setExit("west", outside);
        theatre.setExit("east", pub);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("down", outside);

        office.setExit("west", lab);
        
        //set Items
        Item dagger = new Dagger();
        Item bandage = new Bandage();
        Item Key = new Key();
        
        lab.addRoomInventoryItem(dagger);
        lab.addRoomInventoryItem(bandage);
        lab.addRoomInventoryItem(Key);
        //start game outside 
        pl.setCurrentRoom(outside);
    }
    	

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Sacremento, California, 29 december 1977");
        System.out.println("This adventure is based on real events and is not meant for the faint hearted");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("Welcome detective,");
        System.out.println("we are glad you could help our force out. Follow me to the crime scene");
        System.out.println(pl.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command)
    {
    	boolean wantToQuit = false;
    	 String commandWord = command.getCommandWord();
    	if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
    	if(pl.gethealth() < 1){
    		System.out.println("You are dead, thank you for playing!");
    	}
    	if(pl.gethealth() > 0)
    	{
	        if (commandWord.equals("help"))
	            printHelp();
	        else if (commandWord.equals("go"))
	            goRoom(command);
	        else if(commandWord.equals("lookAround"))
	        	look();
	        else if(commandWord.equals("checkHealth"))
	        	pl.isAlive();
	        else if(commandWord.equals("go up"))
	        	System.out.println("yes");
	        	floor++;
	        if(commandWord.equals("down"))
	        	floor--;
	        if(commandWord.equals("take"))
	        	pickUpItem(command);
	        if(commandWord.equals("inventory"))
	        	System.out.println("Inventory: " + pl.getPlayerInventory().itemDiscriptions() + "\n" + "Max Weight: 100" + "\n" + "Current weight: " + pl.getPlayerInventory().getWeight());
	        if(commandWord.equals("drop"))
	        	dropItem(command);
	        if(commandWord.equals("use")){
	        	useItem(command);
	        }
    	}
    	if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
    	if(command.hasSecondWord()){
    		pl.setDamage(1);
    	}
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = pl.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else if(pl.getCurrentRoom().getShortDescription().equals("in a lecture theatre") && nextRoom.getShortDescription().equals("in the campus pub") && pl.getPlayerInventory().getItem("Key") == null){
        	System.out.println("The door to the east is locked, it needs a key to open it");
        }
        else {
            pl.setCurrentRoom(nextRoom);
            System.out.println(pl.getCurrentRoom().getLongDescription());
        }
    }
    
    public void pickUpItem(Command command){
    	if(command.hasSecondWord()){
    		//if(pl.getCurrentRoom().getRoomInventory().hasItem(command.getSecondWord()) == true){
    			pl.take(command.getSecondWord());
    		//}
    	}
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Which item would you like to take?");
            return;
        }
    }
    public void dropItem(Command command){
    	if(command.hasSecondWord()){
    		//if(pl.getCurrentRoom().getRoomInventory().hasItem(command.getSecondWord()) == true){
    			pl.drop(command.getSecondWord());
    		//}
    	}
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Which item would you like to take?");
            return;
        }
    }
    
    private void useItem(Command command){
    	if(command.hasSecondWord()){
    		//pl.drop(command.getSecondWord());
    		Item usableItem = pl.getPlayerInventory().items.get(command.getSecondWord());
    		System.out.println(usableItem.Use());
    	}
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Which item would you like to use?");
            return;
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }


    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
    public void look(){
    	if(floor > 0){
    		System.out.println(pl.getCurrentRoom().getLongDescription()+ "\n" + "Floor: " + floor +  "/n");
    	}
    	else
    		System.out.println(pl.getCurrentRoom().getLongDescription()+ "\n" + "Floor: Ground floor");
    }
}

package adventure;

import adventure.Room.Type;
import items.Key;
import items.Medicine;
import items.Tool;

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

public class Game
{
    private Parser parser;
    private Player player;
    private Room livingroom, kitchen, hallway1, garage, frontgarden, hallway2, toiletroom, basement, upstairs, bathroom, your_bedroom, parent_bedroom;
    private Key parentBedroomKey;
    private Tool crowbar, wrench;
    private Medicine pill, apple;
    public static Game game; // Created to get the player instance of the game pointer.

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        createItems();
        player = new Player();
        parser = new Parser();
        game = this;
    }

	/**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {

        // create the rooms
        livingroom = new Room("livingroom","in the livingroom", adventure.Room.Type.Inside);
        kitchen = new Room("kitchen","in the kitchen", Type.Inside);
        hallway1 = new Room("hallway1","in the southside of the hallway", Type.Inside);
        garage = new Room("garage","in the garage", Type.Inside);
        frontgarden = new Room("frontgarden","at the front of the house", Type.Outside);
        hallway2 = new Room("hallway2","in the northside of the hallway", Type.Inside);
        toiletroom = new Room("toiletroom","in the toiletroom", Type.Inside);
        basement = new Room("basement","your in the basement.\nTheres a toxic gas hanging around this place", Type.Inside);
        upstairs = new Room("upstairs","in the upstairs", Type.Inside);
        bathroom = new Room("bathroom","in the bathroom", Type.Inside);
        your_bedroom = new Room("your_bedroom","in the your bedroom", Type.Inside);
        parent_bedroom = new Room("parent_bedroom","in the parent bedroom", Type.Inside);

        // initialise rooms
        livingroom.setExit("north", kitchen);
        livingroom.setExit("west", hallway1);
        
        kitchen.setExit("south", livingroom);
        kitchen.setExit("west", hallway2);
        
        hallway1.setExit("north", hallway2);
        hallway1.setExit("east", livingroom);
        hallway1.setExit("south", frontgarden);
        hallway1.setExit("west", toiletroom);
        
        frontgarden.setExit("north", hallway1);
        
        toiletroom.setExit("east", hallway1);
        toiletroom.setExit("down", basement);
        
        hallway2.setExit("east", kitchen);
        hallway2.setExit("south", hallway1);
        hallway2.setExit("up", upstairs);
        hallway2.setExit("down", basement);
        hallway2.setExit("west", garage);
        
        garage.setExit("east", hallway2);
        
        basement.setExit("up", hallway2);
        
        upstairs.setExit("down", hallway2);
        upstairs.setExit("east", parent_bedroom);
        upstairs.setExit("south", bathroom);
        upstairs.setExit("west", your_bedroom);
        
        parent_bedroom.setExit("west", upstairs);
        bathroom.setExit("north", upstairs);
        your_bedroom.setExit("east", upstairs);
        
        parent_bedroom.setLocked(true);
        
        basement.setLocked(true);
        basement.isToxic(true);
    }
    
    private void createItems() {

    	// create the items
    	parentBedroomKey = new Key("Key","can be used to open the door to the parent bedroom");
    	parentBedroomKey.setWeight(0.5f);
    	crowbar = new Tool("Crowbar", "can be used to break open borded-up doors");
    	crowbar.setWeight(12f);
    	wrench = new Tool("Wrench", "can be used to remove bolts");
    	wrench.setWeight(10f);
    	pill = new Medicine("UnknownPill", "<Unknown description>");
    	pill.setWeight(1f);
    	apple = new Medicine("Apple", "can heal you by 10 to 30 HP");
    	apple.setWeight(1f);
    	
        // initialise items
    	parentBedroomKey.setDoor(upstairs, parent_bedroom);
    	
    	crowbar.canBreakOpen(true);
    	crowbar.canBreakDoor(basement, hallway2);
    	crowbar.canBreakOpen(true);
    	
    	wrench.canBreakDoor(toiletroom, basement);
    	
    	pill.isDeadly(true);
    	
    	apple.isOneTimeUse(true);
    	
    	// initialise items to rooms
        basement.addItem(parentBedroomKey);
        basement.addItem(crowbar);
        garage.addItem(wrench);
        parent_bedroom.addItem(pill);
        kitchen.addItem(apple);
	}

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();
        player.goToRoom(livingroom);  // start game in livingroom
        Sound.InHouse.play();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished && player.isAlive()) {
            Command command = parser.getCommand();
            if (command != null){
            	finished = processCommand(command);
            }
        }
        System.out.println("Game over!");
        System.out.println("You died.");
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to this Adventure!");
        System.out.println("Adventure is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equalsIgnoreCase("help")){
            printHelp();
        }
        else if (commandWord.equalsIgnoreCase("go")){
            goRoom(command);
        }
        else if (commandWord.equalsIgnoreCase("quit")){
            wantToQuit = quit(command);
        }
        else if (commandWord.equalsIgnoreCase("look")){
        	printRoomDescription();
        }else if (commandWord.equalsIgnoreCase("pickup")) {
        	if (command.hasSecondWord()) {
        		if (player.getCurrentRoom().isItemInRoom(command.getSecondWord())) {
        			player.pickup(command.getSecondWord());
        		}else {
        			System.out.println();
        			System.out.println("That item doesn't exist inside this room.");
        		}
        	}else {
        		System.out.println();
        		System.out.println("Please insert a name of the item to pickup.");
        	}
        }else if (commandWord.equalsIgnoreCase("drop")) {
        	if (command.hasSecondWord()) {
        		if (player.hasItem(command.getSecondWord())) {
        			player.drop(command.getSecondWord());
        		}else {
        			System.out.println();
        			System.out.println("That item doesn't exist inside your inventory.");
        		}
        	}else {
        		System.out.println();
        		System.out.println("Please insert a name of the item to drop.");
        	}
        }else if (commandWord.equalsIgnoreCase("inventory")) {
        	if (player.getInventory().isEmpty()) {
        		System.out.println("Your inventory is empty");
        	}
        	else {
        		player.getInventory().printItemString();
        	}
        }else if (commandWord.equalsIgnoreCase("itemInfo")) {
        	if (command.hasSecondWord()) {
        		if (player.hasItem(command.getSecondWord())) {
        			player.getItemInfo(command.getSecondWord());
        		}else {
        			System.out.println();
        			System.out.println("That item doesn't exist inside your inventory.");
        		}
        	}else {
        		System.out.println();
        		System.out.println("Please insert a name of the item to view.");
        	}
        }else if (commandWord.equalsIgnoreCase("use")) {
        	if (command.hasSecondWord()) {
        		if (player.hasItem(command.getSecondWord())) {
        			player.use(command.getSecondWord());
        		}else {
        			System.out.println();
        			System.out.println("That item doesn't exist inside your inventory.");
        		}
        	}else {
        		System.out.println();
        		System.out.println("Please insert a name of the item to use.");
        	}
        }
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
        System.out.println("around your house.");
        System.out.println();
        System.out.println("Your stats: ");
        System.out.println(" - health: "+ player.getHealth());
        System.out.println(" - stength: "+ player.getStrength());
        System.out.println(" - current weigth: "+ player.getInventory().getCurrentWeigth());
        System.out.println();
        System.out.println("command words are:");
        parser.showCommands();
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            player.goToRoom(nextRoom);
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
    
    /**
     * Print out the room description.
     */
    private void printRoomDescription() {
    	System.out.println();
    	System.out.println(player.getCurrentRoom().getLongDescription());
	}

    public Player getPlayer() {
    	return this.player;
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
    
}

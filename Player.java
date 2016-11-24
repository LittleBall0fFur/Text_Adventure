
public class Player {
	private Room currentRoom;
	private static Player instance;
	private int health;
	private int damage;
	
	private Inventory playerInventory = new Inventory();
	
	private Player() {
		// TODO Auto-generated constructor stub
		health = 100;
	}
	
	/**
	 * Singleton because there can be one player and only one player
	 * @return instance of player
	 */
	public static Player getInstance(){
		if(instance == null) instance = new Player();
		return instance;
	}
	
	public Inventory getPlayerInventory(){
		return playerInventory;
	}
	public void take(String key){
		playerInventory.swap(key, currentRoom.getRoomInventory());
	}
	public void drop(String key){
		currentRoom.getRoomInventory().swap(key, playerInventory);
	}
	public void setCurrentRoom(Room newRoom){
		currentRoom = newRoom;
	}
	public Room getCurrentRoom(){
		return currentRoom;
	}
	public void heal(int amount){
		health += amount;
	}
	public int gethealth(){
		return health;
	}
	public void isAlive(){
		if(health > 0){
			System.out.println("You are alive. health: " + health);
		}
		else if (health <= 0){
			System.out.println("You are dead");
			System.out.println("Thank you for playing!");
		}
	}
	public void setDamage(int amount){
		damage = amount;
		loseLife();
	}
	public void loseLife(){
		health -= damage;
	}
}

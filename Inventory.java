import java.util.HashMap;
import java.util.Map.Entry;

public class Inventory {
	HashMap<String,Item> items;
	private Item item;
	private String itemDiscription;
	public int maxWeight = 100;
	public int weight = 0;
	
	public Inventory() {
		// TODO Auto-generated constructor stub
		items = new HashMap<String, Item>();
		if(item != null){
			itemDiscription = new String(item.getDiscription());
		}
	}
	//adds item to HashMap
	public void putItem(String key, Item otherInventory){
		items.put(key, otherInventory);
	}
	
	public void swap(String key,Inventory otherInventory){
		item = otherInventory.items.get(key);
		if(maxWeight > (weight + item.getWeight())){
			items.put(key, otherInventory.getItems().remove(key));
			weight = weight + item.getWeight();
			System.out.println("in inventory");
		}
		else System.out.println("Inventory full");
	}
	public HashMap<String, Item> getItems(){
		return items;
	}
	public String itemDiscriptions(){
		itemDiscription = "";
		for (Entry<String, Item> currentItem : items.entrySet()){
			Item thisItem = currentItem.getValue();
			itemDiscription += " " + thisItem.getName();
		}
		return itemDiscription;
	}
	public Item getItem(String key){
		return items.get(key);
	}
	public HashMap<String,Item> returnItems(){
		return items;
	}
	public boolean hasItem(String key){
		return (getItem(key)!= null);
	}
	public int getWeight(){
		return weight;
	}
}

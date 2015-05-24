package entities;

public class entity_continent {
	private String name;
	private int area;
	
	public entity_continent(String name,int area){
		this.setName(name);
		this.setArea(area);
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package androidServer.bean;

public class IndoorList {
	private String position;
	private int x;
	private int y;
	private int averageStrength;
	private String description;
	private String building;
	private String floor;
	private int maxRssi;
	private int minRssi;
	private int number;
	private float ratio;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getAverageStrength() {
		return averageStrength;
	}

	public void setAverageStrength(int averageStrength) {
		this.averageStrength = averageStrength;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public int getMaxRssi() {
		return maxRssi;
	}

	public void setMaxRssi(int maxRssi) {
		this.maxRssi = maxRssi;
	}

	public int getMinRssi() {
		return minRssi;
	}

	public void setMinRssi(int minRssi) {
		this.minRssi = minRssi;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

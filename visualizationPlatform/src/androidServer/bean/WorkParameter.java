package androidServer.bean;

public class WorkParameter {
	private String province;
	private String city;
	private int ci;
	private int lac;
	private int isIndoor;
	private String freqChannel;
	private String cellNameEng;
	private String cellNameCh;
	private String cellAddress;
	private double longitude;
	private double latitude;
	private double longitudeMap;
	private double latitudeMap;
	private String belonging;
	private String scene;
	private String converageType;
	private String network;
	private String region;
	private int wpcid;
	private int enodeb;

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public int getLac() {
		return lac;
	}

	public int getIsIndoor() {
		return isIndoor;
	}

	public void setIsIndoor(int isIndoor) {
		this.isIndoor = isIndoor;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public String getFreqChannel() {
		return freqChannel;
	}

	public void setFreqChannel(String freqChannel) {
		this.freqChannel = freqChannel;
	}

	public String getCellNameEng() {
		return cellNameEng;
	}

	public void setCellNameEng(String cellNameEng) {
		if (cellNameEng != null && cellNameEng.length() > 30)
			this.cellNameEng = cellNameEng.substring(0, 30);
		else
			this.cellNameEng = cellNameEng;
	}

	public String getCellNameCh() {
		return cellNameCh;
	}

	public void setCellNameCh(String cellNameCh) {
		if (cellNameCh != null && cellNameCh.length() > 30)
			this.cellNameCh = cellNameCh.substring(0, 30);
		else
			this.cellNameCh = cellNameCh;
	}

	public String getCellAddress() {
		return cellAddress;
	}

	public void setCellAddress(String cellAddress) {
		this.cellAddress = cellAddress;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getBelonging() {
		return belonging;
	}

	public void setBelonging(String belonging) {
		this.belonging = belonging;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getConverageType() {
		return converageType;
	}

	public void setConverageType(String converageType) {
		this.converageType = converageType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getWpcid() {
		return wpcid;
	}

	public void setWpcid(int wpcid) {
		this.wpcid = wpcid;
	}

	public int getEnodeb() {
		return enodeb;
	}

	public void setEnodeb(int enodeb) {
		this.enodeb = enodeb;
	}

	public double getLongitudeMap() {
		return longitudeMap;
	}

	public void setLongitudeMap(double longitudeMap) {
		this.longitudeMap = longitudeMap;
	}

	public double getLatitudeMap() {
		return latitudeMap;
	}

	public void setLatitudeMap(double latitudeMap) {
		this.latitudeMap = latitudeMap;
	}

}

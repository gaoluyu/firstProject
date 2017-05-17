package androidServer.bean;

import java.sql.Timestamp;

public class BusinessReport {
	
	private int ci; 
	private String lac;
	private String freqChannel;
	private String cellNameEng;
	private String cellNameCh;
	private String cellAddress;
	private String belonging;
	private String scene;
	private String region;
	private int enodeb;
	private double longitude; 
	private double  latitude;
	private double avg_strength;
	private double avg_rsrp;
	private double avg_rsrq;
	private double avg_sinr;
	private String network;

	public int getCi() {
		return ci;
	}
	public void setCi(int ci) {
		this.ci = ci;
	}
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
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
		this.cellNameEng = cellNameEng;
	}
	public String getCellNameCh() {
		return cellNameCh;
	}
	public void setCellNameCh(String cellNameCh) {
		this.cellNameCh = cellNameCh;
	}
	public String getCellAddress() {
		return cellAddress;
	}
	public void setCellAddress(String cellAddress) {
		this.cellAddress = cellAddress;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getEnodeb() {
		return enodeb;
	}
	public void setEnodeb(int enodeb) {
		this.enodeb = enodeb;
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
	public double getAvg_strength() {
		return avg_strength;
	}
	public void setAvg_strength(double avg_strength) {
		this.avg_strength = avg_strength;
	}
	public double getAvg_rsrp() {
		return avg_rsrp;
	}
	public void setAvg_rsrp(double avg_rsrp) {
		this.avg_rsrp = avg_rsrp;
	}
	public double getAvg_rsrq() {
		return avg_rsrq;
	}
	public void setAvg_rsrq(double avg_rsrq) {
		this.avg_rsrq = avg_rsrq;
	}
	public double getAvg_sinr() {
		return avg_sinr;
	}
	public void setAvg_sinr(double avg_sinr) {
		this.avg_sinr = avg_sinr;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	
	
	
}

package androidServer.bean;

import java.sql.Timestamp;

public class IndoorRecord implements Cloneable {

	private String position;
	private Timestamp time;
	private String netType;
	private String networkType;
	private int cid;// Cell Identity，基站编号，是个16位的数据（范围是0到65535）
	private int lac;// Location Area Code，位置区域码
	private int signalStrength;
//	private String mcc;// Mobile Country Code，移动国家代码（中国的为460）
	private String mnc;// Mobile Network Code，移动网络号码（中国移动为00 02，中国联通为01，中国电信03）
	private String uuid;
	private int rsrp;
	private int rsrq;
	private int sinr;
	private int dl_bps;
	private int ul_bps;
	private String imsi;
	private String inspector;

	public IndoorRecord() {
	}

	// public IndoorRecord(int signalStrength, int cid, String position,
	// Timestamp time,
	// String netType, String networkType, int lac, String mcc, String mnc,
	// Timestamp cellRecordTime,
	// String uuid) {
	// this.signalStrength = signalStrength;
	// this.cid = cid;
	// this.position = position;
	// this.time = time;
	// this.netType = netType;
	// this.networkType = networkType;
	// this.lac = lac;
	// this.mcc = mcc;
	// this.mnc = mnc;
	// this.uuid = uuid;
	// }

	@Override
	public Object clone() {

		IndoorRecord ir = null;
		try {
			ir = (IndoorRecord) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ir == null)
			return null;
		if (this.time != null)
			ir.time = (Timestamp) this.time.clone();
		return ir;
	}

	public int getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

//	public String getMcc() {
//		return mcc;
//	}
//
//	public void setMcc(String mcc) {
//		this.mcc = mcc;
//	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public int getRsrp() {
		return rsrp;
	}

	public void setRsrp(int rsrp) {
		this.rsrp = rsrp;
	}

	public int getRsrq() {
		return rsrq;
	}

	public void setRsrq(int rsrq) {
		this.rsrq = rsrq;
	}

	public int getSinr() {
		return sinr;
	}

	public void setSinr(int sinr) {
		this.sinr = sinr;
	}

	public int getDl_bps() {
		return dl_bps;
	}

	public void setDl_bps(int dl_bps) {
		this.dl_bps = dl_bps;
	}

	public int getUl_bps() {
		return ul_bps;
	}

	public void setUl_bps(int ul_bps) {
		this.ul_bps = ul_bps;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

}

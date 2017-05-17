package androidServer.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.IndoorBeaconRecord;
import androidServer.bean.IndoorRecord;
import androidServer.bean.Inspection;
import androidServer.bean.Inspector;
import androidServer.bean.Neighbor;
import androidServer.bean.Sim;

public interface AndroidMapper {

	List<Map<String, Object>> getAvailableBeaconList(@Param("province") String province, @Param("city") String city);

	long getTotalBeaconNumber(@Param("floor") int floor, @Param("building") int building);

	int insertIndoor(@Param("list") List<IndoorRecord> list, @Param("inspector") String inspector);

	int insertNeighbor(List<Neighbor> list);

	// int insertOrUpdateSim(@Param("imsi") String imsi, @Param("imei") String
	// imei, @Param("phoneName") String phoneName,
	// @Param("operatingSystem") String operatingSystem, @Param("phoneType")
	// String phoneType);

	int insertOrUpdateSim(@Param("sim") Sim sim);

	int insertIndoorBeaconRecord(List<IndoorBeaconRecord> indoorBeaconRecords);

	boolean updateIndoorRecord(List<Map<String, Object>> speeds);

	int insertInspector(@Param("inspector") Inspector inspector);

	Inspector selectInspector(@Param("username") String username);

	void insertInspection(@Param("list") List<Inspection> inspectionList);

	void insertTrainDatas(@Param("list") List<Map<String, Object>> list, @Param("username") String username);
	
	void insertTrainMap(@Param("marker") String marker,@Param("X") double X,@Param("Y") double Y);
}

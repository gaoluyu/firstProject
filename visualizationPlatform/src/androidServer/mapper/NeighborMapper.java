package androidServer.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.IndoorBeaconRecord;
import androidServer.bean.IndoorRecord;
import androidServer.bean.Inspection;
import androidServer.bean.Inspector;
import androidServer.bean.Neighbor;
import androidServer.bean.Sim;

public interface NeighborMapper {

	List<Map<String, Object>> selectCidAndNeighbor(@Param("province") String province, @Param("city") String city,
			@Param("timeStart") Timestamp timeStartTS, @Param("timeEnd") Timestamp timeEndTS,
			@Param("networkList") String network, @Param("buildingId") int buildingId, @Param("floor") int floor,
			@Param("mncList") String mnc);

	List<Map<String, Object>> selectMaxNeighborDifferenceDaily(@Param("timeStart") Timestamp startTime,
			@Param("timeEnd") Timestamp endTime, @Param("position") String position, @Param("mncList") String mnc,
			@Param("networkList") String networkType, @Param("buildingId") int buildingId);

	List<Map<String, Object>> selectNeighborHist(@Param("timeStart") Timestamp startTime,
			@Param("timeEnd") Timestamp endTime, @Param("position") String position, @Param("mncList") String mnc,
			@Param("networkList") String networkType, @Param("buildingId") int buildingId);
}

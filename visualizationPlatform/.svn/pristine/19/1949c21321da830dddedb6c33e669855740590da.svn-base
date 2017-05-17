package androidServer.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LTEStatisticMapper {
	List<Map<String, Object>> selectRsrpRsrq(@Param("startTime") Timestamp start, @Param("endTime") Timestamp end,
			@Param("mncList") String mncList, @Param("buildingId") int buildingId, @Param("floor") int floor);

	/**
	 * 
	 * @param start
	 * @param end
	 * @param mncList
	 * @param buildingId
	 * @param floor
	 * @param isDown
	 *            1 get downlink speed cdf data, 0 get uplink speed cdf data
	 * @return
	 */
	List<Map<String, Integer>> selectCDFbps(@Param("startTime") Timestamp start, @Param("endTime") Timestamp end,
			@Param("mncList") String mncList, @Param("buildingId") int buildingId, @Param("floor") int floor,
			@Param("isDown") int isDown);

}

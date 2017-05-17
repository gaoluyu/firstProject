package androidServer.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.KeepAliveLocation;

public interface InspectAdminMapper {

	List<Map<String, Object>> selectImsi(@Param("building") int building);

	List<Map<String, Object>> selectImsiStatic(@Param("timeStartTS") Timestamp timeStartTS,
			@Param("timeEndTS") Timestamp timeEndTS, @Param("imsi") long imsi, @Param("building") int building);

	List<Map<String, Object>> selectPositionByDate(@Param("timeStartTS") Timestamp timeStartTS,
			@Param("timeEndTS") Timestamp timeEndTS, @Param("imsi") long imsi, @Param("building") int building);

	List<Map<String, Object>> selectPositionBoxPlotByDate(@Param("timeStartTS") Timestamp timeStartTS,
			@Param("timeEndTS") Timestamp timeEndTS, @Param("imsi") long imsi, @Param("building") int building);
	
	List<Map<String, Object>> selectUserNameExist(@Param("userName") String userName);

	void insertKeepAliveLocation(@Param("kal") KeepAliveLocation kal);

}
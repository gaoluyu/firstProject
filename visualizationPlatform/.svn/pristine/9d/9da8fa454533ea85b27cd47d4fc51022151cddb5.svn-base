package androidServer.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.HeatData;
import androidServer.bean.IndoorRecord;
import androidServer.bean.WorkParameter;

import com.sun.org.glassfish.gmbal.ParameterNames;

public interface OutdoorMapper {
	List<Map<String, Object>> selectOutdoorMapPoints(@Param("province") String province, @Param("city") String city,
			@Param("wpNetwork") String wpNetwork, @Param("operatorList") String operatorList,
			@Param("typeList") String typeList, @Param("timeStart") Timestamp timeStart,
			@Param("timeEnd") Timestamp timeEnd, @Param("ciMayDefault") int ciMayDefault,
			@Param("dataType") String dataType);
	
	Map<String,Object> getAvgIndex(@Param("cid") int cid,@Param("timeStart") Timestamp timeStart,
			@Param("timeEnd") Timestamp timeEnd,@Param("operatorList") String operatorList,
			@Param("typeList") String typeList);

	Map<String,Object> getAvgSpeed(@Param("cid") int cid,@Param("timeStart") Timestamp timeStart,
			@Param("timeEnd") Timestamp timeEnd,@Param("operatorList") String operatorList,
			@Param("typeList") String typeList);

	List<HeatData> selectHeatData(@Param("province") String province, @Param("city") String city);

	List<HeatData> selectHeatResult(@Param("province") String province, @Param("city") String city);

	void updateHeatResult(@Param("list") List<HeatData> list, @Param("province") String province,
			@Param("city") String city, @Param("updateTime") Timestamp updateTime);

	Map<String, Object> selectCenterCoordinate(@Param("province") String province, @Param("city") String city);

	List<Map<String, Object>> selectCiOrCNameLike(@Param("province") String province, @Param("city") String city,
			@Param("CiOrCName") String ciorCiName, @Param("network") String network);
	
	int getWarningNum(@Param("cid") int cid, @Param("startTime") Timestamp startTime,
			@Param("endTime") Timestamp endTime, @Param("operatorList") String operatorList, 
			@Param("typeList") String typeList, @Param("warningValue") int warningValue);
}

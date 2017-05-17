package androidServer.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.BusinessReport;

import androidServer.bean.InspectNumber;
import androidServer.bean.InspectReport;
import androidServer.bean.InspectTimes;
import androidServer.bean.InspectTimes;

import androidServer.bean.WarningReport;
import androidServer.bean.WorkParameter;

public interface ReportMapper {

	List<WarningReport> getWarningReport(@Param("username") String username, @Param("startTime") Timestamp startTime,
			@Param("endTime") Timestamp endTime, @Param("itemStart") int itemStart, @Param("offset") int offset,
			@Param("isAll") int isAll, @Param("regionMayDefault") String regionMayDefault);

	int getTotalCount(@Param("username") String username, @Param("startTime") Timestamp timeStartTS,
			@Param("endTime") Timestamp timeEndTS, @Param("regionMayDefault") String regionMayDefault);

	List<InspectNumber> selectInspectNumber(@Param("province") String province, @Param("city") String city,
			@Param("timeStart") Timestamp timeStartTS, @Param("timeEnd") Timestamp timeEndTS);

	List<InspectTimes> selectInspectTimes(@Param("province") String province, @Param("city") String city,
			@Param("timeStart") Timestamp timeStartTS, @Param("timeEnd") Timestamp timeEndTS);

	List<String> selectDateString(@Param("timeStart") Timestamp timeStartTS, @Param("timeEnd") Timestamp timeEndTS);

	List<BusinessReport> getBusinessReport(@Param("province") String province, @Param("city") String city,
			@Param("startTime") Timestamp timeStartTS, @Param("endTime") Timestamp timeEndTS, @Param("itemStart") int i,
			@Param("offset") int pageSize, @Param("isAll") int isAll, @Param("mncList") String mncList,
			@Param("wpNetwork") String wpNetwork, @Param("wpRegion") String wpRegion);

	List<InspectReport> getInspectReport(@Param("buildingId") int buildingId, @Param("timeStart") Timestamp timeStartTS,
			@Param("timeEndTS") Timestamp timeEndTS, @Param("start") int start, @Param("end") int end,
			@Param("isAll") int isAll);

}

package androidServer.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.HeatData;
import androidServer.bean.IndoorRecord;
import androidServer.bean.WorkParameter;

import com.sun.org.glassfish.gmbal.ParameterNames;

public interface WarningMapper {
	void truncateScan();

	void udpateScan(@Param("timeStart") Timestamp timeStart, @Param("timeEnd") Timestamp timeEnd,
			@Param("timeType") String timeType);

	void insertWarningBoxBelowStandard(@Param("timeStart") Timestamp timeStart, @Param("timeEnd") Timestamp timeEnd);

	void insertWarningBoxWithBadTrend(@Param("timeStart") Timestamp timeStart, @Param("timeEnd") Timestamp timeEnd);

	long selectMessageNumber(@Param("username") String username, @Param("operator") String operator);

	long selectMessageTotalNumber(@Param("username") String username, @Param("operator") String operator);
}
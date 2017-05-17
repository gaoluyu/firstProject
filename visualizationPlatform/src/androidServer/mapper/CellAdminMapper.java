package androidServer.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.WorkParameter;

public interface CellAdminMapper {
	int insertWorkParameter(List<WorkParameter> list);

	int insertSingleWorkParameter(@Param("item") WorkParameter wp);

	List<WorkParameter> selectWorkParameter(@Param("province") String province, @Param("city") String city,
			@Param("itemStart") int itemStart, @Param("offset") int offset, @Param("operator") String operator,
			@Param("ciMayDefault") int ciMayDefault);

	long selectTotalNumber(@Param("province") String province, @Param("city") String city,
			@Param("operator") String operator, @Param("ciMayDefault") int ciMayDefault);

	WorkParameter selectWorkParameterWithCI(@Param("province") String province, @Param("city") String city,
			@Param("ci") Integer ci, @Param("network") String network);

	List<String> selectProvince();

	List<String> selectCity(@Param("province") String province);

	void deleteWP(@Param("province") String province, @Param("city") String city, @Param("ci") int ci,
			@Param("network") String network);

	List<String> selectWPRegionList(@Param("province") String province, @Param("city") String city,
			@Param("operatorLike") String operatorLike);

}

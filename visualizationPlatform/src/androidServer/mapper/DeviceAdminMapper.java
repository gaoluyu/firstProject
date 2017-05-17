package androidServer.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.Beacon;
import androidServer.bean.Building;
import androidServer.bean.WorkParameter;

public interface DeviceAdminMapper {

	List<Building> selectAllBuilding(@Param("province") String province, @Param("city") String city,
			@Param("itemStart") int itemStart, @Param("offset") int offset, @Param("idMayDefault") int idMayDefault);

	Building selectBuilding(@Param("id") int id);

	String selectBuildingName(@Param("id") Integer id);

	long selectBuildingTotalCount(@Param("province") String province, @Param("city") String city,
			@Param("idMayDefault") int idMayDefault);

	int insertSingleBuilding(@Param("item") Building building);

	int updateBuilding(@Param("item") Building building);

	void deleteBuilding(@Param("province") String province, @Param("city") String city, @Param("id") int id,
			@Param("unit") String unit);

	List<Map<String, Object>> selectBuildingCi(@Param("buildingId") int buildingId);

	int insertSingleBuildingCi(@Param("buildingId") int buildingId, @Param("network") String network,
			@Param("ci") int ci);

	int insertBuildingCi(@Param("list") List<Map<String, Object>> list);

	int updateSingleBuildingCi(@Param("id") int id, @Param("buildingId") int buildingId,
			@Param("network") String network, @Param("ci") int ci);

	int updateBuildingCi(@Param("list") List<Map<String, Object>> list);

	int deleteBuildingCi(@Param("id") int id);

	int deleteBuildingCiWithBid(@Param("buildingId") int buildingId);

	List<Beacon> selectBeacon(@Param("id") int id, @Param("itemStart") int itemStart, @Param("offset") int offset);

	Beacon selectSingleBeacon(@Param("mac") String beaconInSession);

	int insertSingleBeacon(@Param("item") Beacon beacon);

	void deleteBeacon(@Param("mac") String mac);

	long getBeaconCount(@Param("id") int id);

	List<Map<String, Object>> selectBuildingMap(@Param("buildingId") int buildingId, @Param("floor") int floor);

	int updateBuildingMap(@Param("list") List<Map<String, Object>> list);

	void deleteBuildingMap(@Param("floor") int floor, @Param("buildingId") int buildingId);

	int selectBuildingInsertId();

	List<Map<String, Object>> selectIdOrBNameLike(@Param("province") String province, @Param("city") String city,
			@Param("idOrCName") String idOrCName);

	List<Map<String, String>> selectBuildingRegionList(@Param("province") String province, @Param("city") String city,
			@Param("regionLike") String regionLike);

}

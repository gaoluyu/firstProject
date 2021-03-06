package androidServer.mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import androidServer.bean.User;

public interface UserMapper {

	String checkingUsername(@Param("username") String username);

	int insertingUser(@Param("item") User user);

	int initialCoveRatio(@Param("item") User user, @Param("operator") String operator, @Param("netType") String netType,
			@Param("coverageRatio") int coverageRatio);

	int initialWarningValue(@Param("username") String username, @Param("warningValue") int warningValue,
			@Param("netType") String netType,@Param("thresHold") int thresHold);

	User selectUser(@Param("username") String username);

	int modifyPassword(@Param("newpassword") String newpassword, @Param("username") String username);

	List<Map<String, Object>> getUserSetting(@Param("username") String username, @Param("operator") String operator);

	int updateUserSetting(@Param("username") String username, @Param("province") String province,
			@Param("city") String city, @Param("list") List<Map<String, Object>> list);

	List<Map<String, Object>> getMessage(@Param("username") String username, @Param("itemStart") int itemStart,
			@Param("offset") int offset, @Param("operator") String operator);

	List<Map<String, Object>> getMessageDetail(@Param("username") String username, @Param("id") int id);

	int modifyMark(@Param("username") String username, @Param("id") int id, @Param("mark") String mark,
			@Param("telephone") String telephone);

	Integer getNetworkWarningValue(@Param("username") String username, @Param("netType") String netType);

	List<Map<String, Object>> getWarningBottom(@Param("netType") String netType);

	List<Map<String, Object>> getSettingValue(@Param("username") String username);

	List<Map<String, Object>> getCoverageSetting(@Param("username") String username);

	List<Map<String, Object>> getNetworkCoverageRatio(@Param("username") String username,
			@Param("network") String network);

	int updateCoverageSetting(@Param("username") String username, @Param("coverageRatio") int coverageRatio);

	List<Map<String, Object>> getNetworkList();

	int updateNetworklist(@Param("list") List<Map<String, Object>> list);

	List<Map<String, Object>> getRedBottom();

	String selectAuthenticatedPages(@Param("role") int role);
	
	int getRole(@Param("username") String username);

}

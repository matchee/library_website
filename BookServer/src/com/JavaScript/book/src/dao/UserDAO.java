package dao;

import java.util.List;
import com.domain.User;
public interface UserDAO {
	User get(Integer id);
	Integer save(User user);
	void update(User user);
	void delete(User user);
	void delete(Integer id);
	
	List<User> findByNameAndPass(User user);
	User findByName(String name);
	User findByEmail(String email);
}

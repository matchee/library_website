package dao.persistence.impl;

import java.util.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//import com.common.hibernate3.support.YeekuHibernateDaoSupport;
import com.domain.*;
import dao.UserDAO;
public class UserDaoHibernate extends HibernateDaoSupport implements UserDAO{
	public User get(Integer id){
		return getHibernateTemplate().get(User.class, id);
	}

	public Integer save(User user) {
		// TODO Auto-generated method stub
		Integer inte = (Integer)getHibernateTemplate().save(user);
		return inte;
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(user);		
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(user);
	}
	
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(get(id));
	}

	@SuppressWarnings("unchecked")
	public List<User> findByNameAndPass(User user) {
		// TODO Auto-generated method stub
		return (List<User>)getHibernateTemplate()
				.find("from User u where u.userName=? and u.pass=?", user.getName(), user.getPassword());
	}

	public User findByName(String name) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<User> us = (List<User>)getHibernateTemplate()
				.find("from User where userName = ? " , name);
			if (us!= null && us.size() >= 1)
			{
				return us.get(0);
			}
			return null;
	}

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<User> us = (List<User>)getHibernateTemplate()
				.find("from User where email = ? " , email);
			if (us!= null && us.size() >= 1)
			{
				return us.get(0);
			}
			return null;
	}
}

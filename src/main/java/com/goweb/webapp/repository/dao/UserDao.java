/**
 * 
 */
package com.goweb.webapp.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goweb.webapp.repository.model.User;

/**
 * @author GVL00940
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {

}

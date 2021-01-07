/**
 * 
 */
package com.goweb.webapp.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goweb.webapp.repository.model.Client;

/**
 * @author Krakenpham
 *
 */
@Repository
public interface ClientDao extends JpaRepository<Client, Long>{

}

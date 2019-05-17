package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;

@Component
public class DAOCompany {
	
	public DAOCompany(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	private static final String SQL_SELECT_ALL_COMPANY = "SELECT id, name FROM company ";
	private static final String SQL_FIND_COMPANY_BY_ID ="SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_FIND_COMPANY_BY_NAME ="SELECT id, name FROM company WHERE name LIKE ? ";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
	private static final String SQL_DELETE_COMPUTERS_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ?";
	
	private final DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	

    /* Impl�mentation de la méthode listCompany() définie dans l'interface DAOCompany */
	/**
	 * 
	 * @param pagination
	 * @return
	 * @throws DAOException
	 * @throws ParseException
	 */
    public List<Company> listCompany(MySQLPage pagination) throws DAOException, ParseException {
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_COMPANY+pagination.getPagination());
        
        return CompanyRowMapper.mapCompaniesMapper(rows);
    }
    
    @SuppressWarnings("unchecked")
	public Company showCompany(int id) throws DAOException, ParseException {
    	jdbcTemplate = new JdbcTemplate(dataSource);
        Company company = (Company) jdbcTemplate.queryForObject(
        		SQL_FIND_COMPANY_BY_ID, new Object[] { id }, new CompanyRowMapper());
        
	    return company;	
	}
    
    public List<Company> listCompanyByName(MySQLPage pagination,String name) throws DAOException, ParseException {
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_COMPANY_BY_NAME+pagination.getPagination(),new Object[] { '%'+name+'%' });
        
        return CompanyRowMapper.mapCompaniesMapper(rows);
    }
    
    @Transactional
	public void deleteCompany(Long id) {
    	jdbcTemplate = new JdbcTemplate(dataSource); 
        jdbcTemplate.update( SQL_DELETE_COMPUTERS_BY_COMPANY_ID, new Object[] { id });
        jdbcTemplate.update( SQL_DELETE_COMPANY, new Object[] { id });
    }
}
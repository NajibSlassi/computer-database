package Main;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOFactory;

public class cdb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DTOComputer c1= new DTOComputer();
		Long id=(long) 0;
		c1.setId(id);
		c1.setCompanyId(id);
		c1.setDiscontinued("1991-01-01 00:00:00");
		c1.setIntroduced("1991-01-01 00:00:00");
		
		DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
		dao.createComputer(c1);

	}

}

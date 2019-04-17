package Main;

import java.text.ParseException;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOFactory;

public class cdb {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		DTOComputer dtoc1= new DTOComputer();
		dtoc1.setName("HP");
		dtoc1.setCompanyId("1");
		dtoc1.setIntroduced("2019-01-01 00:00:00");
		dtoc1.setDiscontinued("2029-01-01 00:00:00");
		
		
		Computer c = Mapper.mapComputer(dtoc1);
		
		
		DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
		//dao.createComputer(c);
		Computer sc =dao.showComputer("Apple II");
		System.out.print("ComputerID: "+ sc.getId()+ " ComputerName: "+sc.getName()+" Date Introduced: " + sc.getIntroduced()+" Date Discontinued: " + sc.getDiscontinued()+" CompanyID: "+ sc.getCompanyId());
		

	}

}

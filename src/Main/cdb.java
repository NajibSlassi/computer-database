package Main;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOCompanyImpl;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOFactory;

public class cdb {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		//Computer c = Mapper.mapComputer(dtoc1);
		//DAOCompanyImpl dao = new DAOCompanyImpl(DAOFactory.getInstance());
		//DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
		//dao.createComputer(c);
		//Computer sc =dao.showComputer(574);
		//System.out.print("ComputerID: "+ sc.getId()+ " ComputerName: "+sc.getName()+" Date Introduced: " + sc.getIntroduced()+" Date Discontinued: " + sc.getDiscontinued()+" CompanyID: "+ sc.getCompanyId());
		//dao.updateComputer("Compac","HP","2023-12-31 23:00:00","2018-12-31 23:00:00","2");
		//dao.deleteComputer(575L);
		//List<Computer> l = dao.listComputer();
		//for (Computer x:l) {
		//	System.out.println("ComputerID: "+ x.getId()+ " ComputerName: "+x.getName()+" Date Introduced: " + x.getIntroduced()+" Date Discontinued: " + x.getDiscontinued()+" CompanyID: "+ x.getCompanyId());
		//}
		//List<Company> l = dao.listCompany(); 
		//for (Company x:l) {
		//	System.out.println("CompanyID: "+ x.getId()+ " CompanyName: "+x.getName());
		//}
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez: \n"
				+ "1: Voir la liste des ordinateurs\n"
				+ "2: Voir la liste des sociétés \n"
				+ "3: Voir les détails d'un ordinateur \n"
				+ "4: Créer un ordinateur \n"
				+ "5: Mettre à jour un ordinateur\n"
				+ "6: Supprimer un ordinateur\n");
		int i = 0;
		boolean test=false;
		do {
			test=false;
			try {
				i = Integer.parseInt(sc.nextLine());
			}
			catch ( NumberFormatException badData ){
				test=true;
				System.out.println("Vous avez saisie "+i+" Veuillez choisir une option valide: \n"
						+ "1: Voir la liste des ordinateurs\n"
						+ "2: Voir la liste des sociétés \n"
						+ "3: Voir les détails d'un ordinateur \n"
						+ "4: Créer un ordinateur \n"
						+ "5: Mettre à jour un ordinateur\n"
						+ "6: Supprimer un ordinateur\n");
		    }
			}
		while(test==true);
		int[] array = {1,2,3,4,5,6};
		int testValue = (int) i;
		boolean contains = IntStream.of(array).anyMatch(x -> x == testValue);
		
		while (!contains) {
			System.out.println("Vous avez saisie "+i+" Veuillez choisir une option valide: \n"
					+ "1: Voir la liste des ordinateurs\n"
					+ "2: Voir la liste des sociétés \n"
					+ "3: Voir les détails d'un ordinateur \n"
					+ "4: Créer un ordinateur \n"
					+ "5: Mettre à jour un ordinateur\n"
					+ "6: Supprimer un ordinateur\n");
			i = Integer.parseInt(sc.nextLine());
			int pValue = (int) i;
			contains = IntStream.of(array).anyMatch(x -> x == pValue);
		}
		if (i==1) {
			DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
			List<Computer> l = dao.listComputer();
			for (Computer x:l) {
				System.out.println("ComputerID: "+ x.getId()+ " ComputerName: "+x.getName()+" Date Introduced: " + x.getIntroduced()+" Date Discontinued: " + x.getDiscontinued()+" CompanyID: "+ x.getCompanyId());
			}
		}
		else if(i==2){
			DAOCompanyImpl dao = new DAOCompanyImpl(DAOFactory.getInstance());
			List<Company> l = dao.listCompany(); 
			for (Company x:l) {
				System.out.println("CompanyID: "+ x.getId()+ " CompanyName: "+x.getName());
			}
		}
		else if(i==3) {
			System.out.println("Entrez le Id de l'ordinateur à consulter:");
			int idComputer = sc.nextInt();
			DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
			Computer computer =dao.showComputer(idComputer);
			System.out.print("ComputerID: "+ computer.getId()+ "\n ComputerName: "+computer.getName()+"\n Date Introduced: " + computer.getIntroduced()+"\n Date Discontinued: " + computer.getDiscontinued()+"\n CompanyID: "+ computer.getCompanyId());
		}
		else if(i==4) {
			System.out.println("Entrez le nom de l'ordinateur:");
			String name = sc.nextLine();
			System.out.println("Entrez la date d'introduction de l'ordinateur:");
			String introduced= sc.nextLine();
			System.out.println("Entrez la date d'abandon de l'ordinateur:");
			String discontinued= sc.nextLine();
			System.out.println("Entrez l'id de la société fabricante:");
			String companyId= sc.nextLine();
			DTOComputer dtoc1= new DTOComputer();
			dtoc1.setName(name);
			dtoc1.setCompanyId(companyId);
			dtoc1.setIntroduced(introduced);
			dtoc1.setDiscontinued(discontinued);
			Computer c = Mapper.mapComputer(dtoc1);
			DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
			dao.createComputer(c);	
		}
		else if (i==5) {
			System.out.println("Entrez le nom de l'ordinateur que vous voulez mettre à jour:");
			String name = sc.nextLine();
			System.out.println("Entrez le nouveau nom de l'ordinateur:");
			String newname = sc.nextLine();
			System.out.println("Entrez la nouvelle date d'introduction de l'ordinateur:");
			String introduced= sc.nextLine();
			System.out.println("Entrez la nouvelle date d'abandon de l'ordinateur:");
			String discontinued= sc.nextLine();
			System.out.println("Entrez le nouveau id de la société fabricante:");
			String companyId= sc.nextLine();
			DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
			dao.updateComputer(name,newname,introduced,discontinued,companyId);
			
		}
		else if (i==6) {
			System.out.println("Entrez l'id de l'ordinateur que vous voulez supprimer:");
			Long id = sc.nextLong();
			DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
			dao.deleteComputer(id);
		}
		else {
			System.out.println("Entrez l'id de l'ordinateur que vous voulez supprimer:");
			
		}

	}

}

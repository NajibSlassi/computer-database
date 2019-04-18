package Main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class cdb{

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
			int numOfElements=l.size();
			int elementsPerPage=100;
			int numOfPages= numOfElements/elementsPerPage +1;
			int quitter=0;
			
			CommandLineTable st = new CommandLineTable();
			
			int numElement = 0;
			
			List<Computer> sublist= l.subList(0, elementsPerPage);
			while (quitter==0){
				for (Computer x:sublist) {
					numElement+=1;
					//test code
			        //st.setRightAlign(true);//if true then cell text is right aligned
			        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
			        st.setHeaders("ComputerID", "ComputerName", "Date Introduced","Date Discontinued","CompanyID:");//optional - if not used then there will be no header and horizontal lines
			        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			        
			        st.addRow(Long.toString(x.getId()), x.getName(), dateFormat.format(x.getIntroduced()),dateFormat.format(x.getDiscontinued()),Long.toString(x.getCompanyId()));
			        //System.out.println("ComputerID: "+ x.getId()+ " ComputerName: "+x.getName()+" Date Introduced: " + x.getIntroduced()+" Date Discontinued: " + x.getDiscontinued()+" CompanyID: "+ x.getCompanyId());
			        if (numElement==elementsPerPage) {
			        	numElement=0;
			        	st.print();
			        	System.out.println("Vous avez "+numOfPages+" pages , "+elementsPerPage+" elements par page et "+numOfElements+" éléments en total");
			        	st = new CommandLineTable();
			        	break;
			        }
			    
				}
				System.out.println("Consulter une autre page? 0 = Oui, 1= Non");
				quitter=Integer.parseInt(sc.nextLine());
				if (quitter==0) {
					System.out.println("Aller vers la page:");
					int t=Integer.parseInt(sc.nextLine());
					if (t==numOfPages) {
						sublist= l.subList((t-1)*elementsPerPage, numOfElements);
						for (Computer x:sublist) {
							numElement+=1;
							//test code
					        //st.setRightAlign(true);//if true then cell text is right aligned
					        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
					        st.setHeaders("ComputerID", "ComputerName", "Date Introduced","Date Discontinued","CompanyID:");//optional - if not used then there will be no header and horizontal lines
					        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
					        
					        st.addRow(Long.toString(x.getId()), x.getName(), dateFormat.format(x.getIntroduced()),dateFormat.format(x.getDiscontinued()),Long.toString(x.getCompanyId()));
						}
						st.print();
			        	System.out.println("Vous avez "+numOfPages+" pages , "+elementsPerPage+" elements par page et "+numOfElements+" éléments en total");
			        	st = new CommandLineTable();
					}else {
						sublist= l.subList((t-1)*elementsPerPage, t*elementsPerPage);
					}
					
				}
				
			}
			
			
			
					
		}
		else if(i==2){
			DAOCompanyImpl dao = new DAOCompanyImpl(DAOFactory.getInstance());
			List<Company> l = dao.listCompany(); 
			CommandLineTable st = new CommandLineTable();
			for (Company x:l) {
				//test code
		        //st.setRightAlign(true);//if true then cell text is right aligned
		        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
		        st.setHeaders("id", "CompanyName");//optional - if not used then there will be no header and horizontal lines
		    
		        
		        st.addRow(Long.toString(x.getId()), x.getName());
		        
				//System.out.println("CompanyID: "+ x.getId()+ " CompanyName: "+x.getName());
			}
			st.print();
			
		}
		else if(i==3) {
			System.out.println("Entrez le Id de l'ordinateur à consulter:");
			int idComputer = sc.nextInt();
			DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
			Computer computer =dao.showComputer(idComputer);
			CommandLineTable st = new CommandLineTable();
			//test code
	        //st.setRightAlign(true);//if true then cell text is right aligned
	        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
	        st.setHeaders("ComputerID", "ComputerName", "Date Introduced","Date Discontinued","CompanyID");//optional - if not used then there will be no header and horizontal lines
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	        
	        st.addRow(Long.toString(computer.getId()), computer.getName(), dateFormat.format(computer.getIntroduced()),dateFormat.format(computer.getDiscontinued()),Long.toString(computer.getCompanyId()));
			st.print();
	        //System.out.print("ComputerID: "+ computer.getId()+ "\n ComputerName: "+computer.getName()+"\n Date Introduced: " + computer.getIntroduced()+"\n Date Discontinued: " + computer.getDiscontinued()+"\n CompanyID: "+ computer.getCompanyId());
		}
		else if(i==4) {
			System.out.println("Entrez le nom de l'ordinateur:");
			String name = sc.nextLine();
			System.out.println("Entrez la date d'introduction de l'ordinateur:");
			String introduced= sc.nextLine();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateIntro = dateFormat.parse(introduced);
			
			System.out.println("Entrez la date de sortie de l'ordinateur:");
			String discontinued= sc.nextLine();
			
			Date dateDisc = dateFormat.parse(discontinued);
			
			while (dateIntro.compareTo(dateDisc)>0) {
				System.out.println("Date d'entrée en stock: "+ introduced + " supérieur à la date de sortie que vous avez saisi, Veuillez entrez une date de sortie ultérieure :");
				discontinued= sc.nextLine();
				dateDisc = dateFormat.parse(discontinued);
			}
			
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
			System.out.println("Une erreur est survenue veuillez réessayez");
			
		}

	}

}

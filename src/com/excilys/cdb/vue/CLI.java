package com.excilys.cdb.vue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOException;

import Main.CommandLineTable;



public class CLI {
	
	
	
	private static CLI INSTANCE = null;
	private Scanner sc = new Scanner(System.in);
	private static Controller controller=Controller.getInstance();
	
	public static CLI getInstance()
    {           
        if (INSTANCE == null){   
        	INSTANCE = new CLI(); 
        }
        return INSTANCE;
    }
	
	boolean quit=false;
	
	public void run() throws DAOException, ParseException {
        while (!quit) {
        	
            showMenu();}
            
    }

    private void showMenu() throws DAOException, ParseException {
        printMenu();
        int m = sc.nextInt();
        controller.sendToService(m) ;
    }
    
 
    private void printMenu() {
        System.out.println("Choisissez une action");
        System.out.println("0 - Quitter");
        System.out.println("1 - Lister les ordinateurs");
        System.out.println("2 - Lister les sociétés");
        System.out.println("3 - Détail d'un ordinateur");
        System.out.println("4 - Créer un ordinateur");
        System.out.println("5 - Mettre à jour un ordinateur");
        System.out.println("6 - Supprimer un ordinateur");
    }
    public void quit() throws DAOException, ParseException {
        quit = true;
        System.out.println("Au revoir");
        
    }
	
    public DTOComputer updateComputer() throws ParseException {
    	System.out.println("Entrez l'id de l'ordinateur que vous voulez mettre à  jour:");
		Long idold = sc.nextLong();
		System.out.println("Entrez le nouveau nom de l'ordinateur:");
		sc = new Scanner(System.in);
		String newname = sc.nextLine();
		
		System.out.println("Entrez la nouvelle date d'introduction de l'ordinateur:");
		String introduced= sc.nextLine();
		System.out.println("Entrez la nouvelle date de sortie de l'ordinateur:");
		String discontinued= sc.nextLine();
		System.out.println("Entrez le nouveau id de la société fabricante:");
		String companyId= sc.nextLine();
		DTOComputer dtoc1= new DTOComputer();
		dtoc1.setId(Long.toString(idold));
		dtoc1.setName(newname);
		dtoc1.setCompanyId(companyId);
		dtoc1.setIntroduced(introduced);
		dtoc1.setDiscontinued(discontinued);
		return (dtoc1);	
    }

    public Long deleteComputer() {
    	System.out.println("Entrez l'id de l'ordinateur que vous voulez supprimer:");
		Long id = sc.nextLong();
		return id;
    }
    
    public int readInt() {
    	System.out.println("Entrez le Id de l'ordinateur à  consulter:");
    	int id = sc.nextInt();
    	return id;
    }

    public void showComputerDetails(Computer computer) {
		
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


    public DTOComputer createComputer() throws ParseException {
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
			System.out.println("Date d'entré en stock: "+ introduced + " supérieure à la date de sortie que vous avez saisi, Veuillez entrez une date de sortie ultérieure :");
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
		return dtoc1;
    }
    public void showComputers(List<Computer> l) {
    	
    	int numOfElements=l.size();
		int elementsPerPage=100;
		int numOfPages= numOfElements/elementsPerPage +1;
		int quitter=0;
		
		CommandLineTable st = new CommandLineTable();
		
		int numElement = 0;
		
		List<Computer> sublist= l.subList(0, elementsPerPage);
		int t =1;
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
		        	System.out.println("Page "+t+" sur "+numOfPages+" pages , "+elementsPerPage+" elements par page et "+numOfElements+" Ã©lÃ©ments en total");
		        	st = new CommandLineTable();
		        	break;
		        }
		    
			}
			System.out.println("Consulter une autre page? 0 = Oui, 1= Non");
			quitter=Integer.parseInt(sc.nextLine());
			if (quitter==0) {
				System.out.println("Aller vers la page:");
				t=Integer.parseInt(sc.nextLine());
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
		        	System.out.println("Page "+t+" sur " +numOfPages+" pages , "+elementsPerPage+" elements par page et "+numOfElements+" éléments en total");
		        	st = new CommandLineTable();
				}else {
					sublist= l.subList((t-1)*elementsPerPage, t*elementsPerPage);
				}
				
			}
			
		};
    }


    public void showCompanies(List<Company> lc) {
    	CommandLineTable st = new CommandLineTable();
		for (Company x:lc) {
			//test code
	        //st.setRightAlign(true);//if true then cell text is right aligned
	        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
	        st.setHeaders("id", "CompanyName");//optional - if not used then there will be no header and horizontal lines
	    
	        
	        st.addRow(Long.toString(x.getId()), x.getName());
	        
			//System.out.println("CompanyID: "+ x.getId()+ " CompanyName: "+x.getName());
		}
		st.print();
    }
	

}

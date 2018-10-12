package com.dummy.myerp.testbusiness.business;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;



public class ComptabiliteManagerImplIntegrationTest extends BusinessTestCase {
	
	 private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	 
	@Test
	    public void addReferenceUnit() throws Exception {
	    	EcritureComptable vEcritureComptable;
	        vEcritureComptable = new EcritureComptable();
	        vEcritureComptable.setId(-1);
	        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
	        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        vEcritureComptable.setDate(pattern.parse("2016-12-31 00:00:00"));
	        vEcritureComptable.setLibelle("Cartouches d’imprimante");
	        SimpleDateFormat df = new SimpleDateFormat("yyyy");
	        String refYear= df.format(vEcritureComptable.getDate());
	        vEcritureComptable.setReference("AC"+"-"+refYear+"/00001");
	        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
	                                                                                 "Cartouches d’imprimante", new BigDecimal(100),
	                                                                                 null));
	        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
	        																		 "Facture F110001", null,
	        																		 new BigDecimal(100)));
	        manager.addReference(vEcritureComptable);
	        String digit = "AC-2016/00042".substring(8);
	        int val= Integer.parseInt(digit)+1;
	        String dynreference = "AC-2016/"+String.format("%05d", val);
	        assertEquals(dynreference, vEcritureComptable.getReference());
	    }
	 
	 
	@Test
	    public void insertEcritureComptableUnit() throws ParseException {
		        List<EcritureComptable> l = getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
		        EcritureComptable e= l.get(l.size()-1);
		        int v = Integer.parseInt(e.getReference().substring(8))+1;
		        SimpleDateFormat formater = new SimpleDateFormat("yyyy");
				int annee = Integer.parseInt(formater.format(e.getDate()));
		        e.setReference(e.getJournal().getCode()+"-"+annee+"/"+String.format("%05d", v));
		        e.setJournal(new JournalComptable("AC", "Achat"));
	            e.setLibelle("testajout");
	            e.setDate(new Date());
	            e.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
	                                                                                     null, new BigDecimal(200),
	                                                                                     null));
	            e.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
	                                                                                     null, null,
	                                                                                     new BigDecimal(200)));
	            
	            try {
					manager.insertEcritureComptable(e);
		          
				} catch (FunctionalException e1) {
					System.out.println("Erreur pendant l'insertion de l'ecriture comptable");
					e1.printStackTrace();
				}
	            
	            Assert.assertNotNull(e.getId());
	            
	            /*List<EcritureComptable> list= getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
	            boolean found= false;
	            for(EcritureComptable e:list) {
	            	if (e.getReference().equals("VE-2018/00029")) {
	            		found=true;
	            	}
	            }
	            Assert.assertTrue(found);*/
	            
	    	
	    }
	
	@Test
    public void updateEcritureComptableUnit() {
    	
    		EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setId(new Integer(-5));
            vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
            vEcritureComptable.setLibelle("Libellee");
            int refYear= LocalDate.of(2016, 12, 27).getYear();
            vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-"+refYear+"/00005");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                     null, new BigDecimal(200),
                                                                                     null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                     null, null,
                                                                                     new BigDecimal(200)));
            
            try {
				manager.updateEcritureComptable(vEcritureComptable);
	            List<EcritureComptable> list= getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
	            boolean found= false;
	            for(EcritureComptable e:list) {
	            	if (e.getReference().equals("BQ-2016/00005")) {
	            		if(e.getLibelle().equals("Libellee")) {
	            		found=true;
	            		}
	            	}
	            }
	            Assert.assertTrue(found);
			} catch (FunctionalException e1) {
				System.out.println("Erreur pendant la mise à jour de l'ecriture comptable");
				e1.printStackTrace();
			}  	
    }
	
	@Test
    public void deleteEcritureComptableUnit() throws ParseException {
    	
    		EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setId(new Integer(-4));
            vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
            SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        vEcritureComptable.setDate(pattern.parse("2016-12-28 00:00:00"));
            vEcritureComptable.setLibelle("TMA Appli Yyy"); 
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
	        String refYear= df.format(vEcritureComptable.getDate());
            vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-"+refYear+"/00004");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                     null, new BigDecimal(200),
                                                                                     null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                     null, null,
                                                                                     new BigDecimal(200)));
            
            int sizeinit = getBusinessProxy().getComptabiliteManager().getListEcritureComptable().size();
            manager.deleteEcritureComptable(vEcritureComptable.getId());
            int sizefinal = getBusinessProxy().getComptabiliteManager().getListEcritureComptable().size();
			Assert.assertEquals(sizeinit-1, sizefinal);   	
    }
	

	
	@Test(expected=FunctionalException.class)
	public void checkEcritureComptableContext() throws FunctionalException {
		
		EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libellee");
        int refYear= LocalDate.of(2016, 12, 13).getYear();
        vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-"+refYear+"/00003");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                 null, new BigDecimal(200),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                 null, null,
                                                                                 new BigDecimal(200)));
        
        manager.checkEcritureComptableContext(vEcritureComptable);	
	}
}

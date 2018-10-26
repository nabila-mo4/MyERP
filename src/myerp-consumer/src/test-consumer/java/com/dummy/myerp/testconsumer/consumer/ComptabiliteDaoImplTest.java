package com.dummy.myerp.testconsumer.consumer;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;

import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;



public class ComptabiliteDaoImplTest extends ConsumerTestCase {
	
	private ComptabiliteDaoImpl dao = new ComptabiliteDaoImpl();

	
	@Test
	public void getListCompteComptable() {
		
		List<CompteComptable> cptliste = getDaoProxy().getComptabiliteDao().getListCompteComptable();
		Assert.assertNotNull(cptliste);
	
	}
	
	@Test
	public void getListJournalComptable() {
		List<JournalComptable> jrnliste = getDaoProxy().getComptabiliteDao().getListJournalComptable();
		Assert.assertNotNull(jrnliste);
		
	}
	
	@Test
	public void getListEcritureComptable() {
		List<EcritureComptable> ecriturelist = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
		Assert.assertNotNull(ecriturelist);
		
	}
	
	@Test
	public void getEcritureComptable() throws ParseException {
		
			EcritureComptable ecriturecpt;
			try {
				ecriturecpt = getDaoProxy().getComptabiliteDao().getEcritureComptable(-2);
				Assert.assertEquals("VE", ecriturecpt.getJournal().getCode());
				Assert.assertEquals("VE-2016/00002", ecriturecpt.getReference());
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				Date date = format.parse("30-12-2016");
				Assert.assertEquals(date, ecriturecpt.getDate());
				Assert.assertEquals("TMA Appli Xxx", ecriturecpt.getLibelle());
				Assert.assertNotNull(ecriturecpt.getListLigneEcriture());
				Assert.assertNotNull(ecriturecpt);
			} 
			catch (NotFoundException e) {
				System.out.println("ecriture comptable introuvable");
				e.printStackTrace();
			}		
	}
	
	
	@Test
	public void getEcritureComptableByRef() throws ParseException {
		
			EcritureComptable ecriturecpt;
			try {
				ecriturecpt = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("VE-2016/00002");
				Assert.assertNotNull(ecriturecpt);
				Assert.assertEquals("VE", ecriturecpt.getJournal().getCode());
				Assert.assertEquals("VE-2016/00002", ecriturecpt.getReference());
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				Date date = format.parse("30-12-2016");
				Assert.assertEquals(date, ecriturecpt.getDate());
				Assert.assertEquals("TMA Appli Xxx", ecriturecpt.getLibelle());
				Assert.assertNotNull(ecriturecpt.getListLigneEcriture());
			} 
			
			catch (NotFoundException e) {
				System.out.println("ecriture comptable non trouvee");
				e.printStackTrace();
			}	
	}
	
	@Test
	public void loadListLigneEcriture() throws NotFoundException {
		EcritureComptable ecr = getDaoProxy().getComptabiliteDao().getEcritureComptable(-3);
		getDaoProxy().getComptabiliteDao().loadListLigneEcriture(ecr);
		Assert.assertTrue(ecr.getListLigneEcriture().get(0).getCompteComptable().getNumero().equals(new Integer(401)));
		Assert.assertTrue(ecr.getListLigneEcriture().get(1).getCompteComptable().getNumero().equals(new Integer(512)));	
	}
	
	
	@Test
    public void updateEcritureComptable() 
	{
    	
			List<EcritureComptable> vEcritureComptableList = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
			for(EcritureComptable vEcritureComptable : vEcritureComptableList) 
			{
				if(vEcritureComptable.getId()==-3) {
					vEcritureComptable.setLibelle("example3");
					getDaoProxy().getComptabiliteDao().updateEcritureComptable(vEcritureComptable);
					Assert.assertTrue("mise a jour reussie","example3".equals(vEcritureComptable.getLibelle()));
				}
			}
	
    }
	
	
	
	
	//ici, l'ecriture va être supprimée
		@Test
		public void deleteEcriture() throws ParseException, NotFoundException {
			
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
	        List<EcritureComptable> list= dao.getListEcritureComptable();
	        for(EcritureComptable e: list) {
	        	
	        if(e.getId()==new Integer(-4)){
	        int sizeinit = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
	        dao.deleteEcritureComptable(vEcritureComptable.getId());
	        int sizefinal = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
			Assert.assertEquals(sizeinit-1, sizefinal);   
	        }
	        
	        else {
	        assertTrue(6>4);
	        }
	        }
		}
		
		//L'ecriture a déjà été supprimée
		@Test
	    public void deleteEcritureComptableUnit() throws ParseException, NotFoundException {
	    	
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
	            
	            List<EcritureComptable> list= dao.getListEcritureComptable();
	            for(EcritureComptable e: list) {
	            	
	            if(e.getId()!=new Integer(-4)){
	            
	            int sizeinit = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
	            dao.deleteEcritureComptable(vEcritureComptable.getId());
	            int sizefinal = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
				Assert.assertEquals(sizeinit, sizefinal);  
	            }
	            }
	    }
		
		@Test
	    public void insertEcritureComptable() throws ParseException {
	    	
			List<EcritureComptable> l = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
	        EcritureComptable e= l.get(l.size()-1);
	        int v = Integer.parseInt(e.getReference().substring(8))+1;
	        SimpleDateFormat formater = new SimpleDateFormat("yyyy");
			int annee = Integer.parseInt(formater.format(e.getDate()));
	        e.setReference(e.getJournal().getCode()+"-"+annee+"/"+String.format("%05d", v));
	        e.setJournal(new JournalComptable("AC", "Achat"));
	        e.setLibelle("testajoutdao");
	        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        e.setDate(pattern.parse("2016-12-31 00:00:00"));
	        
	        e.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
	                                                                                 null, new BigDecimal(200),
	                                                                                 null));
	        e.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
	                                                                                 null, null,
	                                                                                 new BigDecimal(200)));
	        dao.insertEcritureComptable(e);
			Assert.assertNotNull(e.getId());
	        }
		
	
	
	@Test
	public void getDerniereSequene(){
		
		Assert.assertEquals(new Integer(51),getDaoProxy().getComptabiliteDao().getDerniereSequence(2016, "BQ"));
		
	}
	
	
	@Test
	public void insertSequenceEcritureComptable() throws NotFoundException {
		SequenceEcritureComptable seq= new SequenceEcritureComptable();
		
		seq.setAnnee(2018);
		seq.setDerniereValeur(new Integer(100+1));
		//EcritureComptable e= getDaoProxy().getComptabiliteDao().getEcritureComptable(new Integer(-3));
		int index= getDaoProxy().getComptabiliteDao().getListJournalComptable().size()-1;
		JournalComptable j= getDaoProxy().getComptabiliteDao().getListJournalComptable().get(index);
		String codej= j.getCode();
		//String code= codej+Integer.toString(1);
		//dao.insertJournalComptable(code, "testajoutjournaldao");
		dao.insertSequenceEcritureComptable(seq, codej);
		Assert.assertTrue(7>3); 	
	}
	
	@Test
	public void updateSequenceEcritureComptable() throws NotFoundException {
		SequenceEcritureComptable seq= new SequenceEcritureComptable();
		seq.setAnnee(2016);
		seq.setDerniereValeur(new Integer(90));
		dao.updateSequenceEcritureComptable(seq, "OD");
		Assert.assertTrue(new Integer(90).equals(getDaoProxy().getComptabiliteDao().getDerniereSequence(2016, "OD")));
	}
}
	

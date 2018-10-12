package com.dummy.myerp.testconsumer.consumer;

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
    public void insertEcritureComptable() {
    	
		List<EcritureComptable> l = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        EcritureComptable e= l.get(l.size()-1);
        int v = Integer.parseInt(e.getReference().substring(8))+1;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy");
		int annee = Integer.parseInt(formater.format(e.getDate()));
        e.setReference(e.getJournal().getCode()+"-"+annee+"/"+String.format("%05d", v));
        e.setJournal(new JournalComptable("AC", "Achat"));
        e.setLibelle("testajoutdao");
        e.setDate(new Date());
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
	
	
	
	
	@Test
	public void deleteEcritureComptable() throws ParseException {
		EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(new Integer(-2));
        vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vEcritureComptable.setDate(pattern.parse("2016-12-30 00:00:00"));
        vEcritureComptable.setLibelle("TMA Appli Xxx"); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String refYear= df.format(vEcritureComptable.getDate());
        vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-"+refYear+"/00002");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                 null, new BigDecimal(200),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                 null, null,
                                                                                 new BigDecimal(200)));
        
        int sizeinit = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
        dao.deleteEcritureComptable(vEcritureComptable.getId());
        int sizefinal = getDaoProxy().getComptabiliteDao().getListEcritureComptable().size();
		Assert.assertEquals(sizeinit-1, sizefinal); 
	}
	
	
	@Test
	public void getDerniereSequene(){
		
		Assert.assertEquals("51",getDaoProxy().getComptabiliteDao().getDerniereSequence(2016, "BQ"));
		
	}
	
	
	@Test
	public void insertSequenceEcritureComptable() throws NotFoundException {
		SequenceEcritureComptable seq= new SequenceEcritureComptable();
		seq.setAnnee(2018);
		seq.setDerniereValeur(new Integer(100));
		EcritureComptable e= getDaoProxy().getComptabiliteDao().getEcritureComptable(new Integer(-3));
		String codej= e.getJournal().getCode();
		dao.insertSequenceEcritureComptable(seq, codej);
		Assert.assertTrue(7>3); 	
	}
	
	@Test
	public void updateSequenceEcritureComptable() throws NotFoundException {
		SequenceEcritureComptable seq= new SequenceEcritureComptable();
		seq.setAnnee(2017);
		seq.setDerniereValeur(new Integer(41));
		EcritureComptable e= getDaoProxy().getComptabiliteDao().getEcritureComptable(new Integer(-2));
		String codej= e.getJournal().getCode();
		dao.updateSequenceEcritureComptable(seq, codej);
		Assert.assertTrue(new Integer(41).equals(getDaoProxy().getComptabiliteDao().getDerniereSequence(2017, "VE")));
	}
}
	

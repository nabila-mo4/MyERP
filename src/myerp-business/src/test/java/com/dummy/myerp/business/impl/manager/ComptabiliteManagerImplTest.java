package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    
   @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AB", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        //vEcritureComptable.setReference("ABb2017ilalanaba");
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String refYear= df.format(vEcritureComptable.getDate());
        vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-"+refYear+"/00001");
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitRG2() throws FunctionalException, ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(6);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vEcritureComptable.setDate(pattern.parse("2017-12-31 00:00:00"));
        vEcritureComptable.setReference("AC-2017/00006");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(140)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    
    // test RG5: test de code journal
    @Test
    public void checkEcritureComptableUnitRG5journalcode() throws Exception {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        //vEcritureComptable.setReference("nabila");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, null, new BigDecimal(123)
                                                                                ));
        SimpleDateFormat annee = new SimpleDateFormat("yyyy");
        String annee1= annee.format(vEcritureComptable.getDate());
        
        vEcritureComptable.setReference("AC"+"-"+annee1+"/00001");
       
        //System.out.println(vEcritureComptable.getReference());
        //if(vEcritureComptable.getReference()==null) {System.out.println("reference nulle");};
        
       
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    
    // suite RG5 : annee de reference
    
	@Test
    public void checkEcritureComptableUnitRG5annee() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setJournal(new JournalComptable("AB", "Achat"));
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String mdate= "20180831";
        vEcritureComptable.setDate(format.parse(mdate));
        
        String refYear= "2018";
        vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-"+refYear+"/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
       
        manager.checkEcritureComptableUnit(vEcritureComptable);
    
	}
	}



	
	

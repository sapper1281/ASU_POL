/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ASU_POL;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(name = "NEW_USER_ASU_TR")
@NamedQueries({
    @NamedQuery(name = "NewUserAsuTrS.findAll", query = "SELECT n FROM NewUserAsuTrS n"),
    @NamedQuery(name = "NewUserAsuTrS.findByPodr", query = "SELECT n FROM NewUserAsuTrS n WHERE n.podr = :podr"),
    @NamedQuery(name = "NewUserAsuTrS.findByTabNum", query = "SELECT n FROM NewUserAsuTrS n WHERE n.newUserAsuTrSPK.tabNum = :tabNum"),
    @NamedQuery(name = "NewUserAsuTrS.findBySnShtatJob", query = "SELECT n FROM NewUserAsuTrS n WHERE n.snShtatJob = :snShtatJob"),
    @NamedQuery(name = "NewUserAsuTrS.findByFnShtatJob", query = "SELECT n FROM NewUserAsuTrS n WHERE n.fnShtatJob = :fnShtatJob"),
    @NamedQuery(name = "NewUserAsuTrS.findByKat", query = "SELECT n FROM NewUserAsuTrS n WHERE n.kat = :kat"),
    @NamedQuery(name = "NewUserAsuTrS.findByDtRozh", query = "SELECT n FROM NewUserAsuTrS n WHERE n.dtRozh = :dtRozh"),
    @NamedQuery(name = "NewUserAsuTrS.findByDtJobIn", query = "SELECT n FROM NewUserAsuTrS n WHERE n.dtJobIn = :dtJobIn"),
    @NamedQuery(name = "NewUserAsuTrS.findByFirstName", query = "SELECT n FROM NewUserAsuTrS n WHERE n.newUserAsuTrSPK.firstName = :firstName"),
    @NamedQuery(name = "NewUserAsuTrS.findByMiddleName", query = "SELECT n FROM NewUserAsuTrS n WHERE n.newUserAsuTrSPK.middleName = :middleName"),
    @NamedQuery(name = "NewUserAsuTrS.findByLastName", query = "SELECT n FROM NewUserAsuTrS n WHERE n.newUserAsuTrSPK.lastName = :lastName"),
    @NamedQuery(name = "NewUserAsuTrS.findByIdJob", query = "SELECT n FROM NewUserAsuTrS n WHERE n.newUserAsuTrSPK.idJob = :idJob"),
    @NamedQuery(name = "NewUserAsuTrS.findByIdRoadAsuTr", query = "SELECT n FROM NewUserAsuTrS n WHERE n.newUserAsuTrSPK.idRoadAsuTr = :idRoadAsuTr"),
    @NamedQuery(name = "NewUserAsuTrS.findByRPps", query = "SELECT n FROM NewUserAsuTrS n WHERE n.rPps = :rPps"),
    @NamedQuery(name = "NewUserAsuTrS.findByCodeRoadAsuTr", query = "SELECT n FROM NewUserAsuTrS n WHERE n.codeRoadAsuTr = :codeRoadAsuTr"),
    @NamedQuery(name = "NewUserAsuTrS.findByRazdPer", query = "SELECT n FROM NewUserAsuTrS n WHERE n.razdPer = :razdPer"),
    @NamedQuery(name = "NewUserAsuTrS.findByPfr", query = "SELECT n FROM NewUserAsuTrS n WHERE n.pfr = :pfr"),
    @NamedQuery(name = "NewUserAsuTrS.findByPer", query = "SELECT n FROM NewUserAsuTrS n WHERE n.per = :per"),
    @NamedQuery(name = "NewUserAsuTrS.findByPriz", query = "SELECT n FROM NewUserAsuTrS n WHERE n.priz = :priz")})
public class NewUserAsuTrS implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NewUserAsuTrSPK newUserAsuTrSPK;
    @Column(name = "PODR")
    private String podr;
    @Column(name = "SN_SHTAT_JOB")
    private String snShtatJob;
    @Column(name = "FN_SHTAT_JOB")
    private String fnShtatJob;
    @Column(name = "KAT")
    private String kat;
    @Column(name = "DT_ROZH")
    @Temporal(TemporalType.DATE)
    private Date dtRozh;
    @Column(name = "DT_JOB_IN")
    @Temporal(TemporalType.DATE)
    private Date dtJobIn;
    @Basic(optional = false)
    @Column(name = "RPps")
    private int rPps;
    @Column(name = "CODE_ROAD_ASU_TR")
    private String codeRoadAsuTr;
    @Column(name = "RAZD_PER")
    private String razdPer;
    @Column(name = "PFR")
    private String pfr;
    @Column(name = "PER")
    @Temporal(TemporalType.DATE)
    private Date per;
    @Column(name = "PRIZ")
    private Short priz;

    public NewUserAsuTrS() {
    }

    public NewUserAsuTrS(NewUserAsuTrSPK newUserAsuTrSPK) {
        this.newUserAsuTrSPK = newUserAsuTrSPK;
    }

    public NewUserAsuTrS(NewUserAsuTrSPK newUserAsuTrSPK, int rPps) {
        this.newUserAsuTrSPK = newUserAsuTrSPK;
        this.rPps = rPps;
    }

    public NewUserAsuTrS(int tabNum, String firstName, String middleName, String lastName, int idJob, int idRoadAsuTr) {
        this.newUserAsuTrSPK = new NewUserAsuTrSPK(tabNum, firstName, middleName, lastName, idJob, idRoadAsuTr);
    }

    public NewUserAsuTrSPK getNewUserAsuTrSPK() {
        return newUserAsuTrSPK;
    }

    public void setNewUserAsuTrSPK(NewUserAsuTrSPK newUserAsuTrSPK) {
        this.newUserAsuTrSPK = newUserAsuTrSPK;
    }

    public String getPodr() {
        return podr;
    }

    public void setPodr(String podr) {
        this.podr = podr;
    }

    public String getSnShtatJob() {
        return snShtatJob;
    }

    public void setSnShtatJob(String snShtatJob) {
        this.snShtatJob = snShtatJob;
    }

    public String getFnShtatJob() {
        return fnShtatJob;
    }

    public void setFnShtatJob(String fnShtatJob) {
        this.fnShtatJob = fnShtatJob;
    }

    public String getKat() {
        return kat;
    }

    public void setKat(String kat) {
        this.kat = kat;
    }

    public Date getDtRozh() {
        return dtRozh;
    }

    public void setDtRozh(Date dtRozh) {
        this.dtRozh = dtRozh;
    }

    public Date getDtJobIn() {
        return dtJobIn;
    }

    public void setDtJobIn(Date dtJobIn) {
        this.dtJobIn = dtJobIn;
    }

    public int getRPps() {
        return rPps;
    }

    public void setRPps(int rPps) {
        this.rPps = rPps;
    }

    public String getCodeRoadAsuTr() {
        return codeRoadAsuTr;
    }

    public void setCodeRoadAsuTr(String codeRoadAsuTr) {
        this.codeRoadAsuTr = codeRoadAsuTr;
    }

    public String getRazdPer() {
        return razdPer;
    }

    public void setRazdPer(String razdPer) {
        this.razdPer = razdPer;
    }

    public String getPfr() {
        return pfr;
    }

    public void setPfr(String pfr) {
        this.pfr = pfr;
    }

    public Date getPer() {
        return per;
    }

    public void setPer(Date per) {
        this.per = per;
    }

    public Short getPriz() {
        return priz;
    }

    public void setPriz(Short priz) {
        this.priz = priz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newUserAsuTrSPK != null ? newUserAsuTrSPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NewUserAsuTrS)) {
            return false;
        }
        NewUserAsuTrS other = (NewUserAsuTrS) object;
        if ((this.newUserAsuTrSPK == null && other.newUserAsuTrSPK != null) || (this.newUserAsuTrSPK != null && !this.newUserAsuTrSPK.equals(other.newUserAsuTrSPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ASU_POL.NewUserAsuTrS[newUserAsuTrSPK=" + newUserAsuTrSPK + "]";
    }

}

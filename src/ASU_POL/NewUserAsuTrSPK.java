/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ASU_POL;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author apopovkin
 */
@Embeddable
public class NewUserAsuTrSPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "TAB_NUM")
    private int tabNum;
    @Basic(optional = false)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Basic(optional = false)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "ID_JOB")
    private int idJob;
    @Basic(optional = false)
    @Column(name = "ID_ROAD_ASU_TR")
    private int idRoadAsuTr;

    public NewUserAsuTrSPK() {
    }

    public NewUserAsuTrSPK(int tabNum, String firstName, String middleName, String lastName, int idJob, int idRoadAsuTr) {
        this.tabNum = tabNum;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.idJob = idJob;
        this.idRoadAsuTr = idRoadAsuTr;
    }

    public int getTabNum() {
        return tabNum;
    }

    public void setTabNum(int tabNum) {
        this.tabNum = tabNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIdJob() {
        return idJob;
    }

    public void setIdJob(int idJob) {
        this.idJob = idJob;
    }

    public int getIdRoadAsuTr() {
        return idRoadAsuTr;
    }

    public void setIdRoadAsuTr(int idRoadAsuTr) {
        this.idRoadAsuTr = idRoadAsuTr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tabNum;
        hash += (firstName != null ? firstName.hashCode() : 0);
        hash += (middleName != null ? middleName.hashCode() : 0);
        hash += (lastName != null ? lastName.hashCode() : 0);
        hash += (int) idJob;
        hash += (int) idRoadAsuTr;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NewUserAsuTrSPK)) {
            return false;
        }
        NewUserAsuTrSPK other = (NewUserAsuTrSPK) object;
        if (this.tabNum != other.tabNum) {
            return false;
        }
        if ((this.firstName == null && other.firstName != null) || (this.firstName != null && !this.firstName.equals(other.firstName))) {
            return false;
        }
        if ((this.middleName == null && other.middleName != null) || (this.middleName != null && !this.middleName.equals(other.middleName))) {
            return false;
        }
        if ((this.lastName == null && other.lastName != null) || (this.lastName != null && !this.lastName.equals(other.lastName))) {
            return false;
        }
        if (this.idJob != other.idJob) {
            return false;
        }
        if (this.idRoadAsuTr != other.idRoadAsuTr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ASU_POL.NewUserAsuTrSPK[tabNum=" + tabNum + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", idJob=" + idJob + ", idRoadAsuTr=" + idRoadAsuTr + "]";
    }

}

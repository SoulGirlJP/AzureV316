/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Skills;

import java.awt.Point;

/**
 *
 * @author YJH
 */
public class MatrixSkill {
    private int nSkillID, nSLV, nAngle, nUnk1, nUnk2;
    private Point pt, pt2;
    private boolean bLeft;
    
    public MatrixSkill(int nSkillID, int nSLV, int nAngle, int nUnk1, Point pt, int nUnk2, boolean bLeft) {
        this.nSkillID = nSkillID;
        this.nSLV = nSLV;
        this.nAngle = nAngle;
        this.nUnk1 = nUnk1;
        this.pt = pt;
        this.nUnk2 = nUnk2;
        this.bLeft = bLeft;
        this.pt2 = null;
    }

    /**
     * @return the nSkillID
     */
    public int getnSkillID() {
        return nSkillID;
    }

    /**
     * @param nSkillID the nSkillID to set
     */
    public void setnSkillID(int nSkillID) {
        this.nSkillID = nSkillID;
    }

    /**
     * @return the nSLV
     */
    public int getnSLV() {
        return nSLV;
    }

    /**
     * @param nSLV the nSLV to set
     */
    public void setnSLV(int nSLV) {
        this.nSLV = nSLV;
    }

    /**
     * @return the nAngle
     */
    public int getnAngle() {
        return nAngle;
    }

    /**
     * @param nAngle the nAngle to set
     */
    public void setnAngle(int nAngle) {
        this.nAngle = nAngle;
    }

    /**
     * @return the nUnk1
     */
    public int getnUnk1() {
        return nUnk1;
    }

    /**
     * @param nUnk1 the nUnk1 to set
     */
    public void setnUnk1(int nUnk1) {
        this.nUnk1 = nUnk1;
    }

    /**
     * @return the nUnk2
     */
    public int getnUnk2() {
        return nUnk2;
    }

    /**
     * @param nUnk2 the nUnk2 to set
     */
    public void setnUnk2(int nUnk2) {
        this.nUnk2 = nUnk2;
    }

    /**
     * @return the pt
     */
    public Point getPt() {
        return pt;
    }

    /**
     * @param pt the pt to set
     */
    public void setPt(Point pt) {
        this.pt = pt;
    }

    /**
     * @return the pt2
     */
    public Point getPt2() {
        return pt2;
    }

    /**
     * @param pt2 the pt2 to set
     */
    public void setPt2(Point pt2) {
        this.pt2 = pt2;
    }

    /**
     * @return the bLeft
     */
    public boolean isbLeft() {
        return bLeft;
    }

    /**
     * @param bLeft the bLeft to set
     */
    public void setbLeft(boolean bLeft) {
        this.bLeft = bLeft;
    }
}

package org.publicData.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Construction {
    @Id
    @Column(name = "cnstrcNo", length = 200)
    private String cnstrcNo;
    @Column(name = "cnstrcNm", length = 200)
    private String cnstrcNm;
    @Column(name = "sumry", length = 4000)
    private String sumry;
    @Column(name = "chargerNm", length = 200)
    private String chargerNm;
    @Column(name = "chargerTelno", length = 200)
    private String chargerTelno;
    @Column(name = "prtnDept", length = 3)
    private String prtnDept;
    @Column(name = "sprvisor", length = 1000)
    private String sprvisor;
    @Column(name = "cnstrtr", length = 100)
    private String cnstrtr;
    @Column(name = "wct", length = 100)
    private String wct;
    @Column(name = "bsr", length = 3)
    private String bsr;
    @Column(name = "bgnde", length = 50)
    private String bgnde;
    @Column(name = "endde", length = 50)
    private String endde;
    @Column(name = "planRt", length = 10)
    private String planRt;
    @Column(name = "pastdays", length = 10)
    private String pastdays;
    @Column(name = "ddays", length = 10)
    private String ddays;
    @Column(name = "achivRt", length = 10)
    private String achivRt;
    @Column(name = "totdays", length = 10)
    private String totdays;
    @Column(name = "acmsltRt", length = 10)
    private String acmsltRt;
    @Column(name = "cnstrcLc", length = 3000)
    private String cnstrcLc;
    @Column(name = "markerY", length = 20)
    private String markerY;
    @Column(name = "markerX", length = 20)
    private String markerX;
    @Column(name = "markerS", length = 4000)
    private String markerS;
    @Column(name = "markerP", length = 4000)
    private String markerP;

    @Builder
    public Construction(String cnstrcNo
                        ,String cnstrcLc
                        ,String bgnde
                        ,String markerY
                        ,String markerX
                        ,String markerS
                        ,String sumry
                        ,String sprvisor
                        ,String chargerNm
                        ,String markerP
                        ,String cnstrcNm
                        ,String achivRt
                        ,String bsr
                        ,String endde
                        ,String pastdays
                        ,String planRt
                        ,String wct
                        ,String chargerTelno
                        ,String ddays
                        ,String totdays
                        ,String prtnDept
                        ,String cnstrtr
                        ,String acmsltRt) {
        this.cnstrcNo = cnstrcNo;
        this.cnstrcLc = cnstrcLc;
        this.bgnde = bgnde;
        this.markerY = markerY;
        this.markerX = markerX;
        this.markerS = markerS;
        this.sumry = sumry;
        this.sprvisor = sprvisor;
        this.chargerNm = chargerNm;
        this.markerP = markerP;
        this.cnstrcNm = cnstrcNm;
        this.achivRt = achivRt;
        this.bsr = bsr;
        this.endde = endde;
        this.pastdays = pastdays;
        this.planRt = planRt;
        this.wct = wct;
        this.chargerTelno = chargerTelno;
        this.ddays = ddays;
        this.totdays = totdays;
        this.prtnDept = prtnDept;
        this.cnstrtr = cnstrtr;
        this.acmsltRt = acmsltRt;

    }
}

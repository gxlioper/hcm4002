package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_flow_config")
public class ExamFlowConfig implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	 	@Column(name = "exam_num")
		private String exam_num;

	    @Column(name = "h0")
		private long h0;
	    
	    @Column(name = "h0creater")
	    private long h0creater;
	    
	    @Column(name = "h0date")
		private Date h0date;	    

	    @Column(name = "h1")
		private long h1;
	    
	    @Column(name = "h1creater")
	    private long h1creater;
	    
	    @Column(name = "h1date")
		private Date h1date;

	    @Column(name = "s0")
	    private long s0;
	    
	    @Column(name = "s")
	    private long s;
	    
	    @Column(name = "s0creater")
	    private long s0creater;
	    
	    @Column(name = "s0date")
		private Date s0date;
	    
	    @Column(name = "s1")
	    private long s1;
	    
	    @Column(name = "s1creater")
	    private long s1creater;
	    
	    @Column(name = "s1date")
		private Date s1date;
	    
	    @Column(name = "z")
	    private long z;
	    
	    @Column(name = "zcreater")
	    private long zcreater;
	    
	    @Column(name = "zdate")
		private Date zdate;
	    
	    @Column(name = "z0")
	    private long z0;
	    
	    @Column(name = "z0creater")
	    private long z0creater;
	    
	    @Column(name = "z0date")
		private Date z0date;
	    
	    @Column(name = "z1")
	    private long z1;
	    
	    @Column(name = "z1creater")
	    private long z1creater;
	    
	    @Column(name = "z1date")
		private Date z1date;
	    
	    @Column(name = "c0")
	    private long c0;
	    
	    @Column(name = "c0creater")
	    private long c0creater;
	    
	    @Column(name = "c0date")
	    private Date c0date;
	    
	    @Column(name = "c")
	    private long c;
	    
	    @Column(name = "ccreater")
	    private long ccreater;
	    
	    @Column(name = "cdate")
		private Date cdate;
	    
	    @Column(name="f0")
	    private long f0;
	    
	    @Column(name="f0creater")
	    private long f0creater;
	    
	    @Column(name="f0date")
	    private Date f0date;
	    
	    @Column(name = "f")
	    private long f;
	    
	    @Column(name = "fcreater")
	    private long fcreater;
	    
	    @Column(name = "fdate")
		private Date fdate;
	    
	    @Column(name = "p0")
	    private long p0;
	    
	    @Column(name = "p0creater")
	    private long p0creater;
	    
	    @Column(name = "p0date")
		private Date p0date;
	    
	    @Column(name = "p1")
	    private long p1;
	    
	    @Column(name = "p1creater")
	    private long p1creater;
	    
	    @Column(name = "p1date")
		private Date p1date;
	    
	    @Column(name = "edesc")
	    private String edesc;
	    
	    @Column(name = "e0")
	    private long e0;
	    
	    @Column(name = "e0creater")
	    private long e0creater;
	    
	    @Column(name = "e0date")
		private Date e0date;
	    
	    @Column(name = "e1")
	    private long e1;
	    
	    @Column(name = "e1creater")
	    private long e1creater;
	    
	    @Column(name = "m")
	    private long m;
	    
	    @Column(name = "mcreater")
	    private long mcreater;
	    
	    @Column(name = "mdate")
		private Date mdate;
	    
	    @Column(name = "e1date")
		private Date e1date;    
	   
	    @Column(name = "v")
		private long v;
	    
	    @Column(name = "vcreater")
	    private long vcreater;
	    
	    @Column(name = "vdate")
		private Date vdate;
	    
	    @Column(name = "vtcreater")
	    private long vtcreater;
	    
	    @Column(name = "vtdate")
	    private Date vtdate;
	    
	    @Column(name="t")
	    private long t;
	    
	    @Column(name="tcreater")
	    private long tcreater;
	    
	    @Column(name="tdate")
	    private Date tdate;
	    
	    @Column(name="sdate")
	    private Date sdate;
	    
	    @Column(name="c1")
	    private  long c1;
	    
	    @Column(name="c1creater")
	    private  long c1creater;
	    
	    @Column(name="c1date")
	    private Date  c1date;
	    
	    public Date getSdate() {
			return sdate;
		}

		public void setSdate(Date sdate) {
			this.sdate = sdate;
		}

		public long getZ() {
			return z;
		}

		public void setZ(long z) {
			this.z = z;
		}

		public long getZcreater() {
			return zcreater;
		}

		public void setZcreater(long zcreater) {
			this.zcreater = zcreater;
		}

		public Date getZdate() {
			return zdate;
		}

		public void setZdate(Date zdate) {
			this.zdate = zdate;
		}

		public long getM() {
			return m;
		}

		public void setM(long m) {
			this.m = m;
		}

		public long getMcreater() {
			return mcreater;
		}

		public void setMcreater(long mcreater) {
			this.mcreater = mcreater;
		}

		public Date getMdate() {
			return mdate;
		}

		public void setMdate(Date mdate) {
			this.mdate = mdate;
		}

		public long getZ0() {
			return z0;
		}

		public void setZ0(long z0) {
			this.z0 = z0;
		}

		public long getZ0creater() {
			return z0creater;
		}

		public void setZ0creater(long z0creater) {
			this.z0creater = z0creater;
		}

		public Date getZ0date() {
			return z0date;
		}

		public void setZ0date(Date z0date) {
			this.z0date = z0date;
		}

		public long getZ1() {
			return z1;
		}

		public void setZ1(long z1) {
			this.z1 = z1;
		}

		public long getZ1creater() {
			return z1creater;
		}

		public void setZ1creater(long z1creater) {
			this.z1creater = z1creater;
		}

		public Date getZ1date() {
			return z1date;
		}

		public void setZ1date(Date z1date) {
			this.z1date = z1date;
		}

		public long getH0creater() {
			return h0creater;
		}

		public void setH0creater(long h0creater) {
			this.h0creater = h0creater;
		}

		public long getH1creater() {
			return h1creater;
		}

		public void setH1creater(long h1creater) {
			this.h1creater = h1creater;
		}

		public long getS0creater() {
			return s0creater;
		}

		public void setS0creater(long s0creater) {
			this.s0creater = s0creater;
		}

		public long getS1creater() {
			return s1creater;
		}

		public void setS1creater(long s1creater) {
			this.s1creater = s1creater;
		}

		public long getCcreater() {
			return ccreater;
		}

		public void setCcreater(long ccreater) {
			this.ccreater = ccreater;
		}
		
		public long getF0() {
			return f0;
		}

		public void setF0(long f0) {
			this.f0 = f0;
		}

		public long getF0creater() {
			return f0creater;
		}

		public void setF0creater(long f0creater) {
			this.f0creater = f0creater;
		}

		public Date getF0date() {
			return f0date;
		}

		public void setF0date(Date f0date) {
			this.f0date = f0date;
		}

		public long getFcreater() {
			return fcreater;
		}

		public void setFcreater(long fcreater) {
			this.fcreater = fcreater;
		}

		public long getP0creater() {
			return p0creater;
		}

		public void setP0creater(long p0creater) {
			this.p0creater = p0creater;
		}

		public long getP1creater() {
			return p1creater;
		}

		public void setP1creater(long p1creater) {
			this.p1creater = p1creater;
		}

		public long getE0creater() {
			return e0creater;
		}

		public void setE0creater(long e0creater) {
			this.e0creater = e0creater;
		}

		public long getE1creater() {
			return e1creater;
		}

		public void setE1creater(long e1creater) {
			this.e1creater = e1creater;
		}

		public Date getH0date() {
			return h0date;
		}

		public void setH0date(Date h0date) {
			this.h0date = h0date;
		}

		public Date getH1date() {
			return h1date;
		}

		public void setH1date(Date h1date) {
			this.h1date = h1date;
		}

		public Date getS0date() {
			return s0date;
		}

		public void setS0date(Date s0date) {
			this.s0date = s0date;
		}

		public Date getS1date() {
			return s1date;
		}

		public void setS1date(Date s1date) {
			this.s1date = s1date;
		}

		public Date getCdate() {
			return cdate;
		}

		public void setCdate(Date cdate) {
			this.cdate = cdate;
		}

		public Date getFdate() {
			return fdate;
		}

		public void setFdate(Date fdate) {
			this.fdate = fdate;
		}

		public Date getP0date() {
			return p0date;
		}

		public void setP0date(Date p0date) {
			this.p0date = p0date;
		}

		public Date getP1date() {
			return p1date;
		}

		public void setP1date(Date p1date) {
			this.p1date = p1date;
		}

		public Date getE0date() {
			return e0date;
		}

		public void setE0date(Date e0date) {
			this.e0date = e0date;
		}

		public Date getE1date() {
			return e1date;
		}

		public void setE1date(Date e1date) {
			this.e1date = e1date;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		@Column(name = "center_num")
	    private String center_num;

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public long getH0() {
			return h0;
		}

		public void setH0(long h0) {
			this.h0 = h0;
		}

		public long getH1() {
			return h1;
		}

		public void setH1(long h1) {
			this.h1 = h1;
		}

		public long getS0() {
			return s0;
		}

		public void setS0(long s0) {
			this.s0 = s0;
		}

		public long getS1() {
			return s1;
		}

		public void setS1(long s1) {
			this.s1 = s1;
		}

		public long getC() {
			return c;
		}

		public void setC(long c) {
			this.c = c;
		}

		public long getF() {
			return f;
		}

		public void setF(long f) {
			this.f = f;
		}

		public long getP0() {
			return p0;
		}

		public void setP0(long p0) {
			this.p0 = p0;
		}

		public long getP1() {
			return p1;
		}

		public void setP1(long p1) {
			this.p1 = p1;
		}

		public long getE0() {
			return e0;
		}

		public void setE0(long e0) {
			this.e0 = e0;
		}

		public long getE1() {
			return e1;
		}

		public void setE1(long e1) {
			this.e1 = e1;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public long getV() {
			return v;
		}

		public void setV(long v) {
			this.v = v;
		}

		public long getVcreater() {
			return vcreater;
		}

		public void setVcreater(long vcreater) {
			this.vcreater = vcreater;
		}

		public Date getVdate() {
			return vdate;
		}

		public void setVdate(Date vdate) {
			this.vdate = vdate;
		}

		public long getVtcreater() {
			return vtcreater;
		}

		public void setVtcreater(long vtcreater) {
			this.vtcreater = vtcreater;
		}

		public Date getVtdate() {
			return vtdate;
		}

		public void setVtdate(Date vtdate) {
			this.vtdate = vtdate;
		}

		public long getC0() {
			return c0;
		}

		public void setC0(long c0) {
			this.c0 = c0;
		}

		public long getC0creater() {
			return c0creater;
		}

		public void setC0creater(long c0creater) {
			this.c0creater = c0creater;
		}

		public Date getC0date() {
			return c0date;
		}

		public void setC0date(Date c0date) {
			this.c0date = c0date;
		}

		public long getT() {
			return t;
		}

		public void setT(long t) {
			this.t = t;
		}

		public long getTcreater() {
			return tcreater;
		}

		public void setTcreater(long tcreater) {
			this.tcreater = tcreater;
		}

		public Date getTdate() {
			return tdate;
		}

		public void setTdate(Date tdate) {
			this.tdate = tdate;
		}

		public long getS() {
			return s;
		}

		public void setS(long s) {
			this.s = s;
		}

		public String getEdesc() {
			return edesc;
		}

		public void setEdesc(String edesc) {
			this.edesc = edesc;
		}

		public long getC1() {
			return c1;
		}

		public void setC1(long c1) {
			this.c1 = c1;
		}

		public long getC1creater() {
			return c1creater;
		}

		public void setC1creater(long c1creater) {
			this.c1creater = c1creater;
		}

		public Date getC1date() {
			return c1date;
		}

		public void setC1date(Date c1date) {
			this.c1date = c1date;
		}

		@Override
		public String toString() {
			return "ExamFlowConfig [id=" + id + ", exam_num=" + exam_num + ", h0=" + h0 + ", h0creater=" + h0creater
					+ ", h0date=" + h0date + ", h1=" + h1 + ", h1creater=" + h1creater + ", h1date=" + h1date + ", s0="
					+ s0 + ", s=" + s + ", s0creater=" + s0creater + ", s0date=" + s0date + ", s1=" + s1
					+ ", s1creater=" + s1creater + ", s1date=" + s1date + ", z=" + z + ", zcreater=" + zcreater
					+ ", zdate=" + zdate + ", z0=" + z0 + ", z0creater=" + z0creater + ", z0date=" + z0date + ", z1="
					+ z1 + ", z1creater=" + z1creater + ", z1date=" + z1date + ", c0=" + c0 + ", c0creater=" + c0creater
					+ ", c0date=" + c0date + ", c=" + c + ", ccreater=" + ccreater + ", cdate=" + cdate + ", f0=" + f0
					+ ", f0creater=" + f0creater + ", f0date=" + f0date + ", f=" + f + ", fcreater=" + fcreater
					+ ", fdate=" + fdate + ", p0=" + p0 + ", p0creater=" + p0creater + ", p0date=" + p0date + ", p1="
					+ p1 + ", p1creater=" + p1creater + ", p1date=" + p1date + ", edesc=" + edesc + ", e0=" + e0
					+ ", e0creater=" + e0creater + ", e0date=" + e0date + ", e1=" + e1 + ", e1creater=" + e1creater
					+ ", m=" + m + ", mcreater=" + mcreater + ", mdate=" + mdate + ", e1date=" + e1date + ", v=" + v
					+ ", vcreater=" + vcreater + ", vdate=" + vdate + ", vtcreater=" + vtcreater + ", vtdate=" + vtdate
					+ ", t=" + t + ", tcreater=" + tcreater + ", tdate=" + tdate + ", sdate=" + sdate + ", c1=" + c1
					+ ", c1creater=" + c1creater + ", c1date=" + c1date + ", center_num=" + center_num + "]";
		}	

	}
package com.fr.page.stable;

import java.awt.print.PageFormat;
import java.awt.print.Paper;

import com.fr.base.Margin;
import com.fr.base.PaperSize;
import com.fr.general.ComparatorUtils;
import com.fr.page.PaperSettingProvider;
import com.fr.stable.unit.FU;
import com.fr.stable.unit.INCH;
import com.fr.stable.unit.UnitUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;

public class PaperSetting implements XMLable, PaperSettingProvider {
	private PaperSize paperSize;
	private Margin margin;
	private int orientation = 0;
	private static final long DTOP = 987552L;
	private static final long DLEFT = 2743200L;
	private static final long DBOTTOM = 987552L;
	private static final long DRIGHT = 2743200L;

	public PaperSetting() {
		this.paperSize = new PaperSize();
		this.margin = new Margin(FU.getInstance(987552L), FU.getInstance(2743200L), FU.getInstance(987552L), FU.getInstance(2743200L));
	}

	public PaperSetting(PaperSize paramPaperSize, Margin paramMargin, int paramInt) {
		this.paperSize = paramPaperSize;
		this.margin = paramMargin;
		this.orientation = paramInt;
	}

	@Override
	public PaperSize getPaperSize() {
		return this.paperSize;
	}

	@Override
	public void setPaperSize(PaperSize paramPaperSize) {
		this.paperSize = paramPaperSize;
	}

	@Override
	public Margin getMargin() {
		return this.margin;
	}

	@Override
	public void setMargin(Margin paramMargin) {
		this.margin = paramMargin;
	}

	@Override
	public int getOrientation() {
		return this.orientation;
	}

	@Override
	public void setOrientation(int paramInt) {
		this.orientation = paramInt;
	}

	@Override
	public boolean equals(Object paramObject) {
		return ((paramObject instanceof PaperSetting)) && (ComparatorUtils.equals(this.paperSize, ((PaperSetting) paramObject).paperSize)) && (ComparatorUtils.equals(this.margin, ((PaperSetting) paramObject).margin)) && (this.orientation == ((PaperSetting) paramObject).orientation);
	}

	@Override
	public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
		paramXMLPrintWriter.startTAG("PaperSetting");
		if (getOrientation() != 0)
			paramXMLPrintWriter.attr("orientation", getOrientation());
		PaperSize localPaperSize = getPaperSize();
		if ((localPaperSize != null) && (!localPaperSize.equals(PaperSize.PAPERSIZE_A4)))
			paramXMLPrintWriter.startTAG("PaperSize").attr("width", localPaperSize.getWidth().toFU()).attr("height", localPaperSize.getHeight().toFU()).end();
		Margin localMargin = getMargin();
		if ((localMargin != null) && ((localMargin.getTop().toFU() != 987552L) || (localMargin.getLeft().toFU() != 2743200L) || (localMargin.getBottom().toFU() != 987552L) || (localMargin.getRight().toFU() != 2743200L)))
			paramXMLPrintWriter.startTAG("Margin").attr("top", localMargin.getTop().toFU()).attr("left", localMargin.getLeft().toFU()).attr("bottom", localMargin.getBottom().toFU()).attr("right", localMargin.getRight().toFU()).end();
		paramXMLPrintWriter.end();
	}

	@Override
	public void readXML(XMLableReader paramXMLableReader) {
		if (paramXMLableReader.isAttr()) {
			setOrientation(paramXMLableReader.getAttrAsInt("orientation", 0));
		} else if (paramXMLableReader.isChildNode()) {
			String str = paramXMLableReader.getTagName();
			Object localObject;
			if ("PaperSize".equals(str)) {
				localObject = new PaperSize();
				setPaperSize((PaperSize) localObject);
				if (!paramXMLableReader.getXMLVersion().isAfterREPORT_REFECT_FOR65_4_XML_VERSION()) {
					((PaperSize) localObject).setWidth(UnitUtils.unit4CompatibleInchDV(paramXMLableReader.getAttrAsString("width", "0")));
					((PaperSize) localObject).setHeight(UnitUtils.unit4CompatibleInchDV(paramXMLableReader.getAttrAsString("height", "0")));
				} else if (!paramXMLableReader.getXMLVersion().isAfterUNIT_REFECT_XML_VERSION()) {
					((PaperSize) localObject).setWidth(UnitUtils.unit4CompatibleMMDV(paramXMLableReader.getAttrAsString("width", "0")));
					((PaperSize) localObject).setHeight(UnitUtils.unit4CompatibleMMDV(paramXMLableReader.getAttrAsString("height", "0")));
				} else {
					((PaperSize) localObject).setWidth(FU.getInstance(paramXMLableReader.getAttrAsLong("width", 0L)));
					((PaperSize) localObject).setHeight(FU.getInstance(paramXMLableReader.getAttrAsLong("height", 0L)));
				}
			} else if ("Margin".equals(str)) {
				localObject = new Margin();
				setMargin((Margin) localObject);
				if (!paramXMLableReader.getXMLVersion().isAfterREPORT_REFECT_FOR65_4_XML_VERSION()) {
					((Margin) localObject).setTop(UnitUtils.unit4CompatibleInchDV(paramXMLableReader.getAttrAsString("top", "0")));
					((Margin) localObject).setLeft(UnitUtils.unit4CompatibleInchDV(paramXMLableReader.getAttrAsString("left", "0")));
					((Margin) localObject).setBottom(UnitUtils.unit4CompatibleInchDV(paramXMLableReader.getAttrAsString("bottom", "0")));
					((Margin) localObject).setRight(UnitUtils.unit4CompatibleInchDV(paramXMLableReader.getAttrAsString("right", "0")));
				} else if (!paramXMLableReader.getXMLVersion().isAfterUNIT_REFECT_XML_VERSION()) {
					((Margin) localObject).setTop(UnitUtils.unit4CompatibleMMDV(paramXMLableReader.getAttrAsString("top", "0")));
					((Margin) localObject).setLeft(UnitUtils.unit4CompatibleMMDV(paramXMLableReader.getAttrAsString("left", "0")));
					((Margin) localObject).setBottom(UnitUtils.unit4CompatibleMMDV(paramXMLableReader.getAttrAsString("bottom", "0")));
					((Margin) localObject).setRight(UnitUtils.unit4CompatibleMMDV(paramXMLableReader.getAttrAsString("right", "0")));
				} else {
					((Margin) localObject).setTop(FU.getInstance(paramXMLableReader.getAttrAsLong("top", 0L)));
					((Margin) localObject).setLeft(FU.getInstance(paramXMLableReader.getAttrAsLong("left", 0L)));
					((Margin) localObject).setBottom(FU.getInstance(paramXMLableReader.getAttrAsLong("bottom", 0L)));
					((Margin) localObject).setRight(FU.getInstance(paramXMLableReader.getAttrAsLong("right", 0L)));
				}
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		PaperSetting localPaperSetting = (PaperSetting) super.clone();
		localPaperSetting.setPaperSize((PaperSize) getPaperSize().clone());
		localPaperSetting.setMargin((Margin) getMargin().clone());
		return localPaperSetting;
	}

	@Override
	public PageFormat modifyPageFormat(PageFormat paramPageFormat, INCH paramINCH1, INCH paramINCH2) {
		if (paramPageFormat == null)
			paramPageFormat = new PageFormat();
		else
			paramPageFormat = (PageFormat) paramPageFormat.clone();
		int i = getOrientation();
		double d1 = paramINCH1.toPixD(72);
		double d2 = paramINCH2.toPixD(72);
		Paper localPaper = paramPageFormat.getPaper();
		localPaper.setSize(getPaperSize().getWidth().toPixD(72), getPaperSize().getHeight().toPixD(72));
		localPaper.setImageableArea(d1, d2, getPaperSize().getWidth().toPixD(72) - 2.0D * d1, getPaperSize().getHeight().toPixD(72) - 2.0D * d2);
		paramPageFormat.setPaper(localPaper);
		if (i == 1) {

			paramPageFormat.setOrientation(0);
		} else {

			paramPageFormat.setOrientation(0);
		}

		return paramPageFormat;
	}
}

/*
 * Location: C:\Users\Administrator\Desktop\fr-server-7.1.jar Qualified Name:
 * com.fr.page.stable.PaperSetting JD-Core Version: 0.6.0
 */
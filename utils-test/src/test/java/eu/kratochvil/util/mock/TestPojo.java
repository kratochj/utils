package eu.kratochvil.util.mock;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class TestPojo {

	private Integer idObyvatel;

	private String opCislo;

	private String opSerie;

	private Short opDruh;

	private Date datumVydani;

	private Date datumPrevzeti;

	private Date datumPlatnosti;

	private Date datumSkonceniPlatnosti;

	private Date datumSkartace;

	private String opJmeno;

	private String opPrijmeni;

	private String opRodneCislo;

	private Date opDatumNarozeni;

	private Integer kodUradVydal;

	private Integer idFoto;

	private Integer idPodpis;

	private Date bokDatumZmeny;

	private String uredniZaznamy;

	public Integer getIdObyvatel() {
		return idObyvatel;
	}

	public void setIdObyvatel(Integer idObyvatel) {
		this.idObyvatel = idObyvatel;
	}

	public String getOpCislo() {
		return opCislo;
	}

	public void setOpCislo(String opCislo) {
		this.opCislo = opCislo;
	}

	public String getOpSerie() {
		return opSerie;
	}

	public void setOpSerie(String opSerie) {
		this.opSerie = opSerie;
	}

	public Short getOpDruh() {
		return opDruh;
	}

	public void setOpDruh(Short opDruh) {
		this.opDruh = opDruh;
	}

	public Date getDatumVydani() {
		return datumVydani;
	}

	public void setDatumVydani(Date datumVydani) {
		this.datumVydani = datumVydani;
	}

	public Date getDatumPrevzeti() {
		return datumPrevzeti;
	}

	public void setDatumPrevzeti(Date datumPrevzeti) {
		this.datumPrevzeti = datumPrevzeti;
	}

	public Date getDatumPlatnosti() {
		return datumPlatnosti;
	}

	public void setDatumPlatnosti(Date datumPlatnosti) {
		this.datumPlatnosti = datumPlatnosti;
	}

	public Date getDatumSkonceniPlatnosti() {
		return datumSkonceniPlatnosti;
	}

	public void setDatumSkonceniPlatnosti(Date datumSkonceniPlatnosti) {
		this.datumSkonceniPlatnosti = datumSkonceniPlatnosti;
	}

	public Date getDatumSkartace() {
		return datumSkartace;
	}

	public void setDatumSkartace(Date datumSkartace) {
		this.datumSkartace = datumSkartace;
	}

	public String getOpJmeno() {
		return opJmeno;
	}

	public void setOpJmeno(String opJmeno) {
		this.opJmeno = opJmeno;
	}

	public String getOpPrijmeni() {
		return opPrijmeni;
	}

	public void setOpPrijmeni(String opPrijmeni) {
		this.opPrijmeni = opPrijmeni;
	}

	public String getOpRodneCislo() {
		return opRodneCislo;
	}

	public void setOpRodneCislo(String opRodneCislo) {
		this.opRodneCislo = opRodneCislo;
	}

	public Date getOpDatumNarozeni() {
		return opDatumNarozeni;
	}

	public void setOpDatumNarozeni(Date opDatumNarozeni) {
		this.opDatumNarozeni = opDatumNarozeni;
	}

	public Integer getKodUradVydal() {
		return kodUradVydal;
	}

	public void setKodUradVydal(Integer kodUradVydal) {
		this.kodUradVydal = kodUradVydal;
	}

	public Integer getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Integer idFoto) {
		this.idFoto = idFoto;
	}

	public Integer getIdPodpis() {
		return idPodpis;
	}

	public void setIdPodpis(Integer idPodpis) {
		this.idPodpis = idPodpis;
	}

	public Date getBokDatumZmeny() {
		return bokDatumZmeny;
	}

	public void setBokDatumZmeny(Date bokDatumZmeny) {
		this.bokDatumZmeny = bokDatumZmeny;
	}

	public String getUredniZaznamy() {
		return uredniZaznamy;
	}

	public void setUredniZaznamy(String uredniZaznamy) {
		this.uredniZaznamy = uredniZaznamy;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("idObyvatel", idObyvatel).append("opCislo", opCislo).append("opSerie", opSerie)
				.append("opDruh", opDruh).append("datumVydani", datumVydani).append("datumPrevzeti", datumPrevzeti)
				.append("datumPlatnosti", datumPlatnosti).append("datumSkonceniPlatnosti", datumSkonceniPlatnosti)
				.append("datumSkartace", datumSkartace).append("opJmeno", opJmeno).append("opPrijmeni", opPrijmeni)
				.append("opRodneCislo", opRodneCislo).append("opDatumNarozeni", opDatumNarozeni)
				.append("kodUradVydal", kodUradVydal).append("idFoto", idFoto).append("idPodpis", idPodpis)
				.append("bokDatumZmeny", bokDatumZmeny).append("uredniZaznamy", uredniZaznamy);
		return builder.toString();
	}
}

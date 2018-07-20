package konita.rc.com.rc.model;

/*
 * Created by ozi on 07/04/2018.
 */

public class modTransaksi {
    private int id;
    private String noHp;
    private String nominal;
    private String provider;
    private String tglTrx;

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTglTrx() {
        return tglTrx;
    }

    public void setTglTrx(String tglTrx) {
        this.tglTrx = tglTrx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

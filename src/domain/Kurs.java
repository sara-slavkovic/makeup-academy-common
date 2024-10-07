/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Korisnik
 */
public class Kurs implements GenericEntity {

    private int idKursa;
    private String nazivKursa;
    private int trajanjeUNedeljama;
    private TipSminkanja tipSminkanja;
    private Predavac predavac;

    public Kurs() {
    }

    public Kurs(int idKursa, String nazivKursa, int trajanjeUNedeljama, TipSminkanja tipSminkanja, Predavac predavac) {
        this.idKursa = idKursa;
        this.nazivKursa = nazivKursa;
        this.trajanjeUNedeljama = trajanjeUNedeljama;
        this.tipSminkanja = tipSminkanja;
        this.predavac = predavac;
    }

    public int getIdKursa() {
        return idKursa;
    }

    public void setIdKursa(int idKursa) {
        this.idKursa = idKursa;
    }

    public String getNazivKursa() {
        return nazivKursa;
    }

    public void setNazivKursa(String nazivKursa) {
        this.nazivKursa = nazivKursa;
    }

    public int getTrajanjeUNedeljama() {
        return trajanjeUNedeljama;
    }

    public void setTrajanjeUNedeljama(int trajanjeUNedeljama) {
        this.trajanjeUNedeljama = trajanjeUNedeljama;
    }

    public TipSminkanja getTipSminkanja() {
        return tipSminkanja;
    }

    public void setTipSminkanja(TipSminkanja tipSminkanja) {
        this.tipSminkanja = tipSminkanja;
    }

    public Predavac getPredavac() {
        return predavac;
    }

    public void setPredavac(Predavac predavac) {
        this.predavac = predavac;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kurs other = (Kurs) obj;
        if (this.idKursa != other.idKursa) {
            return false;
        }
        if (this.trajanjeUNedeljama != other.trajanjeUNedeljama) {
            return false;
        }
        if (!Objects.equals(this.nazivKursa, other.nazivKursa)) {
            return false;
        }
        if (!Objects.equals(this.tipSminkanja, other.tipSminkanja)) {
            return false;
        }
        return Objects.equals(this.predavac, other.predavac);
    }

    @Override
    public String toString() {
        return nazivKursa;
    }

    @Override
    public String getTableName() {
        return "kurs";
    }

    @Override
    public List<GenericEntity> getList(ResultSet resultSet) throws Exception {
        List<GenericEntity> list = new LinkedList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String naziv = resultSet.getString("nazivKursa");
            int trajanjeUNedeljama = resultSet.getInt("trajanjeUNedeljama");
            int idTipaSminkanja = resultSet.getInt("idTipaSminkanja");
            TipSminkanja ts = new TipSminkanja();
            ts.setIdTipaSminkanja(idTipaSminkanja);
            int idPredavaca = resultSet.getInt("idPredavaca");
            Predavac p = new Predavac();
            p.setIdPredavaca(idPredavaca);

            Kurs k = new Kurs(id, naziv, trajanjeUNedeljama, ts, p);
            list.add(k);
        }
        return list;
    }

    @Override
    public String getAttributeNames() {
        return "nazivKursa,trajanjeUNedeljama,idTipaSminkanja,idPredavaca";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, GenericEntity entity) throws Exception {
        Kurs k = (Kurs) entity;
        ps.setString(1, k.getNazivKursa());
        ps.setInt(2, k.getTrajanjeUNedeljama());
        ps.setInt(3, k.getTipSminkanja().getIdTipaSminkanja());
        ps.setInt(4, k.getPredavac().getIdPredavaca());
    }

    @Override
    public String getUpdateQuery() {
        //za izmenu kursa
        return "nazivKursa=?, trajanjeUNedeljama=?, idTipaSminkanja=?, idPredavaca=?";
    }

    @Override
    public String getID(GenericEntity entity) {
        Kurs kurs = (Kurs) entity;
        return String.valueOf(kurs.getIdKursa());
    }

    @Override
    public String getOrderCondition() {
        //za order by
        return "id";
    }

    @Override
    public GenericEntity getResult(ResultSet resultSet) throws Exception {
        GenericEntity entity = null;
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String naziv = resultSet.getString("nazivKursa");
            int trajanjeUNedeljama = resultSet.getInt("trajanjeUNedeljama");
            int idTipaSminkanja = resultSet.getInt("idTipaSminkanja");
            TipSminkanja ts = new TipSminkanja();
            ts.setIdTipaSminkanja(idTipaSminkanja);
            int idPredavaca = resultSet.getInt("idPredavaca");
            Predavac p = new Predavac();
            p.setIdPredavaca(idPredavaca);

            entity = new Kurs(id, naziv, trajanjeUNedeljama, ts, p);
        }
        return entity;
    }

    @Override
    public String getCondition(GenericEntity entity) {
        //za filter, pretraga po nazivu
        Kurs k = (Kurs) entity;
        return "LOWER(nazivKursa) LIKE '%" + k.getNazivKursa() + "%'";
        //LOWER(nazivKursa) LIKE '$kurs%' -- sadrzi rec kurs negde u nazivu        
    }

    ////////////////////////
    //so UcitajListuPredavacaPoKursu       
    //list = repository.vratiSaJoin((Predavac) param);
    //dbb
//    @Override//negener
//    public List<GenericEntity> vratiSaJoin(GenericEntity entity) throws Exception {
//        List<GenericEntity> list = new LinkedList<>();
//        String query = "SELECT p.id, p.ime, COUNT(*) AS brojKurseva\n"
//                + "FROM predavac p JOIN kurs k ON k.idPredavaca=p.id\n"
//                + "GROUP BY p.id";
//        Connection connection = DbConnectionFactory.getInstance().getConnection();
//        Statement st = connection.createStatement();
//        ResultSet resultSet = st.executeQuery(query);
//        while (resultSet.next()) {
//            int id = resultSet.getInt("p.id");
//            String ime = resultSet.getString("p.ime");
//            int brojKurseva = resultSet.getInt("brojKurseva");
//            Predavac p = new Predavac();
//            p.setBrojKurseva(brojKurseva);
//            p.setIdPredavaca(id);
//            p.setIme(ime);
//            list.add(p);
//        }
//        System.out.println("dbbroker: " + list.toString());
//        resultSet.close();
//        st.close();
//        return list;
//    }
    //gener
//        @Override
//    public List<GenericEntity> vratiSaJoin(GenericEntity entity) throws Exception {
//        List<GenericEntity> lista = new ArrayList<>();
//        try {
//            String query = "SELECT " + entity.getJoinColumns() + " FROM " + entity.getTableName() + entity.getTableAbbreviation() + entity.getJoin() + entity.getGroupBy() + " ORDER BY" + entity.getOrderBy();
//            System.out.println(query);
//            Statement st = DBConnection.getInstance().getConnection().createStatement();
//            ResultSet rs = st.executeQuery(query);
//            lista = entity.getListFromJoin(rs);
//            rs.close();
//            st.close();
//        } catch (Exception e) {
//            throw e;
//        }
//        return lista;
//    }
//
//    @Override
//    public List<GenericEntity> vratiSaJoinPodUslovom(GenericEntity entity) throws Exception {
//        List<GenericEntity> lista = new ArrayList<>();
//        try {
//            String query = "SELECT " + entity.getJoinColumns() + " FROM " + entity.getTableName() + entity.getTableAbbreviation() + entity.getJoin() + " WHERE " + entity.getPrimaryKeyForJoin(entity) + " OR " + entity.getCondition(entity);
//            System.out.println(query);
//            Statement st = DBConnection.getInstance().getConnection().createStatement();
//            ResultSet rs = st.executeQuery(query);
//            lista = entity.getListFromJoin(rs);
//            rs.close();
//            st.close();
//        } catch (Exception e) {
//            throw e;
//        }
//        return lista;
//    }
    //  cont 
//    public List<Predavac> ucitajListuPredavacKurs() throws Exception {
//        AbstractGenericOperation operation = new UcitajListuPredavacaPoKursu();
//        operation.execute(new Predavac());
//        List<Predavac> lista = ((UcitajListuPredavacaPoKursu)operation).getList();
//        System.out.println("controller "+lista.toString());
//        return lista;
//    }
//predkurscontroller
//uprepareviewuopenform
//            List<Predavac> lista = Controller.getInstance().ucitajListuPredavacKurs();
//            ptm = new PredavacTableModel(lista);
//            frmPredavacKurs.getTblPredavacKurs().setModel(ptm);
//            ptm.setLista(lista);    
    //reposit
//    public List<GenericEntity> vratiSaJoin(GenericEntity entity) throws Exception;
//    public List<GenericEntity> vratiSaJoinPodUslovom(GenericEntity entity) throws Exception;
}

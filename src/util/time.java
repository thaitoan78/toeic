/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author toanten
 */
public class time {

    public String tanggal() {
        Date tgl = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        return (sdf.format(tgl));
    }

    public String tanggalQuery() {
        Date tgl = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return (sdf.format(tgl));
    }

    public String tanggalBulan() {
        Date tgl = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        return (sdf.format(tgl));
    }

    public String tanggalTahun() {
        Date tgl = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return (sdf.format(tgl));
    }
}

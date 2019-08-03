package com.battery.bms.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.Collections;
import java.util.List;

public class CurrentState   {


    private ObservableField<String> packvoltage;//总电压
    private ObservableField<String> current;//电流
    private ObservableField<String>	power	;	 //	功率	;
    private ObservableField<String>	soc	;	 //	SOC	;电池剩余电量百分比
    private ObservableField<String>	designedcapacity	;	 //	额定容量	;系统满充容量
    private ObservableField<String>	remainingcapacity	;	 //	剩余容量	;
    private ObservableField<String>	balancevoldiff	;	 //	均衡压差	;
    private ObservableField<String>	cyclecount	;	 //	循环次数	;
    private ObservableField<String>	batterytemp	;	 //	电池平均温度	;
    private ObservableField<String>	mostemp	;	 //	MOS管温度	;
    private ObservableField<String>	ballancetemp	;	 //	均衡温度	;
    private ObservableField<String>	balancestatus	;	 //	均衡状态	;
    private ObservableField<String>	chargemosstatus	;	 //	充电MOS状态	;
    private ObservableField<String>	dischargemosstatus	;	 //	放电MOS状态	;

    //后来添加的参数
    private ObservableField<String> packstatus;//返回系统状态
    private ObservableField<String> batterystaus;//返回Battery状态
    private ObservableField<String> packconfig;//MCU系统配置参数
    private ObservableField<String> manufactureaccess;//制造商信息

    private ObservableList<String> cellvoltagelist = new ObservableArrayList<>();
    private ObservableList<String> templist = new ObservableArrayList<>();
    private ObservableList<Boolean> minmax = new ObservableArrayList();



    public CurrentState(){

    }

    public CurrentState(String packvoltage,
                        String current,
                        String power,
                        String soc,
                        String designedcapacity,
                        String remainingcapacity,
                        String balancevoldiff,
                        String cyclecount,
                        String batterytemp,
                        String mostemp,
                        String ballancetemp,
                        String balancestatus,
                        String chargemosstatus,
                        String dischargemosstatus
                        ){
        this.current = new ObservableField<String>(current);
        this.packvoltage = new ObservableField<String>(packvoltage);
        this.power =  new ObservableField<String>(power);
        this.soc =  new ObservableField<String>(soc);
        this.designedcapacity = new ObservableField<String>(designedcapacity);
        this.remainingcapacity = new ObservableField<String>(remainingcapacity);
        this.balancevoldiff = new ObservableField<String>(balancevoldiff);
        this.cyclecount = new ObservableField<String>(cyclecount);
        this.batterytemp = new ObservableField<String>(batterytemp);
        this.mostemp = new ObservableField<String>(mostemp);
        this.ballancetemp = new ObservableField<String>(ballancetemp);
        this.balancestatus = new ObservableField<String>(balancestatus);
        this.chargemosstatus = new ObservableField<String>(chargemosstatus);
        this.dischargemosstatus = new ObservableField<String>(dischargemosstatus);
    }

    public ObservableField<String> getPackvoltage() {
        return packvoltage;
    }

    public void setPackvoltage(ObservableField<String> packvoltage) {
        this.packvoltage = packvoltage;
    }

    public ObservableField<String> getCurrent() {
        return current;
    }

    public void setCurrent(ObservableField<String> current) {
        this.current = current;
    }

    public ObservableField<String> getPower() {
        return power;
    }

    public void setPower(ObservableField<String> power) {
        this.power = power;
    }

    public ObservableField<String> getSoc() {
        return soc;
    }

    public void setSoc(ObservableField<String> soc) {
        this.soc = soc;
    }

    public ObservableField<String> getDesignedcapacity() {
        return designedcapacity;
    }

    public void setDesignedcapacity(ObservableField<String> designedcapacity) {
        this.designedcapacity = designedcapacity;
    }

    public ObservableField<String> getRemainingcapacity() {
        return remainingcapacity;
    }

    public void setRemainingcapacity(ObservableField<String> remainingcapacity) {
        this.remainingcapacity = remainingcapacity;
    }

    public ObservableField<String> getBalancevoldiff() {
        return balancevoldiff;
    }

    public void setBalancevoldiff(ObservableField<String> balancevoldiff) {
        this.balancevoldiff = balancevoldiff;
    }

    public ObservableField<String> getCyclecount() {
        return cyclecount;
    }

    public void setCyclecount(ObservableField<String> cyclecount) {
        this.cyclecount = cyclecount;
    }

    public ObservableField<String> getBatterytemp() {
        return batterytemp;
    }

    public void setBatterytemp(ObservableField<String> batterytemp) {
        this.batterytemp = batterytemp;
    }

    public ObservableField<String> getMostemp() {
        return mostemp;
    }

    public void setMostemp(ObservableField<String> mostemp) {
        this.mostemp = mostemp;
    }

    public ObservableField<String> getBallancetemp() {
        return ballancetemp;
    }

    public void setBallancetemp(ObservableField<String> ballancetemp) {
        this.ballancetemp = ballancetemp;
    }

    public ObservableField<String> getBalancestatus() {
        return balancestatus;
    }

    public void setBalancestatus(ObservableField<String> balancestatus) {
        this.balancestatus = balancestatus;
    }

    public ObservableField<String> getChargemosstatus() {
        return chargemosstatus;
    }

    public void setChargemosstatus(ObservableField<String> chargemosstatus) {
        this.chargemosstatus = chargemosstatus;
    }

    public ObservableField<String> getDischargemosstatus() {
        return dischargemosstatus;
    }

    public void setDischargemosstatus(ObservableField<String> dischargemosstatus) {
        this.dischargemosstatus = dischargemosstatus;
    }

    public ObservableList<String> getCellvoltagelist() {
        return cellvoltagelist;
    }

    public void setCellvoltagelist(ObservableList<String> cellvoltagelist) {
        this.cellvoltagelist = cellvoltagelist;
    }

    public ObservableList<String> getTemplist() {
        return templist;
    }

    public void setTemplist(ObservableList<String> templist) {
        this.templist = templist;
    }

    public ObservableField<String> getPackstatus() {
        return packstatus;
    }

    public void setPackstatus(ObservableField<String> packstatus) {
        this.packstatus = packstatus;
    }

    public ObservableField<String> getBatterystaus() {
        return batterystaus;
    }

    public void setBatterystaus(ObservableField<String> batterystaus) {
        this.batterystaus = batterystaus;
    }

    public ObservableField<String> getPackconfig() {
        return packconfig;
    }

    public void setPackconfig(ObservableField<String> packconfig) {
        this.packconfig = packconfig;
    }

    public ObservableField<String> getManufactureaccess() {
        return manufactureaccess;
    }

    public void setManufactureaccess(ObservableField<String> manufactureaccess) {
        this.manufactureaccess = manufactureaccess;
    }

    public ObservableList<Boolean> getMinmax() {
        return minmax;
    }

    public void setMinmax(ObservableList<Boolean> minmax) {
        this.minmax = minmax;
    }
}

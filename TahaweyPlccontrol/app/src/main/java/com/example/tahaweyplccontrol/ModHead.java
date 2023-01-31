package com.example.tahaweyplccontrol;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import net.wimpi.modbus.ModbusException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Rohit on 20/03/16.
 */
public class ModHead {
    int pollingInterval = 5000;
    ExecutorService ModBus = Executors.newSingleThreadExecutor();
    ExecutorService WriteHead = Executors.newSingleThreadExecutor();

    Handler Mod_Con;

    private ModHead(){
        // fuck the default constructor
    }

    public ModHead(Handler Mod_Con) {
        this.Mod_Con = Mod_Con;
    }

    public void startPolling() {
        ModBus.execute(reading);
    }

    public void connect() {
        ModBus.execute(connect);
    }
    public void disconnect() {
        ModBus.execute(disconnect);
    }

    public void write(RegisterValue regs) {
        if (!com.example.tahaweyplccontrol.Mod.getInstance().isConnected()) {
            WriteHead.execute(connect);
        }
        WriteHead.execute(new Write(regs));
    }

    Runnable connect = new Runnable() {
        @Override
        public void run() {
            try {
                com.example.tahaweyplccontrol.Mod.getInstance().connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    Runnable disconnect = new Runnable() {
        @Override
        public void run() {
            try {
                com.example.tahaweyplccontrol.Mod.getInstance().disconnect();
                //disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    Runnable reading = new Runnable() {
        @Override
        public void run() {
            while (com.example.tahaweyplccontrol.Mod.getInstance().isConnected()) {
                try {
                    ArrayList<RegisterValue> vals = com.example.tahaweyplccontrol.Mod.getInstance().readRegister();
                    Message message = Message.obtain();
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("regs", vals);
                    message.setData(data);
                    Mod_Con.sendMessage(message);
                    try {
                        Thread.sleep(pollingInterval);
                    } catch (Exception e) {
                        // That's fine
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };


    class Write implements Runnable {
        RegisterValue writeReg;
        public Write(RegisterValue writeReg) {
            this.writeReg = writeReg;
        }

        @Override
        public void run() {
            try {
                com.example.tahaweyplccontrol.Mod.getInstance().writeRegistes(this.writeReg);
            } catch (ModbusException e) {
                e.printStackTrace();
            }
        }
    }
}

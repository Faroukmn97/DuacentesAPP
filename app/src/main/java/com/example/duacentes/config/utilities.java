package com.example.duacentes.config;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class utilities {

    public static String fixEncoding(String response) {
        try {
            byte[] u = response.getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public static String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}

package com.english.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.english.English;
import com.english.cet4.R;
import com.english.inter.IDialogClickListener;

/**
 * Created by vic_ma on 15/12/3.
 * 工具类
 */
public class Utils {
    private static AlertDialog dialog = null;
    /**
     * 解析view
     * @param res
     * @return
     */
    public static View getView(Context context,int res){
        return LayoutInflater.from(context).inflate(res,null);
    }

    /**
     * 弹出对话框
     * @param context
     * @param title
     * @param msg
     */
    public static void showDialog(Context context,String title,String msg, final IDialogClickListener listener){
        View v = getView(context, R.layout.dialog_layout);
        TextView textTitle = (TextView)v.findViewById(R.id.dialog_text_title);
        TextView textContent = (TextView)v.findViewById(R.id.dialog_text_content);
        Button buttonConfirm = (Button)v.findViewById(R.id.dialog_button_confirm);
        Button buttonCancel = (Button)v.findViewById(R.id.dialog_button_cancel);
        textTitle.setText(title);
        textContent.setText(msg);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.confirmClick();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                listener.cancelClick();
            }
        });

        dialog = new AlertDialog.Builder(context)
                            .setView(v)
                            .create();
        dialog.show();

    }

    /**
     * 隐藏对话框
     */
    public static void dissmissDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}

package tdtu.lab05.musicapp.Fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tdtu.lab05.musicapp.Adapter.AdapterDanhSachphat;
import tdtu.lab05.musicapp.DB.DBDanhSachPhat;
import tdtu.lab05.musicapp.Models.DanhSachPhat;
import tdtu.lab05.musicapp.R;

public class PlayListFragment extends Fragment {

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;
    AdapterDanhSachphat adapterDanhSachphat;
    DBDanhSachPhat DBDanhSachPhat;
    List<DanhSachPhat> listDanhSachPhat;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_themdanhsachphat);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_danhsachphat);
        DBDanhSachPhat = new DBDanhSachPhat(getContext());
        listDanhSachPhat = (ArrayList<DanhSachPhat>) DBDanhSachPhat.getAll();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapterDanhSachphat = new AdapterDanhSachphat(getContext(), listDanhSachPhat);
        recyclerView.setAdapter(adapterDanhSachphat);




        adapterDanhSachphat.onClickDeleteListener(new AdapterDanhSachphat.onClickListener() {
            @Override
            public void onClick(int possion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want to delete ?");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int resultDel = DBDanhSachPhat.delete(String.valueOf(listDanhSachPhat.get(possion).getIddanhsachPhat()));

                        if (resultDel > 0 ) {
                            Toast.makeText(getContext(), "One playlist deleted",
                                    Toast.LENGTH_SHORT).show();
                            reload();
                            adapterDanhSachphat.refresh((ArrayList) DBDanhSachPhat.getAll());
                        }
                        dialog.dismiss();
                    }

                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_themdanhsachphat, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();

                EditText edTenDanhSachPhat = (EditText) layout.findViewById(R.id.edTenDanhSachPhat);
                edTenDanhSachPhat.setText("");
                Button btn_them = (Button) layout.findViewById(R.id.btn_ThemDanhSach);
                Button btn_huy = (Button) layout.findViewById(R.id.btn_huyThemDanhSach);



                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pattern NotSpecialCharacter = Pattern.compile("[A-Za-z0-9AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđ ]+");
                        Matcher IDMatcher = NotSpecialCharacter.matcher(edTenDanhSachPhat.getText().toString().trim());

                        if (valiDate(edTenDanhSachPhat)) {
                            if( !IDMatcher.matches() ){
                                Toast.makeText(getContext(), "Do not enter Special Word", Toast.LENGTH_SHORT).show();
                            } else {
                                DanhSachPhat danhSachPhat = new DanhSachPhat();
                                danhSachPhat.setTendanhsachPhat(edTenDanhSachPhat.getText().toString());
                                long result = DBDanhSachPhat.insert(danhSachPhat);
                                if (result > 0) {
                                    adapterDanhSachphat.refresh((ArrayList) DBDanhSachPhat.getAll());
                                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    reload();
                                }
                                alertDialog.dismiss();
                            }

                        } else {
                            Toast.makeText(getContext(), "Must complete", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                alertDialog.show();

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }


    public void reload() {
        listDanhSachPhat.clear();
        listDanhSachPhat.addAll(DBDanhSachPhat.getAll());
    }

    public boolean valiDate(EditText... editTexts) {

        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

}

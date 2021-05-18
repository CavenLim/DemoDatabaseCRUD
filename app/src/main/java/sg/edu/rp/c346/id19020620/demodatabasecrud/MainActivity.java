package sg.edu.rp.c346.id19020620.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd,btnEdit,btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al ;
    ListView lv;
    ArrayAdapter<Note> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) this.findViewById(R.id.btnAdd);
        btnEdit = (Button) this.findViewById(R.id.btnEdit);
        btnRetrieve = (Button) this.findViewById(R.id.btnRetrieve);
        tvDBContent = (TextView) this.findViewById(R.id.tvDBContent);
        etContent = (EditText) this.findViewById(R.id.etContent);
        lv = (ListView) this.findViewById(R.id.lv);

        al=new ArrayList<Note>();
        aa= new ArrayAdapter<Note>(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                Note target = al.get(position);
                Intent i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("data",target);
                startActivityForResult(i,9);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);
                dbh.close();

                if (inserted_id != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllNotes());
                dbh.close();

                String txt = "" ;
                for(int i =0;i<al.size();i++){
                    Note tmp = al.get(i);
                    txt += "ID:"+tmp.getId()+","+tmp.getNoteContent() +"\n";
                }
                tvDBContent.setText(txt);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note target = al.get(0);
                Intent i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("data",target);
                startActivityForResult(i,9);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            btnRetrieve.performClick();
        }
    }

}
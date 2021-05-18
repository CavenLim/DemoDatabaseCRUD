package sg.edu.rp.c346.id19020620.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    TextView tvID;
    EditText etContent;
    Button btnUpdate , btnDelete;
    Note data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvID = (TextView) this.findViewById(R.id.tvID);
        etContent = (EditText) this.findViewById(R.id.etContent2);
        btnUpdate = (Button) this.findViewById(R.id.btnUpdate);
        btnDelete = (Button) this.findViewById(R.id.btnDelete);

        Intent i = getIntent();
        data = (Note) i.getSerializableExtra("data");
        tvID.setText("ID:"+data.getId());
        etContent.setText(data.getNoteContent());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setNoteContent(etContent.getText().toString());
                dbh.updateNote(data);
                dbh.close();

                Intent intent = new Intent(EditActivity.this,MainActivity.class);

                setResult(RESULT_OK, intent);
                finish();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteNote(data.getId());
                dbh.close();
                Intent intent = new Intent(EditActivity.this,MainActivity.class);

                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

}
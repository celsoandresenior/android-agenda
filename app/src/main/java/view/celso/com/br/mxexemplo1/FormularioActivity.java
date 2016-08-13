package view.celso.com.br.mxexemplo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import view.celso.com.br.dao.AlunoDAO;
import view.celso.com.br.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    private Button salvar;
    private FormularioHelper helper;
    private Aluno aluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno  = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null){
            helper.preencheFormulario(aluno);
        }



        salvar = (Button)findViewById(R.id.formulario_salvar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormularioActivity.this, "botao clicado", Toast.LENGTH_LONG).show();
                Intent intentMain = new Intent(FormularioActivity.this, MainActivity.class);
                finish();


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                aluno =helper.pegaAluno();
                AlunoDAO dao =  new AlunoDAO(this);

                if(aluno.getId() != null){
                    dao.atualiza(aluno);
                }else{
                    dao.insere(aluno);
                }


                dao.close();

                Toast.makeText(FormularioActivity.this, aluno.getNome() + " botao clicado", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

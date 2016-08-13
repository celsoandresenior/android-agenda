package view.celso.com.br.mxexemplo1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import view.celso.com.br.dao.AlunoDAO;
import view.celso.com.br.modelo.Aluno;

public class MainActivity extends AppCompatActivity {
    private Button novoAluno;
    private ListView listaAlunos;
    private Toast print;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {

                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                //print.makeText(MainActivity.this, aluno.getNome(), Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);

            }
        });


        novoAluno = (Button) findViewById(R.id.novoAluno);

        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
               startActivity(intent);
            }
        });
        registerForContextMenu(listaAlunos);

    }

    private void CarregarLista(){

        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter =  new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        CarregarLista();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Delete!");
        MenuItem tifofolip = menu.add("Tifofolipear");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

                Toast.makeText(MainActivity.this, "deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                AlunoDAO dao = new AlunoDAO(MainActivity.this);
                dao.deletar(aluno);
                dao.close();
                CarregarLista();
                return false;
            }
        });
    }


}

package isadora.rangovoice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExibirReceitaActivity extends AppCompatActivity {

    private static final String TAG = "ExibirReceitaActivity";
    private ListView list_view_ingredientes;
    private ListView list_view_modo_preparo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        list_view_ingredientes = (ListView) findViewById(R.id.listViewIngredientesActivity); // list é o nome do arquivo xml que define <ListView>
        list_view_modo_preparo = (ListView) findViewById(R.id.listViewModoPreparoActivity);

        //Resgatar o id passado por parâmetro do item selecionado
        Bundle b = getIntent().getExtras();
        int value = 1; // or other values

        if(b != null) {
            value = b.getInt("id");
        }

        ArrayAdapter<String> adapter_ingredientes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, MainActivity.receitas.get(value).getIngredientes());
        list_view_ingredientes.setAdapter(adapter_ingredientes);

        ArrayAdapter<String> adapter_preparo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, MainActivity.receitas.get(value).getModoPreparo());
        list_view_modo_preparo.setAdapter(adapter_preparo);

    }

}

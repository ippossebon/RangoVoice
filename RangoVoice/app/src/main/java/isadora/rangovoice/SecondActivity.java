package isadora.rangovoice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity {

    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        list_view = (ListView) findViewById(R.id.list); // list é o nome do arquivo xml que define <ListView>

        //Resgatar o id passado por parâmetro do item selecionado
        Bundle b = getIntent().getExtras();
        int value = 1; // or other values
        if(b != null) {
            value = b.getInt("id");
        }
        String[] receita1 = new String[] { "Bolo de chocolate", "Ingredientes", "- 1 xícara de açúcar", "Modo de fazer:",
                "Passo 1: Coloque o açúcar em uma bacia"
        };

        String[] receita2 = new String[] { "Feijão Tropeiro", "Ingredientes", "- 1 quilo de carne magra", "Modo de fazer:",
                "Passo 1: Frite a carne em uma figideira"
        };

        String[] receita3 = new String[] { "Bolinho de arroz", "Ingredientes", "- 2 xícaras de arroz", "Modo de fazer:",
                "Passo 1: Cozinhe o arroz com água"
        };

        String[] receita4 = new String[] { "Mousse de maracujá", "Ingredientes", "- 3 maracujás maduros", "Modo de fazer:",
                "Passo 1: Bata no liquidificador o maracujá"
        };

        String[] receita5 = new String[] { "Massa à carbonara", "Ingredientes", "- 3 maracujás maduros", "Modo de fazer:",
                "Passo 1: Bata no liquidificador o maracujá"
        };

        String[] receita6 = new String[] { "Filé ao molho madeira", "Ingredientes", "- 3 maracujás maduros", "Modo de fazer:",
                "Passo 1: Bata no liquidificador o maracujá"
        };

        String[] receita7 = new String[] { "Pizza caseira", "Ingredientes", "- 3 maracujás maduros", "Modo de fazer:",
                "Passo 1: Bata no liquidificador o maracujá"
        };

        String[] receita8 = new String[] { "Brigadeiro", "Ingredientes", "- 3 maracujás maduros", "Modo de fazer:",
                "Passo 1: Bata no liquidificador o maracujá"
        };

        if(value == 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita1);
            list_view.setAdapter(adapter);
        }else if(value == 1){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita2);
            list_view.setAdapter(adapter);
        }else if(value == 2){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita3);
            list_view.setAdapter(adapter);
        }else if(value == 3){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita4);
            list_view.setAdapter(adapter);
        }else if(value == 4){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita5);
            list_view.setAdapter(adapter);
        }else if(value == 5){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita6);
            list_view.setAdapter(adapter);
        }else if(value == 6){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita7);
            list_view.setAdapter(adapter);
        }else if(value == 7){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, receita8);
            list_view.setAdapter(adapter);
        }
    }

}

package isadora.rangovoice;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Locale;

public class ExibirReceitaActivity extends AppCompatActivity {

    private static final String TAG = "ExibirReceitaActivity";
    private ListView list_view_ingredientes;
    private ListView list_view_modo_preparo;
    private TextToSpeech text_to_speech_object;

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

        final Receita receita_atual = MainActivity.receitas.get(value);
        ArrayAdapter<String> adapter_preparo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, receita_atual.getModoPreparo());
        list_view_modo_preparo.setAdapter(adapter_preparo);

        /* Text to Speech */
        text_to_speech_object = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                text_to_speech_object.setLanguage(new Locale("pt", "br"));
                text_to_speech_object.speak("Ingredientes", TextToSpeech.QUEUE_ADD, null);

                for (String ingrediente : receita_atual.getIngredientes()){
                    text_to_speech_object.speak(ingrediente, TextToSpeech.QUEUE_ADD, null);
                }

                text_to_speech_object.speak("Modo de preparo", TextToSpeech.QUEUE_ADD, null);

                for (String passo: receita_atual.getModoPreparo()){
                    text_to_speech_object.speak(passo, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });
    }

}

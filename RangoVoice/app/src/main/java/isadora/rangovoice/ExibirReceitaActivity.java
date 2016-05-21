package isadora.rangovoice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ExibirReceitaActivity extends AppCompatActivity {

    private static final String TAG = "ExibirReceitaActivity";
    private ListView list_view_ingredientes;
    private ListView list_view_modo_preparo;
    private TextToSpeech text_to_speech_object;
    private Button btnVoice;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Receita receita;

    private int indexIngredientes = -1;
    private int indexPreparo = -1;

    private final int INGREDIENTES = 0;
    private final int MODO_DE_PREPARO = 1;

    private int ondeEstou = INGREDIENTES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        list_view_ingredientes = (ListView) findViewById(R.id.listViewIngredientesActivity); // list é o nome do arquivo xml que define <ListView>
        list_view_modo_preparo = (ListView) findViewById(R.id.listViewModoPreparoActivity);
        btnVoice = (Button) findViewById(R.id.buttonVoice);

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captarVoz();
            }
        });


        //Resgatar o id passado por parâmetro do item selecionado
        Bundle b = getIntent().getExtras();
        int value = 1; // or other values

        if(b != null) {
            value = b.getInt("id");
        }
        receita = MainActivity.receitas.get(value);

        ArrayAdapter<String> adapter_ingredientes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, MainActivity.receitas.get(value).getIngredientes());
        list_view_ingredientes.setAdapter(adapter_ingredientes);

        ArrayAdapter<String> adapter_preparo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, receita.getModoPreparo());
        list_view_modo_preparo.setAdapter(adapter_preparo);

        /* Inicializando o objeto Text to Speech */
        text_to_speech_object = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                text_to_speech_object.setLanguage(new Locale("pt", "br"));
                falar("Receita para " + receita.getNome());
            }
        });

        //falar("Eu tendo os comandos proximo, voltar e repetir");

        /* Text to Speech */
        /*text_to_speech_object = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
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
        });*/
    }

    private void falar(String oque) {
        text_to_speech_object.speak(oque, TextToSpeech.QUEUE_ADD, null);
    }


    /**
     * Mostra a janela que capta a voz
     * */
    private void captarVoz() {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga os comandos para ouvir a receita.");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Bagulho nao suportado", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String textoInterpretado = result.get(0);
                    if (textoInterpretado.equals("repetir")) {
                        comandoRepetir();
                    } else if (textoInterpretado.equals("próximo")) {
                        comandoProximo();
                    } else if (textoInterpretado.equals("voltar")) {
                        comandoVoltar();
                    } else if (textoInterpretado.equals("ingredientes")) {
                        comandoIngredientes();
                    } else if (textoInterpretado.equals("modo de preparo")) {
                        comandoPreparo();
                    } else {
                        falar("não entendi o que você disse!");
                    }
                }
                break;
            }

        }
    }


    private void comandoProximo() {
        if (ondeEstou == INGREDIENTES) {
            if (indexIngredientes < receita.getIngredientes().size()-1) {
                indexIngredientes++;
                falar("Ingrediente " + (indexIngredientes+1) + ", " + receita.getIngredientes().get(indexIngredientes));
            } else {
                falar("Este era o último ingrediente.");
                falar("Para ver o modo de preparo diga modo de preparo.");
            }
        } else {
            if (indexPreparo < receita.getModoPreparo().size()-1) {
                indexPreparo++;
                falar("Passo " + (indexPreparo+1) + ", " + receita.getModoPreparo().get(indexPreparo));
            } else {
                falar("Este era o último passo do modo de preparo.");
            }
        }
    }

    private void comandoVoltar() {
        if (ondeEstou == INGREDIENTES) {
            if (indexIngredientes > 0) {
                indexIngredientes--;
                falar("Ingrediente " + (indexIngredientes+1) + ", " + receita.getIngredientes().get(indexIngredientes));
            } else {
                falar("Não existem ingredientes antes desse.");
            }
        } else {
            if (indexPreparo > 0) {
                indexPreparo--;
                falar("Passo " + (indexPreparo+1) + ", " + receita.getModoPreparo().get(indexPreparo));
            } else {
                falar("Não existem passos de preparo antes desse.");
            }
        }
    }

    private void comandoRepetir() {
        if (ondeEstou == INGREDIENTES) {
            if (indexIngredientes > -1) {
                falar("Ingrediente " + (indexIngredientes+1) + ", " + receita.getIngredientes().get(indexIngredientes));
            } else {
                falar("O passo é -1");
            }
        } else {
            if (indexPreparo > -1) {
                falar("Passo " + (indexPreparo+1) + ", " + receita.getModoPreparo().get(indexPreparo));
            } else {
                falar("O passo é -1");
            }
        }
    }

    private void comandoIngredientes() {
        ondeEstou = INGREDIENTES;
        indexIngredientes = -1;
        falar("Ingredientes da receita");
        comandoProximo();
    }

    private void comandoPreparo() {
        ondeEstou = MODO_DE_PREPARO;
        indexPreparo = -1;
        falar("Modo de Preparo");
        comandoProximo();
    }

}

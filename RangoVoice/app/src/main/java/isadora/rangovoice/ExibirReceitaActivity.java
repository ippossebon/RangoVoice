package isadora.rangovoice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class ExibirReceitaActivity extends AppCompatActivity implements RecognitionListener {


    private static final String TAG = "ExibirReceitaActivity";
    private ListView list_view_ingredientes;
    private ListView list_view_modo_preparo;
    private TextToSpeech text_to_speech_object; //Objeto do google para converter texto em voz.
    private SpeechRecognizer recognizer; //Objeto de reconhecimento da lib sphinxpocket.
    private static final String KWS_SEARCH = "wakeup"; //Ainda não sabemos para que serve isso.
    private static final String KEYPHRASE = "ok chef michael"; //Palavra-chave para ativar microfone.


    private Button btnVoice;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    Receita receita;

    private int indexIngredientes = -1;
    private int indexPreparo = -1;

    private final int INGREDIENTES = 0;
    private final int MODO_DE_PREPARO = 1;

    private int ondeEstou = INGREDIENTES;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /* Define onde as informações serão mostradas. */
        list_view_ingredientes = (ListView) findViewById(R.id.listViewIngredientesActivity);
        list_view_modo_preparo = (ListView) findViewById(R.id.listViewModoPreparoActivity);

        btnVoice = (Button) findViewById(R.id.buttonVoice);

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captarVoz();
            }
        });

        /* Resgatar o id passado por parâmetro do item selecionado */
        Bundle b = getIntent().getExtras();
        int value = 1; // or other values

        if (b != null) {
            value = b.getInt("id");

        }


        runRecognizerSetup();
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void falar(String texto) {
        text_to_speech_object.speak(texto, TextToSpeech.QUEUE_ADD, null);
    }


    /**
     * Mostra a janela que capta a voz
     */
    private void captarVoz() {
        
        recognizer.cancel();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Deve pegar SEMPRE português.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("pt", "br"));
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga os comandos para ouvir a receita.");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, new Long(10000));
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, new Long(10000));
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, new Long(10000));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Bagulho nao suportado", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String textoInterpretado = result.get(0);
                    Log.d(TAG, "Entendeu: " + textoInterpretado);
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
                    aguardarKeyword();
                }
                break;
            }

        }
    }


    private void comandoProximo() {
        if (ondeEstou == INGREDIENTES) {
            if (indexIngredientes < receita.getIngredientes().size() - 1) {
                indexIngredientes++;
                falar("Ingrediente " + (indexIngredientes + 1) + ", " + receita.getIngredientes().get(indexIngredientes));
            } else {
                falar("Este era o último ingrediente.");
                falar("Para ver o modo de preparo diga modo de preparo.");
            }
        } else {
            if (indexPreparo < receita.getModoPreparo().size() - 1) {
                indexPreparo++;
                falar("Passo " + (indexPreparo + 1) + ", " + receita.getModoPreparo().get(indexPreparo));
            } else {
                falar("Este era o último passo do modo de preparo.");
            }
        }
    }

    private void comandoVoltar() {
        if (ondeEstou == INGREDIENTES) {
            if (indexIngredientes > 0) {
                indexIngredientes--;
                falar("Ingrediente " + (indexIngredientes + 1) + ", " + receita.getIngredientes().get(indexIngredientes));
            } else {
                falar("Não existem ingredientes antes deste.");
            }
        } else {
            if (indexPreparo > 0) {
                indexPreparo--;
                falar("Passo " + (indexPreparo + 1) + ", " + receita.getModoPreparo().get(indexPreparo));
            } else {
                falar("Não existem passos de preparo antes deste.");
            }
        }
    }

    private void comandoRepetir() {
        if (ondeEstou == INGREDIENTES) {
            if (indexIngredientes > -1) {
                falar("Ingrediente " + (indexIngredientes + 1) + ", " + receita.getIngredientes().get(indexIngredientes));
            } else {
                falar("O passo é -1");
            }
        } else {
            if (indexPreparo > -1) {
                falar("Passo " + (indexPreparo + 1) + ", " + receita.getModoPreparo().get(indexPreparo));
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ExibirReceita Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://isadora.rangovoice/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ExibirReceita Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://isadora.rangovoice/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {

    }
    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onError(Exception error) {
    }

    @Override
    public void onTimeout() {
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE)) {
            recognizer.stop();
            captarVoz();
        }
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {


    }

    private void runRecognizerSetup() {

        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(ExibirReceitaActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    //btnVoice.setText("Falha ao reconhecer " + result);
                    AlertDialog.Builder dlgAlert;
                    dlgAlert = new AlertDialog.Builder(ExibirReceitaActivity.this);
                    dlgAlert.setMessage("Falha ao reconhecer \"" + "\"" );
                    dlgAlert.setTitle("Falha no reconhecimento");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                } else {
                    //btnVoice.setText("Aperte ou diga \"Ok chef Michael\" para começar.");

                    aguardarKeyword();
                }
            }
        }.execute();
    }

    private void aguardarKeyword(){
        recognizer.stop();
        recognizer.startListening(KWS_SEARCH);
    }

    private void setupRecognizer(File assetsDir) throws IOException {

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setKeywordThreshold(1e-45f) // Threshold to tune for keyphrase to balance between false alarms and misses
                .setBoolean("-allphone_ci", true)  // Use context-independent phonetic search, context-dependent is too slow for mobile
                .getRecognizer();
        recognizer.addListener(this);

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
    }

}

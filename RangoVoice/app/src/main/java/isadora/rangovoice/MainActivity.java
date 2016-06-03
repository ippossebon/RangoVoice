package isadora.rangovoice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView list_view;
    public static ArrayList<Receita> receitas;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Define o layout da activity - de acordo com o nome do arquivo xml


        // Get ListView object from xml
        list_view = (ListView) findViewById(R.id.lista_nomes_receitas); // list é o nome do arquivo xml que define <ListView>

        receitas = new ArrayList<Receita>();
        criaReceitas();
        String[] nomes_receitas = criaArrayNomeReceitas();

        /* Adaptors fornecem o conteúdo a interface.
         Primeiro parâmetro: contexto, Segundo: layout da linha, terceiro: ID do textview em que será escrito, quarto: array de conteudo*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.activity_list_item, android.R.id.text1, nomes_receitas);


        // Assign adapter to ListView
        list_view.setAdapter(adapter);



        // ListView Item Click Listener - Determina o que acontece quando um item da lista de receitas é selecionado.
        list_view.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                //Cria nova activity
                Intent secondActivity = new Intent(MainActivity.this, ExibirReceitaActivity.class);

                //Para passar por parâmetro o item selecionado:
                Bundle b = new Bundle();
                b.putInt("id", itemPosition); //Your id
                secondActivity.putExtras(b); //Put your id to your next Intent
                startActivity(secondActivity);

            }

        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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

    private void criaReceitas(){

        // Bolo de chocolate
        ArrayList<String> ingredientes1 = new ArrayList<String>();
        ingredientes1.add("4 ovos");
        ingredientes1.add("150 ml de leite integral");
        ingredientes1.add("120 ml de óleo");
        ingredientes1.add("1 xícara e 1/8 de açúcar mascavo");
        ingredientes1.add("1/2 colher de sopa de extrato de baunilha");
        ingredientes1.add("1 xícara e 1/2 de farinha de trigo");
        ingredientes1.add("1/4 xícara de cacau em pó");
        ingredientes1.add("1/2 colher de chá de bicarbonato de sódio");
        ingredientes1.add("1 colher de chá de fermento");
        ingredientes1.add("1 pitada de sal");

        ArrayList<String> preparo1 = new ArrayList<String>();
        preparo1.add("Misturar ovos, leite, óleo e baunilha.");
        preparo1.add("Adicionar o açúcar.");
        preparo1.add("Misturar todos os ingredientes secos restantes e adicionar à mistura anterior.");
        preparo1.add("Assar à 180 graus Celsius.");

        Receita bolo_chocolate_receita = new Receita("Bolo de chocolate", ingredientes1, preparo1);
        receitas.add(bolo_chocolate_receita);

        // Feijão tropeiro
        ArrayList<String> ingredientes2 = new ArrayList<String>();
        ingredientes2.add("250 gramas de feijão carioquinha.");
        ingredientes2.add("1 maço de couve.");
        ingredientes2.add("300 gramas de linguiça de porco.");
        ingredientes2.add("150 gramas de bacon.");
        ingredientes2.add("3 ovos.");
        ingredientes2.add("5 dentes de alho.");
        ingredientes2.add("1 cebola.");
        ingredientes2.add("Temperos à gosto.");

        ArrayList<String> preparo2 = new ArrayList<String>();
        preparo2.add("Misturar tudo.");

        Receita feijao_tropeiro_receita = new Receita("Feijão tropeiro", ingredientes2, preparo2);
        receitas.add(feijao_tropeiro_receita);

        // Bolinho de arroz
        ArrayList<String> ingredientes3 = new ArrayList<String>();
        ingredientes3.add("Arroz");
        ArrayList<String> preparo3 = new ArrayList<String>();
        preparo3.add("Cozinhar");

        Receita bolinho_arroz_receita = new Receita("Bolinhos de arroz", ingredientes3, preparo3);
        receitas.add(bolinho_arroz_receita);

        //Mousse de maracujá

        ArrayList<String> ingredientes4 = new ArrayList<String>();
        ingredientes4.add("Maracujá");
        ArrayList<String> preparo4 = new ArrayList<String>();
        preparo4.add("Cozinhar");

        Receita mousse_maracuja_receita = new Receita("Mousse de maracujá", ingredientes4, preparo4);
        receitas.add(mousse_maracuja_receita);

        // Massa carbonara
        ArrayList<String> ingredientes5 = new ArrayList<String>();
        ingredientes5.add("Massa");
        ingredientes5.add("Bacon");
        ArrayList<String> preparo5 = new ArrayList<String>();
        preparo5.add("Cozinhar");

        Receita massa_carbonara_receita = new Receita("Massa à carbonara", ingredientes5, preparo5);
        receitas.add(massa_carbonara_receita);

        //Filé ao molho madeira
        ArrayList<String> ingredientes6 = new ArrayList<String>();
        ingredientes6.add("Filé");
        ingredientes6.add("Vinho");
        ArrayList<String> preparo6 = new ArrayList<String>();
        preparo6.add("Cozinhar");

        Receita file_madeira_receita = new Receita("Filé ao molho madeira", ingredientes6, preparo6);
        receitas.add(file_madeira_receita);

        // Pizza caseira
        ArrayList<String> ingredientes7 = new ArrayList<String>();
        ingredientes7.add("Farinha");
        ingredientes7.add("Ovos");
        ArrayList<String> preparo7 = new ArrayList<String>();
        preparo7.add("Cozinhar");

        Receita pizza_receita = new Receita("Pizza caseira", ingredientes7, preparo7);
        receitas.add(pizza_receita);

        // Brigadeiro
        ArrayList<String> ingredientes8 = new ArrayList<String>();
        ingredientes8.add("1 lata de leite condensado");
        ingredientes8.add("2 colheres de sopa de cacau em pó");
        ingredientes8.add("1 colher de sopa de manteiga");
        ArrayList<String> preparo8 = new ArrayList<String>();
        preparo8.add("Juntar todos os ingredientes e cozinhar em fogo médio até que desgrude da panela.");

        Receita brigadeiro_receita = new Receita("Brigadeiro", ingredientes8, preparo8);
        receitas.add(brigadeiro_receita);
    }

    private String[] criaArrayNomeReceitas(){

        String[] nomes = new String[receitas.size()];
        int i = 0;

        for (Receita r : receitas){
            nomes[i] = r.getNome();
            i++;
        }

        return nomes;
    }
}

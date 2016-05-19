package isadora.rangovoice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Define o layout da activity - de acordo com o nome do arquivo xml


        // Get ListView object from xml
        list_view = (ListView) findViewById(R.id.list); // list é o nome do arquivo xml que define <ListView>

        // Defined Array values to show in ListView
        String[] values = new String[] { "Bolo de chocolate", "Feijão tropeiro", "Bolinho de arroz", "Mousse de maracujá",
                "Massa à carbonara",
                "Filé ao molho madeira",
                "Pizza caseira",
                "Brigadeiro"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        // Adaptors fornecem o conteúdo a interface.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.activity_list_item, android.R.id.text1, values);


        // Assign adapter to ListView
        list_view.setAdapter(adapter);

        // ListView Item Click Listener]

        list_view.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) list_view.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

                /* No nosso caso, devemos lançar uma segunda activity a partir desta - exibir a receita. Para isso, usamos um
                * intent. Olhar site "como criar Activity". */

            }

        });

    }
}

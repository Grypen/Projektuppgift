package edvard.projektuppgift;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHandler;
    EditText textField;
    Button btnAddName;
    Button btnViewName;
    Toast toast;
    Cursor result;

    StringBuffer buffer=new StringBuffer();
    int testScore=10; //Poängen är satt så här för att kunna användas i databasens TABLE,
    // den har ingen riktig funktion utan ska bara göra så att TABLE fungerar som det var menat från början.
    //Detta har med att göra att jag inte kunde få "scoreCounter" i spelet att fungera så jag tog bort den
    // och istället så skickar Mainactivity spelarens namn samt en testscore med "Add name" knappen som kan hämtas från databasen med "View Name" knappen





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler=new DBHandler(this);
        dbHandler.open();

        textField=findViewById(R.id.editText);
        btnAddName=findViewById(R.id.button2);
        btnViewName=findViewById(R.id.button3);

        btnAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newScore();
            }
        });

        btnViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewEveryScore();
            }
        });






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button=findViewById(R.id.button); //Button to start game activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);

            }
        });
    }


    private void newScore(){

        String written=textField.getText().toString();

        boolean added=dbHandler.insertScore(written,testScore);
        if (added) {
            toast = Toast.makeText(getApplicationContext(), "Added score to DB", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private void viewEveryScore(){
        result=dbHandler.getEveryScore();

        while(result.moveToNext()){
            buffer.append("Id: "+result.getString(0)+'\n');
            buffer.append("Name: "+result.getString(1)+'\n');
            buffer.append("Score: "+result.getString(2)+"\n\n");
        }

        message("Playernames with scores",buffer.toString());

    }

    private void message(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this); //Appen kraschar om man deklarerar denna utanför metoden
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHandler.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHandler.open();
    }


}

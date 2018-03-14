package culcumstudy.co.kr.memoprac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private EditText editSearch;
    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        toolbar = findViewById(R.id.mainToolbar);
        editSearch = findViewById(R.id.editSearch);
        recyclerList = findViewById(R.id.recyclerList);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("메모리스트");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_add:
                Intent intent = new Intent(this, WriteActivity.class);
                startActivityForResult(intent, Consts.MEMO_ADD);
                break;
            case R.id.menu_load:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package br.com.luizmoratelli.truco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;

import java.util.PriorityQueue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Stack cards = new Stack<String>(){{
        add("ca");
        add("da");
        add("ha");
        add("sa");
        add("c2");
        add("d2");
        add("h2");
        add("s2");
        add("c3");
        add("d3");
        add("h3");
        add("s3");
        add("c4");
        add("d4");
        add("h4");
        add("s4");
        add("c5");
        add("d5");
        add("h5");
        add("s5");
        add("c6");
        add("d6");
        add("h6");
        add("s6");
        add("c7");
        add("d7");
        add("h7");
        add("s7");
        add("cq");
        add("dq");
        add("hq");
        add("sq");
        add("cj");
        add("dj");
        add("hj");
        add("sj");
        add("ck");
        add("dk");
        add("hk");
        add("sk");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

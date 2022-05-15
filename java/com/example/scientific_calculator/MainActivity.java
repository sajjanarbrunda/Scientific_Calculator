package com.example.scientific_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.net.IpSecManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvmain, tvsec;
    final String pi = "3.14159265";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvsec = findViewById(R.id.tvsec);


    }

    public void buttonClickEvent(View view) {
        tvmain = findViewById(R.id.tvmain);
        String cal = tvmain.getText().toString();
        try {
            switch (view.getId()) {
                case R.id.b1:
                    cal += "1";
                    tvmain.setText(cal);
                    break;
                case R.id.b2:
                    cal += "2";
                    tvmain.setText(cal);
                    break;
                case R.id.b3:
                    cal += "3";
                    tvmain.setText(cal);
                    break;
                case R.id.b4:
                    cal += "4";
                    tvmain.setText(cal);
                    break;
                case R.id.b5:
                    cal += "5";
                    tvmain.setText(cal);
                    break;
                case R.id.b6:
                    cal += "6";
                    tvmain.setText(cal);
                    break;
                case R.id.b7:
                    cal += "7";
                    tvmain.setText(cal);
                    break;
                case R.id.b8:
                    cal += "8";
                    tvmain.setText(cal);
                    break;
                case R.id.b9:
                    cal += "9";
                    tvmain.setText(cal);
                    break;
                case R.id.b0:
                    cal += "0";
                    tvmain.setText(cal);
                    break;
                case R.id.bdot:
                    cal += ".";
                    tvmain.setText(cal);
                    break;
                case R.id.bac:
                    tvmain.setText(" ");
                    tvsec.setText(" ");
                    break;
                case R.id.bc:

                    cal = cal.substring(0, cal.length() - 1);
                    tvmain.setText(cal);
                    break;
                case R.id.bplus:
                    cal += "+";
                    tvmain.setText(cal);
                    break;
                case R.id.bmin:
                    cal += "-";
                    tvmain.setText(cal);
                    break;
                case R.id.bmul:
                    cal += "x";
                    tvmain.setText(cal);
                    break;
                case R.id.bdiv:
                    cal += "÷000";
                    tvmain.setText(cal);
                    break;
                case R.id.bsqrt:
                    double r = Math.sqrt(Double.parseDouble(cal));
                    tvmain.setText(String.valueOf(r));
                    break;
                case R.id.bb1:
                    cal += "(";
                    tvmain.setText(cal);
                    break;
                case R.id.bb2:
                    cal += ")";
                    tvmain.setText(cal);
                    break;
                case R.id.bpi:
                    Button bpi = findViewById(R.id.bpi);
                    tvsec.setText(bpi.getText());
//                    tvsec.setText(cal);
                    tvmain.setText(cal + pi);
                    break;
                case R.id.bsin:
                    cal += "sin";
                    tvmain.setText(cal);
                    break;
                case R.id.bcos:
                    cal += "cos";
                    tvmain.setText(cal);
                    break;
                case R.id.btan:
                    cal += "tan";
                    tvmain.setText(cal);
                    break;

                case R.id.binv:
                    cal += "^ " + "(-1)";
                    tvmain.setText(cal);
                    break;
                case R.id.bfact:
                    int val = Integer.parseInt(cal);
                    int fact = factorial(val);
                    tvmain.setText(String.valueOf(fact));
                    tvsec.setText(val + "!");
                    break;

                case R.id.bsquare:
                    double d = Double.parseDouble(cal);
                    double square = d * d;
                    tvmain.setText(String.valueOf(square));
                    tvsec.setText(d + "^2");
                    break;

                case R.id.bln:
                    cal += "ln";
                    tvmain.setText(cal);
                    break;
                case R.id.blog:
                    cal += "log";
                    tvmain.setText(cal);
                    break;
                case R.id.bequal:
                    String replacedstr = cal.replace('÷', '/').replace('x', '*');
                    double result = eval(replacedstr);
                    tvmain.setText(String.valueOf(result));
                    tvsec.setText(cal);
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //eval finction
    private double eval(final String str) {
        return new Object() {
            int pos = -1, ch;



            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected" + (char) ch);
                return x;
            }

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }


            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }


            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }





            boolean eat(int chartoEat) {
                while (ch == ' ') nextChar();
                if (ch == chartoEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknow Function " + func);


                } else {
                    throw new RuntimeException("UnExpected " + (char) ch);

                }
                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }

        }.parse();


    }

    //factorial function
    private int factorial(int val) {
        return (val == 1 || val == 0) ? 1 : val * factorial(val - 1);
    }
}
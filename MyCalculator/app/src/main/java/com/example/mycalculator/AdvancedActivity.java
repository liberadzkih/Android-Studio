package com.example.mycalculator;

import org.mariuszgromada.math.mxparser.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdvancedActivity extends Activity {
    Button zeroButton, oneButton, twoButton;
    Button threeButton, fourButton, fiveButton;
    Button sixButton, sevenButton, eightButton, nineButton;
    Button bkspButton, cButton, pmButton;
    Button divideButton, multiplyButton, minusButton, plusButton;
    Button equalsButton, dotButton;
    TextView wholeText,filledText;
    Button sinButton, cosButton, tgButton, lnButton;
    Button sqrtButton, squaredButton, powerButton,logButton;

    int equalCounter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        filledText = (TextView) findViewById(R.id.filledText);
        wholeText = (TextView) findViewById(R.id.wholeText);

        bkspButton = (Button) findViewById(R.id.bkspButton);
        cButton = (Button) findViewById(R.id.cButton);
        pmButton = (Button) findViewById(R.id.pmButton);
        divideButton = (Button) findViewById(R.id.divideButton);
        multiplyButton = (Button) findViewById(R.id.multiplyButton);
        minusButton = (Button) findViewById(R.id.minusButton);
        plusButton = (Button) findViewById(R.id.plusButton);
        equalsButton = (Button) findViewById(R.id.equalsButton);
        dotButton = (Button) findViewById(R.id.dotButton);

        zeroButton = (Button) findViewById(R.id.zeroButton);
        oneButton = (Button) findViewById(R.id.oneButton);
        twoButton = (Button) findViewById(R.id.twoButton);
        threeButton = (Button) findViewById(R.id.threeButton);
        fourButton = (Button) findViewById(R.id.fourButton);
        fiveButton = (Button) findViewById(R.id.fiveButton);
        sixButton = (Button) findViewById(R.id.sixButton);
        sevenButton = (Button) findViewById(R.id.sevenButton);
        eightButton = (Button) findViewById(R.id.eightButton);
        nineButton = (Button) findViewById(R.id.nineButton);

        sinButton = (Button) findViewById(R.id.sinButton);
        cosButton = (Button) findViewById(R.id.cosButton);
        tgButton = (Button) findViewById(R.id.tgButton);
        lnButton = (Button) findViewById(R.id.lnButton);
        sqrtButton = (Button) findViewById(R.id.sqrtButton);
        squaredButton = (Button) findViewById(R.id.squaredButton);
        powerButton = (Button) findViewById(R.id.powerButton);
        logButton = (Button) findViewById(R.id.logButton);

        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("1");
            }
        });

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("0");
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("2");
            }
        });

        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("3");
            }
        });

        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("4");
            }
        });

        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("5");
            }
        });

        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("6");
            }
        });

        sevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("7");
            }
        });

        eightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("8");
            }
        });

        nineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("9");
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("+");
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("-");
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("*");
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("/");
            }
        });

        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filledText.getText().toString().length() > 0) {
                    if (Character.isDigit(filledText.getText().charAt(filledText.getText().toString().length() - 1))) {
                        if (!filledText.getText().toString().contains(".")) {
                            filledText.setText(filledText.getText() + ".");
                        }
                    }
                }
            }
        });

        bkspButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filledText.getText().length() > 0 && !filledText.getText().toString().equals("ERROR")) {
                    filledText.setText(filledText.getText().toString().substring(0, filledText.getText().toString().length() - 1));
                }
            }
        });

        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalCounter++;
                performAction();
                if(equalCounter > 1 && wholeText.getText().length() > 0) {
                    filledText.setText(wholeText.getText());
                    equalCounter = 0;
                }
            }
        });

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filledText.length() < 1)
                    wholeText.setText("");
                else filledText.setText("");
            }
        });

        pmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    performActionPMButton("*(-1)");
                    filledText.setText(wholeText.getText());
                }
            }
        });

        sinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    filledText.setText("sin(" + filledText.getText().toString() + ")");
                }
            }
        });

        cosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    filledText.setText("cos(" + filledText.getText().toString() + ")");
                }
            }
        });

        tgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    filledText.setText("tan(" + filledText.getText().toString() + ")");
                }
            }
        });

        lnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    filledText.setText("ln(" + filledText.getText().toString() + ")");
                }
            }
        });

        sqrtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    //performActionSqrt("sqrt");
                    //filledText.setText(wholeText.getText());
                    filledText.setText("sqrt(" + filledText.getText().toString() + ")");
                }
            }
        });

        squaredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    performActionSquared();
                    filledText.setText(wholeText.getText());
                }
            }
        });

        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTextView("^");
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filledText.getText().toString().equals("ERROR")) {
                    filledText.setText("log2(" + filledText.getText().toString() + ")");
                }
            }
        });

    }

    public void fillTextView(String symbol) {
        if (!filledText.getText().toString().equals("ERROR")) {
            if (!filledText.getText().toString().equals("0"))
                filledText.setText(filledText.getText() + symbol);
            else filledText.setText(symbol);
        }
    }

    public void performAction() {
        Expression e = new Expression(filledText.getText().toString());
        if(Double.isNaN(e.calculate()))
            filledText.setText("ERROR");
        else
            wholeText.setText(String.valueOf(e.calculate()));
    }

    public void performActionPMButton(String pm) {
        Expression e = new Expression("( " + filledText.getText().toString()+ " ) " + pm);
        if(Double.isNaN(e.calculate()))
            filledText.setText("ERROR");
        else
            wholeText.setText(String.valueOf(e.calculate()));
    }

    public void performActionSquared() {
        Expression e = new Expression(filledText.getText().toString() + "*" + filledText.getText().toString());
        if(Double.isNaN(e.calculate()))
            filledText.setText("ERROR");
        else
            wholeText.setText(String.valueOf(e.calculate()));
    }

    //https://stackoverflow.com/questions/16769654/how-to-use-onsaveinstancestate-and-onrestoreinstancestate
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("filledText", filledText.getText().toString());
        outState.putString("wholeText", wholeText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        filledText.setText(savedInstanceState.getString("filledText"));
        wholeText.setText(savedInstanceState.getString("wholeText"));
    }

}

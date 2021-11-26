package com.lj.calc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {

    public static SqlHelper cal;
    private TextView tv1;
    private TextView tv2;
    private StringBuilder viewStr;
    private int index = 0;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calc);
        cal = new SqlHelper(this);
        //db = calc.getWritableDatabase();
        tv1 = (TextView) findViewById(R.id.mini);
        tv2 = (TextView) findViewById(R.id.max);
        viewStr = new StringBuilder();
        index = 0;
    }

    //历史记录按钮
    public void history(View view) {
        Intent intent = new Intent(this, com.lj.calc.HistoryActivity.class);
        startActivity(intent);
    }

    //数据库插入数据
    public long add(String a, String result) {
        db = cal.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("formula", a);
        values.put("value", result);
        long id = db.insert("a", null, values);
        db.close();
        return id;
    }

    //加减乘除、求余简单运算方法
    public String calc(StringBuilder strB) {
        String allS = strB.toString();
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        int x = 0;
        int y = 0;
        String result = null;
        for (int i = 0; i < 5; i++) {
            int inde = strB.indexOf(asmd[i]);
            if (inde > 0) {
                String a = allS.split(asmd[i])[0];
                String b = allS.split(asmd[i])[1];
                if (i == 0) {
                    result = String.valueOf(Integer.parseInt(a) + Integer.parseInt(b));
                }
                if (i == 1) {
                    result = String.valueOf(Integer.parseInt(a) - Integer.parseInt(b));
                }
                if (i == 2) {
                    result = String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
                }
                if (i == 3) {
                    if (Integer.parseInt(b) == 0) {
                        Toast.makeText(this, "0不能为除数", Toast.LENGTH_SHORT).show();
                        result = String.valueOf(Integer.parseInt(a));
                    } else {
                        result = String.valueOf(Integer.parseInt(a) / Integer.parseInt(b));
                    }
                }
                if (i == 4) {
                    result = String.valueOf(Integer.parseInt(a) % Integer.parseInt(b));
                }
            }
        }
        return result;
    }

    //数字按钮事件
    public void number_0(View view) {
        viewStr.append("0");
        index++;
        tv2.setText(viewStr);
    }

    public void number_1(View view) {
        viewStr.append("1");
        index++;
        tv2.setText(viewStr);
    }

    public void number_2(View view) {
        viewStr.append("2");
        index++;
        tv2.setText(viewStr);
    }

    public void number_3(View view) {
        viewStr.append("3");
        index++;
        tv2.setText(viewStr);
    }

    public void number_4(View view) {
        viewStr.append("4");
        index++;
        tv2.setText(viewStr);
    }

    public void number_5(View view) {
        viewStr.append("5");
        index++;
        tv2.setText(viewStr);
    }

    public void number_6(View view) {
        viewStr.append("6");
        index++;
        tv2.setText(viewStr);
    }

    public void number_7(View view) {
        viewStr.append("7");
        index++;
        tv2.setText(viewStr);
    }

    public void number_8(View view) {
        viewStr.append("8");
        index++;
        tv2.setText(viewStr);
    }

    public void number_9(View view) {
        viewStr.append("9");
        index++;
        tv2.setText(viewStr);
    }

    //回退按钮事件
    public void backsprce(View view) {
        if (viewStr.length() == 0) return;
        index = viewStr.length();
        viewStr.deleteCharAt(--index);
        tv2.setText(viewStr);
    }

    //符号改变按钮事件
    public void change(View view) {
        String allS = viewStr.toString();
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        int inde = 0;
        String a = null;    //保存字符串中运算符部分
        String b = null;    //保存字符串中数字部分
        for (int i = 0; i < 5; i++) {
            inde = viewStr.indexOf(asmd[i]);
            if (inde != -1) {
                a = asmd[i];
                break;
            }
        }
        //A 字符形 改变 A 正负值
        if (inde == -1) {
            clearc(view);
            int c = -Integer.parseInt(allS);
            viewStr.append(String.valueOf(c));
            index = String.valueOf(c).length();
            tv2.setText(viewStr);
        }
        //A + 字符串形 改变 A 正负值 暂时无法实现，待后续优化
        if (inde == index - 1) {
            return;
//            clearc(view);
//            int c = - Integer.valueOf(allS.split(a)[0]);
//            viewStr.append(String.valueOf(c));
//            index = String.valueOf(c).length();
//            tv2.setText(viewStr);
        }
        //A + B 字符串形 改变 B 正负值
        if (inde >= 0 && inde < index) {
            clearc(view);
            b = allS.split(a)[0];
            int c = -Integer.parseInt(allS.split(a)[1]);
            viewStr.append(b).append(a).append(String.valueOf(c));
            index = String.valueOf(c).length();
            tv2.setText(viewStr);
        }
    }

    //清空按钮事件
    public void clearc(View view) {
        StringBuilder temp = new StringBuilder();
        viewStr = temp.append("");
        tv2.setText(viewStr);
        index = 0;
    }

    public void clearce(View view) {
        StringBuilder temp = new StringBuilder();
        viewStr = temp.append("");
        tv1.setText("");
        tv2.setText(viewStr);
        index = 0;
    }

    //等于按钮事件
    public void equal(View view) {
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        StringBuilder temp = new StringBuilder();
        String aa = viewStr.toString();
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > 0 && inde != index - 1) {
                tv1.setText(calc(viewStr));
                String a = calc(viewStr);
                viewStr = temp.append(a);
                tv2.setText(viewStr);
                index = viewStr.length();
                long id = add(aa + "=", viewStr.toString());
                return;
            } else if (inde > 0 && inde == index - 1) {
                viewStr.deleteCharAt(--index);
                tv1.setText(viewStr);
                tv2.setText(viewStr);
            }
        }
        tv1.setText(viewStr);
        tv2.setText(viewStr);
    }

    //加减乘除按钮事件
    public void addition(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > 0 && viewStr.charAt(index - 1) >= '0' && viewStr.charAt(index - 1) <= '9') {
                tv1.setText(calc(viewStr));
                String a = calc(viewStr);
                viewStr = temp.append(a).append('＋');
                tv2.setText(viewStr);
                index = viewStr.length();
                return;
            }
        }
        char a = viewStr.charAt(index - 1);
        if (a == '＋') {
            return;
        }
        if (a == '－' || a == '×' || a == '÷' || a == '%') {
            viewStr.setCharAt(index - 1, '＋');
        } else {
            viewStr.append("＋");
            index++;
        }
        tv2.setText(viewStr);
    }

    public void subtraction(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > 0 && viewStr.charAt(index - 1) >= '0' && viewStr.charAt(index - 1) <= '9') {
                tv1.setText(calc(viewStr));
                String a = calc(viewStr);
                viewStr = temp.append(a).append('－');
                tv2.setText(viewStr);
                index = viewStr.length();
                return;
            }
        }
        char a = viewStr.charAt(index - 1);
        if (a == '－') {
            return;
        }
        if (a == '＋' || a == '×' || a == '÷' || a == '%') {
            viewStr.setCharAt(index - 1, '－');
        } else {
            viewStr.append("－");
            index++;
        }
        tv2.setText(viewStr);
    }

    public void multiplication(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > 0 && viewStr.charAt(index - 1) >= '0' && viewStr.charAt(index - 1) <= '9') {
                tv1.setText(calc(viewStr));
                String a = calc(viewStr);
                viewStr = temp.append(a).append('×');
                tv2.setText(viewStr);
                index = viewStr.length();
                return;
            }
        }
        char a = viewStr.charAt(index - 1);
        if (a == '×') {
            return;
        }
        if (a == '＋' || a == '－' || a == '÷' || a == '%') {
            viewStr.setCharAt(index - 1, '×');
        } else {
            viewStr.append("×");
            index++;
        }
        tv2.setText(viewStr);
    }

    public void division(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > 0 && viewStr.charAt(index - 1) >= '0' && viewStr.charAt(index - 1) <= '9') {
                tv1.setText(calc(viewStr));
                String a = calc(viewStr);
                viewStr = temp.append(a).append('÷');
                tv2.setText(viewStr);
                index = viewStr.length();
                return;
            }
        }
        char a = viewStr.charAt(index - 1);
        if (a == '÷') {
            return;
        }
        if (a == '＋' || a == '－' || a == '×' || a == '%') {
            viewStr.setCharAt(index - 1, '÷');
        } else {
            viewStr.append("÷");
            index++;
        }
        tv2.setText(viewStr);
    }

    //求余按钮事件
    public void surplus(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > 0 && viewStr.charAt(index - 1) >= '0' && viewStr.charAt(index - 1) <= '9') {
                tv1.setText(calc(viewStr));
                String a = calc(viewStr);
                viewStr = temp.append(a).append('%');
                tv2.setText(viewStr);
                index = viewStr.length();
                return;
            }
        }
        char a = viewStr.charAt(index - 1);
        if (a == '%') {
            return;
        }
        if (a == '＋' || a == '－' || a == '×' || a == '÷') {
            viewStr.setCharAt(index - 1, '%');
        } else {
            viewStr.append("%");
            index++;
        }
        tv2.setText(viewStr);
    }

    //求倒数按钮事件
    public void reciprocal(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > -1) {
                return;
            }
        }
        int a = Integer.parseInt(viewStr.toString().trim());
        if (a == 0) {
            Toast.makeText(this, "0不能为除数", Toast.LENGTH_SHORT).show();
            return;
        }
        clearc(view);
        viewStr.append(1 / a);
        index = String.valueOf(1 / a).length();
        tv2.setText(viewStr);
    }

    //平方按钮事件
    public void square(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > -1) {
                return;
            }
        }
        int a = Integer.parseInt(viewStr.toString().trim());
        clearc(view);
        viewStr.append(a * a);
        index = String.valueOf(a * a).length();
        tv2.setText(viewStr);
    }

    //开平方按钮事件
    public void squareroot(View view) {
        if (viewStr.length() == 0) return;
        String[] asmd = {"＋", "－", "×", "÷", "%"};
        for (int i = 0; i < 5; i++) {
            int inde = viewStr.indexOf(asmd[i]);
            if (inde > -1) {
                return;
            }
        }
        int a = Integer.parseInt(viewStr.toString().trim());
        clearc(view);
        viewStr.append((int) Math.sqrt(a));
        index = String.valueOf((int) Math.sqrt(a)).length();
        tv2.setText(viewStr);
    }

}

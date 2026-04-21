package org.example;



//다항식 계산기 구현
//Stack 자료구조 사용금지, 재귀함수로 풀어주세요.
//GPT 사용금지
//t1 부터 순서대로 클리어 해주세요.
//build.gradle.kts의 dependencies 블록에 testImplementation("org.assertj:assertj-core:3.27.6") 추가하여 assertThat 사용
//TDD의 3단계 싸이클 : red, green, blue
//
//red == 실패 : 실패하는 테스트를 작성
//목표를 설정하는 단계
//green == 성공 : 최대한 꼼수를 써서 테스트를 통과시킴
//어떠한 방법으로더 더 이상 꼼수를 쓸 수 없거나 구현을 쉽게할 수 있는 상태라면 구현
//blue == 리팩토링 : 리팩토링(목표를 달성하는 단계)
//리팩토링을 하면서 자연스럽게 구현을 할 수 있으면 구현을 해도 됨
//성공하는 테스트케이스를 더 추가해도 됩니다.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {
    public static int run(String mathEx)
    {
      int answer = 0;
      List<String> str = new ArrayList<>(Arrays.asList(parenthesis(mathEx,0).split(" ")));
      answer = Integer.parseInt(priorityParse(str));
      return answer;
    }

    private static String parsingProc(List<String> mathEx, int idx, String num)
    {
        //t1 ~ t14
        if(mathEx.size() <= idx)return num;
        String cur = mathEx.get(idx);
        String answer = num;
        switch(cur)
        {
            case "+":
                answer = plus(num, mathEx.get(idx+1));
                break;
            case "-":
                answer = minus(num, mathEx.get(idx+1));
                break;
            case "*":
                answer = multi(num, mathEx.get(idx+1));
                break;
        }
        return parsingProc(mathEx,idx+1, answer);
    }

    //곱하기 나누기 처리
    private static String priorityParse(List<String> mathEx)
    {
        List<String> changedMathEx = new ArrayList<>();
        for(int i = 0; i<mathEx.size(); i++)
        {
            String cur = mathEx.get(i);
            if(cur.equals("*") || cur.equals("/")) {
                String num1 = changedMathEx.getLast(); changedMathEx.removeLast();
                String num2 = mathEx.get(i+1);

                switch (cur) {
                    case "*":
                        cur = multi(num1, num2);
                        break;
                    case "/":
                        cur = div(num1, num2);
                        break;
                }
                i++;
            }
            changedMathEx.add(cur);
        }
        return parsingProc(changedMathEx,0, changedMathEx.getFirst());
    }

    //괄호 처리
    private static String parenthesis(String stringMathEx, int start)
    {
        int i = start;
        String answer = stringMathEx;
        while(i<stringMathEx.length() && stringMathEx.charAt(i) !=')')
        {
            if(stringMathEx.charAt(i)=='(') {
                answer = parenthesis(answer, i + 1);
                stringMathEx = answer;
            }
            i++;
        }
        if(answer.charAt(start) == '(') start++;
        String substr = answer.substring(start, i);
        List<String> subMathEx = new ArrayList<>(Arrays.asList(substr.split(" ")));
        answer = answer.replace("("+substr+")", priorityParse(subMathEx));
        return answer;
    }

    private static  String plus(String num1, String num2)
    {
        int num3 = Integer.parseInt(num1) + Integer.parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
    private static String minus(String num1, String num2)
    {
        int num3 = Integer.parseInt(num1) - Integer.parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
    private static String multi(String num1, String num2)
    {
        int num3 = Integer.parseInt(num1) * Integer.parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
    private static String div(String num1, String num2)
    {
        if(num2 == "0") return num1;
        int num3 = Integer.parseInt(num1) / Integer.parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
}

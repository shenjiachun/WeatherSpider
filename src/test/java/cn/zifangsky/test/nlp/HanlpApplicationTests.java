package cn.zifangsky.test.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HanlpApplicationTests {

    @Test
    public void contextLoads() throws IOException {

//        String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
//                "算法可以宽泛的分为三类，\n" +
//                "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
//                "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
//                "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
//        List<String> sentenceList = HanLP.extractSummary(document, 10);
//        System.out.println(sentenceList);

        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
        analyzer.enableMultithreading(true);
        CustomDictionary.insert("2019年一季报每股收益", "nz 1000");
        CustomDictionary.insert("净利润", "nz 1001");
        CustomDictionary.insert("同比去年增长", "nz 1002");
        System.out.println(HanLP.segment("2019年一季报每股收益0.26元净利润1.93亿元同比去年增长-86.47%"));
        System.out.println(HanLP.segment("2019年一季报每股收益0.15元净利润5281.18万元同比去年增长27.25%"));
        System.out.println(analyzer.analyze("2019年一季报每股收益0.26元净利润1.93亿元同比去年增长-86.47%"));
        System.out.println(analyzer.analyze("2019年一季报每股收益0.15元净利润5281.18万元同比去年增长27.25%"));


    }

}


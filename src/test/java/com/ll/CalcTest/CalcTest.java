package com.ll.CalcTest;

import jdk.javadoc.doclet.Doclet;
import org.example.App;
import org.example.Calc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalcTest {
    @Test
    @DisplayName("1+1=2")
    void t1(){
        assertThat(Calc.run("1+1")).isEqualTo(2);
    }
}

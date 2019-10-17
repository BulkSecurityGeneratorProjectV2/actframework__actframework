package act.util;

/*-
 * #%L
 * ACT Framework
 * %%
 * Copyright (C) 2014 - 2019 ActFramework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import act.Act;
import org.osgl.$;
import org.osgl.util.S;

class StringUtils {

    static $.Transformer<String, String> evaluator = new $.Transformer<String, String>() {
        @Override
        public String transform(String s) {
            return S.string(System.getProperty(s));
        }
    };


    public static String processStringSubstitution(String s) {
        return processStringSubstitution(s, evaluator);
    }

    public static String processStringSubstitution(String s, $.Func1<String, String> evaluator) {
        if (S.blank(s)) {
            return "";
        }
        int n = s.indexOf("${");
        if (n < 0) {
            return s;
        }
        int a = 0;
        int z = n;
        StringBuilder buf = S.builder();
        while (true) {
            buf.append(s.substring(a, z));
            n = s.indexOf("}", z);
            a = n + 1;
            String key = s.substring(z + 2, a);
            buf.append(evaluator.apply(key));
            n = s.indexOf("${", a);
            if (n < 0) {
                buf.append(s.substring(a));
                return buf.toString();
            }
            z = n;
        }
    }

}

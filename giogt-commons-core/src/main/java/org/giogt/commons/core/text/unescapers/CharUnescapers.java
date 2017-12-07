package org.giogt.commons.core.text.unescapers;

import org.giogt.commons.core.text.Unescaper;

public class CharUnescapers {

    public static Unescaper uriUnescaper() {
        return URI_UNESCAPER;
    }

    private static final Unescaper URI_UNESCAPER = new PercentUnescaper();

}

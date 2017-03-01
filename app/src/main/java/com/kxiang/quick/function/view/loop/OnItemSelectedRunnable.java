// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.kxiang.quick.function.view.loop;

// Referenced classes of package com.qingchifan.view:
//            LoopView, OnItemSelectedListener

final class OnItemSelectedRunnable implements Runnable {
    final LoopView loopView;

    OnItemSelectedRunnable(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        if (loopView.onItemSelectedListener!=null){
            loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
        }
        loopView.setLoopScrool(false);
    }
}

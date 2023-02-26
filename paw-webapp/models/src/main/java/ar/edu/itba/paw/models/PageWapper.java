package ar.edu.itba.paw.models;

import java.util.List;

public class PageWapper<T> {
    private final int page;
    private final long pageAmount;
    private final int pageSize;
    private final List<T> pageContent;

    public PageWapper(int page, long pageAmount,int pageSize, List<T> pageContent){
        this.page=page;
        this.pageAmount=pageAmount;
        this.pageContent=pageContent;
        this.pageSize=pageSize;
    }

    public static int calculatePageAmount(long elems, int pageSize) {
        final double result = elems / (double) pageSize;
        final int pageQty = (int) Math.ceil(result);
        return pageQty == 0 ? 1 : pageQty;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public long getPageAmount() {
        return pageAmount;
    }

    public List<T> getPageContent() {
        return pageContent;
    }
}

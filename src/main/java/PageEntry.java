public class PageEntry implements Comparable<PageEntry> {
    private String pdf;
    private int page;
    private int count;

    public PageEntry(String pdf, int page, int count) {
        this.pdf = pdf;
        this.page = page;
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdfName='" + pdf + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(PageEntry o) {
        return Integer.compare(o.count, this.count);
    }

    public String getPdf(){
        return pdf;
    }

    public int getPage(){
        return page;
    }

    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }
}

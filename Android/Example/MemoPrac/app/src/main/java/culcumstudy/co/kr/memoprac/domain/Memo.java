package culcumstudy.co.kr.memoprac.domain;

/**
 * Created by Kyung on 2018-03-12.
 */

public class Memo {

    private static final String DELIMETER ="//";

    private int no;
    private String title;
    private String content;
    private String datetime;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("no").append(DELIMETER).append(getNo()).append("\n");
        result.append("title").append(DELIMETER).append(getTitle()).append("\n");
        result.append("content").append(DELIMETER).append(getContent()).append("\n");
        result.append("datetime").append(DELIMETER).append(getDatetime()).append("\n");

        return result.toString();
    }

    public byte[] toByte(){
        return toString().getBytes();
    }
}

package entity;

import lombok.Data;

@Data
public class CC {
    private int id;

    private C1 c1;
    private C2 c2;

    @Data
    public static class C1{
        private int c1Id;
        private String c1Val;
    }

    @Data
    public static class C2{
        private int c2Id;
        private String c2Val;
    }
}

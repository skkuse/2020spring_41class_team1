package edu.skku.swe.idecide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import edu.skku.swe.idecide.entities.Hardware;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.ItemAdapter;
import edu.skku.swe.idecide.entities.Review;
import edu.skku.swe.idecide.entities.Vendor;

public class HistoryDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Item> list = new ArrayList<>();
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_history_detail);
        tb.setTitle("검색 기록");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");



        recyclerView = findViewById(R.id.rv_history_detail);

        // 원래는 파이어베이스 history detail에서 받아와야함!!
        // 1 갤럭시북 플렉스
        List<Vendor> vendors1 = new ArrayList<>();
        vendors1.add(new Vendor("G마켓", 2246990, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1778440674&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors1.add(new Vendor("옥션", 2246990, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B827869663&frm3=V2"));
        vendors1.add(new Vendor("11번가", 2246990, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2660909695&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        vendors1.add(new Vendor("G9", 2246990, 0, "http://www.g9.co.kr/Display/VIP/Index/1716631272?jaehuid=200008154&service_id=pcdn"));
        vendors1.add(new Vendor("인터파크", 2246990, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6941025934&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors1.add(new Vendor("위메프", 2246990, 0, "https://front.wemakeprice.com/product/407371065?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        list.add(new Item(user_key, "NT930QCG-K716A", "삼성전자", "갤럭시북 플렉스", "#1", "98",
                new Hardware(90, 100, 100, 80, 80, 100),
                new Review(80, 70, 100, 95, 80, 100), vendors1));


        // 2 갤럭시북 플렉스 알파
        List<Vendor> vendors2 = new ArrayList<>();
        vendors2.add(new Vendor("옥션", 1428990, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B862895531&frm3=V2"));
        vendors2.add(new Vendor("G마켓", 1428990, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1825393284&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors2.add(new Vendor("위메프", 1429000, 0, "https://front.wemakeprice.com/product/1068959562?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        vendors2.add(new Vendor("인터파크", 1429000, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=7187814977&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors2.add(new Vendor("11번가", 1429000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2848295590&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        list.add(new Item(user_key, "NT750QCR-A38A", "삼성전자", "갤럭시북 플렉스 알파", "#2", "92",
                new Hardware(90, 100, 100, 75, 75, 100),
                new Review(70, 75, 100, 90, 80, 90), vendors2));


        // 3 갤럭시북 S
        List<Vendor> vendors3 = new ArrayList<>();
        vendors3.add(new Vendor("옥션", 1160990, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B856123220&frm3=V2"));
        vendors3.add(new Vendor("G마켓", 1160990, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1812571972&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors3.add(new Vendor("쿠팡", 1161000, 0, "https://www.coupang.com/vp/products/1597591528?itemId=2729364282&vendorItemId=70719521892&src=1032034&spec=10305201&addtag=400&ctag=1597591528&lptag=P1597591528&itime=20200614192532&pageType=PRODUCT&pageValue=1597591528&wPcid=15921278701852654063169&wRef=prod.danawa.com&wTime=20200614192532&redirect=landing&isAddedCart="));
        vendors3.add(new Vendor("인터파크", 1186800, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=7213318675&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors3.add(new Vendor("위메프", 1199000, 0, "https://front.wemakeprice.com/product/420770856?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        vendors3.add(new Vendor("G9", 1199000, 0, "http://www.g9.co.kr/Display/VIP/Index/1718111587?jaehuid=200008154&service_id=pcdn"));
        vendors3.add(new Vendor("11번가", 1199000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2661006864&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));

        list.add(new Item(user_key, "SM-W767NZNDKOO", "삼성전자", "갤럭시북 S", "#3", "92",
                new Hardware(90, 90, 100, 80, 75, 100),
                new Review(90, 100, 100, 75, 80, 80), vendors3));



        // 4 갤럭시북 이온
        List<Vendor> vendors3_1= new ArrayList<>();
        vendors3_1.add(new Vendor("옥션", 1598990, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B861268868&frm3=V2"));
        vendors3_1.add(new Vendor("G마켓", 1598990, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1823735442&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors3_1.add(new Vendor("인터파크", 1599000, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6940928592&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors3_1.add(new Vendor("11번가", 1599000, 0, "http://www.11st.co.kr/html/nc/SellerProduct2660912308.html"));
        vendors3_1.add(new Vendor("G9", 1599000, 0, "http://www.g9.co.kr/Display/VIP/Index/1718115068?jaehuid=200008154&service_id=pcdn"));
        vendors3_1.add(new Vendor("위메프", 1615830, 0, "https://front.wemakeprice.com/product/407361146?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        list.add(new Item(user_key, "NT950XCR-G58A", "삼성전자", "갤럭시북 이온", "#4", "90",
                new Hardware(90, 90, 100, 75, 75, 100),
                new Review(90, 90, 100, 70, 80, 100), vendors3_1));

        // 5 그램
        List<Vendor> vendors1_2= new ArrayList<>();
        vendors1_2.add(new Vendor("G9", 1498980, 0, "http://www.g9.co.kr/Display/VIP/Index/1793955243?jaehuid=200008154&service_id=pcdn"));
        vendors1_2.add(new Vendor("G마켓", 1499000, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1714224910&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors1_2.add(new Vendor("인터파크", 1499000, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=7007374549&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors1_2.add(new Vendor("옥션", 1522490, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B824484427&frm3=V2"));
        vendors1_2.add(new Vendor("위메프", 1529000, 0, "https://front.wemakeprice.com/product/419077904?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        list.add(new Item(user_key, "15ZD90N-VX50K", "LG전자", "그램", "#5", "90",
                new Hardware(90, 90, 100, 70, 60, 80),
                new Review(100, 80, 100, 80, 70, 80), vendors1_2));

        // 6 블래이드
        List<Vendor> vendors4_2 = new ArrayList<>();
        vendors4_2.add(new Vendor("11번가", 1899000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2885864840&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        vendors4_2.add(new Vendor("G마켓", 2239050, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1822143684&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors4_2.add(new Vendor("옥션", 2337470, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B862342031&frm3=V2"));
        vendors4_2.add(new Vendor("위메프", 2390000, 0, "https://front.wemakeprice.com/product/1112487790?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        list.add(new Item(user_key, "Base 9Gen", "Razer", "블레이드", "#6", "90",
                new Hardware(50, 90, 90, 100, 100, 70),
                new Review(40, 100, 100, 100, 90, 60), vendors4_2));

        // 7 인스피론
        List<Vendor> vendors2_2= new ArrayList<>();
        vendors2_2.add(new Vendor("위메프", 1586500, 0, "https://front.wemakeprice.com/product/213497757?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
        vendors2_2.add(new Vendor("옥션", 1593100, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B720061734&frm3=V2"));
        vendors2_2.add(new Vendor("G마켓", 1593100, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1652809478&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors2_2.add(new Vendor("11번가", 1599000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2481662244&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        vendors2_2.add(new Vendor("인터파크", 1609320, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6664034627&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors2_2.add(new Vendor("G9", 1682600, 0, "http://www.g9.co.kr/Display/VIP/Index/1652809478?jaehuid=200008154&service_id=pcdn"));
        list.add(new Item(user_key, "D001I759003KR", "Dell", "인스피론", "#7", "85",
                new Hardware(70, 80, 100, 95, 90, 70),
                new Review(60, 70, 90, 100, 90, 80), vendors2_2));

        // 8 GF 시리즈
        List<Vendor> vendors4_1 = new ArrayList<>();
        vendors4_1.add(new Vendor("옥션", 1259460, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B757664772&frm3=V2"));
        vendors4_1.add(new Vendor("G마켓", 1259460, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1694752668&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors4_1.add(new Vendor("인터파크", 1259460, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6843916048&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
        vendors4_1.add(new Vendor("G9", 1259460, 0,"http://www.g9.co.kr/Display/VIP/Index/1679650626?jaehuid=200008154&service_id=pcdn"));
        vendors4_1.add(new Vendor("11번가", 1259460, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2573889876&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        vendors4_1.add(new Vendor("쿠팡", 1259460, 0, "https://www.coupang.com/vp/products/1073157144?itemId=2022232165&vendorItemId=70021898634&src=1032034&spec=10305201&addtag=400&ctag=1073157144&lptag=P1073157144&itime=20200614184430&pageType=PRODUCT&pageValue=1073157144&wPcid=15921278701852654063169&wRef=prod.danawa.com&wTime=20200614184430&redirect=landing&isAddedCart="));
        list.add(new Item(user_key, "GF75 Thin 9SC", "MSI", "GF 시리즈", "#8", "85",
                new Hardware(50, 60, 90, 100, 90, 80),
                new Review(40, 50, 100, 100, 90, 70), vendors4_1));

        // 9 요가북
        List<Vendor> vendors3_2= new ArrayList<>();
        vendors3_2.add(new Vendor("11번가", 890000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2797364184&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        vendors3_2.add(new Vendor("옥션", 890000, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B827839285&frm3=V2"));
        vendors3_2.add(new Vendor("G9", 890000, 0, "http://www.g9.co.kr/Display/VIP/Index/1778398683?jaehuid=200008154&service_id=pcdn"));
        list.add(new Item(user_key, "C930 M3", "Lenovo", "요가북", "#9", "75",
                new Hardware(100, 70, 40, 50, 40, 70),
                new Review(100, 60, 50, 60, 40, 70), vendors3_2));

        // 10 swift
        List<Vendor> vendors5_1 = new ArrayList<>();
        vendors5_1.add(new Vendor("11번가", 969000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2856045833&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
        vendors5_1.add(new Vendor("G마켓", 979200, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1825144276&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
        vendors5_1.add(new Vendor("옥션", 979200, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B862662726&frm3=V2"));
        list.add(new Item(user_key, "SF314-42", "Acer", "스위프트 3", "#10", "70",
                new Hardware(80, 90, 60, 70, 80, 40),
                new Review(60, 70, 80, 80, 70, 70), vendors5_1));

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ItemAdapter(list));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
package edu.skku.swe.idecide.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.skku.swe.idecide.entities.Hardware;
import edu.skku.swe.idecide.entities.HistoryAdapter;
import edu.skku.swe.idecide.entities.HomeCard;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.HomeStackAdapter;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.Review;
import edu.skku.swe.idecide.entities.User;
import edu.skku.swe.idecide.entities.Vendor;

public class FragmentHome extends Fragment implements CardStackView.ItemExpendListener {
    private ArrayList<HomeCard> homeCards = new ArrayList<>();
    private RecyclerView recyclerView;
    private String user_key;
    private TextView nicknameTV, nickname2TV;
    private String nickname = "";

    private HomeStackAdapter homeStackAdapter;
    private CardStackView cardStackView;

    public FragmentHome(String user_key) { this.user_key = user_key; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public  View onCreateView( @NonNull LayoutInflater inflater,
                              @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_home,container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_item_homecard);
        nicknameTV = rootView.findViewById(R.id.nickname_home);
        nickname2TV = rootView.findViewById(R.id.nickname2_home);

        if (nickname.length() == 0) {
            // get and set user nickname
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("User").document(user_key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    User user = new User(document.getData());
                    nickname = user.getNickname();
                    if (nickname.length() == 0) {
                        nickname = "회원";
                    }
                    nicknameTV.setText(nickname + "님,");
                    nickname2TV.setText(nickname);
                }
            });
        }
        else {
            nicknameTV.setText(nickname + "님,");
            nickname2TV.setText(nickname);
        }

        cardStackView = rootView.findViewById(R.id.card_homecard);
        cardStackView.setItemExpendListener(this);


        if (homeCards.isEmpty()) {

            List<Item> list1 = new ArrayList<>();
            // 비보북
            List<Vendor> vendors1_1= new ArrayList<>();
            vendors1_1.add(new Vendor("옥션", 840030, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B850727871&frm3=V2"));
            vendors1_1.add(new Vendor("위메프", 840040, 0, "https://front.wemakeprice.com/product/1088305743?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
            vendors1_1.add(new Vendor("G마켓", 840040, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1809053797&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors1_1.add(new Vendor("11번가", 849000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2874173010&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors1_1.add(new Vendor("쿠팡", 849000, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=7227310096&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
            list1.add(new Item(user_key, "S533FL-BQ504", "Asus", "비보북", "", "70",
                    new Hardware(80, 90, 90, 80, 50, 60),
                    new Review(70, 90, 90, 70, 50, 40), vendors1_1));
            // 그램
            List<Vendor> vendors1_2= new ArrayList<>();
            vendors1_2.add(new Vendor("G9", 1498980, 0, "http://www.g9.co.kr/Display/VIP/Index/1793955243?jaehuid=200008154&service_id=pcdn"));
            vendors1_2.add(new Vendor("G마켓", 1499000, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1714224910&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors1_2.add(new Vendor("인터파크", 1499000, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=7007374549&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
            vendors1_2.add(new Vendor("옥션", 1522490, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B824484427&frm3=V2"));
            vendors1_2.add(new Vendor("위메프", 1529000, 0, "https://front.wemakeprice.com/product/419077904?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
            list1.add(new Item(user_key, "15ZD90N-VX50K", "LG전자", "그램", "", "90",
                    new Hardware(90, 90, 100, 70, 60, 80),
                    new Review(100, 80, 100, 80, 70, 80), vendors1_2));



            List<Item> list2 = new ArrayList<>();
            // GF 시리즈
            List<Vendor> vendors2_1= new ArrayList<>();
            vendors2_1.add(new Vendor("옥션", 1259460, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B757664772&frm3=V2"));
            vendors2_1.add(new Vendor("G마켓", 1259460, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1694752668&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors2_1.add(new Vendor("인터파크", 1259460, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6843916048&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
            vendors2_1.add(new Vendor("G9", 1259460, 0,"http://www.g9.co.kr/Display/VIP/Index/1679650626?jaehuid=200008154&service_id=pcdn"));
            vendors2_1.add(new Vendor("11번가", 1259460, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2573889876&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors2_1.add(new Vendor("쿠팡", 1259460, 0, "https://www.coupang.com/vp/products/1073157144?itemId=2022232165&vendorItemId=70021898634&src=1032034&spec=10305201&addtag=400&ctag=1073157144&lptag=P1073157144&itime=20200614184430&pageType=PRODUCT&pageValue=1073157144&wPcid=15921278701852654063169&wRef=prod.danawa.com&wTime=20200614184430&redirect=landing&isAddedCart="));
            list2.add(new Item(user_key, "GF75 Thin 9SC", "MSI", "GF 시리즈", "", "85",
                    new Hardware(50, 60, 90, 100, 90, 80),
                    new Review(40, 50, 100, 100, 90, 70), vendors2_1));
            // 인스피론
            List<Vendor> vendors2_2= new ArrayList<>();
            vendors2_2.add(new Vendor("위메프", 1586500, 0, "https://front.wemakeprice.com/product/213497757?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
            vendors2_2.add(new Vendor("옥션", 1593100, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B720061734&frm3=V2"));
            vendors2_2.add(new Vendor("G마켓", 1593100, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1652809478&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors2_2.add(new Vendor("11번가", 1599000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2481662244&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors2_2.add(new Vendor("인터파크", 1609320, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6664034627&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
            vendors2_2.add(new Vendor("G9", 1682600, 0, "http://www.g9.co.kr/Display/VIP/Index/1652809478?jaehuid=200008154&service_id=pcdn"));
            list2.add(new Item(user_key, "D001I759003KR", "Dell", "인스피론", "", "95",
                    new Hardware(70, 80, 100, 95, 90, 70),
                    new Review(60, 70, 90, 100, 90, 80), vendors2_2));



            List<Item> list3 = new ArrayList<>();
            // 갤럭시북 이온
            List<Vendor> vendors3_1= new ArrayList<>();
            vendors3_1.add(new Vendor("옥션", 1598990, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B861268868&frm3=V2"));
            vendors3_1.add(new Vendor("G마켓", 1598990, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1823735442&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors3_1.add(new Vendor("인터파크", 1599000, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6940928592&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
            vendors3_1.add(new Vendor("11번가", 1599000, 0, "http://www.11st.co.kr/html/nc/SellerProduct2660912308.html"));
            vendors3_1.add(new Vendor("G9", 1599000, 0, "http://www.g9.co.kr/Display/VIP/Index/1718115068?jaehuid=200008154&service_id=pcdn"));
            vendors3_1.add(new Vendor("위메프", 1615830, 0, "https://front.wemakeprice.com/product/407361146?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
            list3.add(new Item(user_key, "NT950XCR-G58A", "삼성전자", "갤럭시북 이온", "", "90",
                    new Hardware(90, 90, 100, 75, 75, 100),
                    new Review(90, 90, 100, 70, 80, 100), vendors3_1));
            // 요가북
            List<Vendor> vendors3_2= new ArrayList<>();
            vendors3_2.add(new Vendor("11번가", 890000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2797364184&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors3_2.add(new Vendor("옥션", 890000, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B827839285&frm3=V2"));
            vendors3_2.add(new Vendor("G9", 890000, 0, "http://www.g9.co.kr/Display/VIP/Index/1778398683?jaehuid=200008154&service_id=pcdn"));
            list3.add(new Item(user_key, "C930 M3", "Lenovo", "요가북", "", "75",
                    new Hardware(100, 70, 40, 50, 40, 70),
                    new Review(100, 60, 50, 60, 40, 70), vendors3_2));



            List<Item> list4 = new ArrayList<>();
            // GF 시리즈
            List<Vendor> vendors4_1 = new ArrayList<>();
            vendors4_1.add(new Vendor("옥션", 1259460, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B757664772&frm3=V2"));
            vendors4_1.add(new Vendor("G마켓", 1259460, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1694752668&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors4_1.add(new Vendor("인터파크", 1259460, 0, "http://shopping.interpark.com/product/productInfo.do?prdNo=6843916048&dispNo=016001&bizCd=P01415&utm_medium=affiliate&utm_source=danawa&utm_campaign=shop_p11736_p01415&utm_content=main"));
            vendors4_1.add(new Vendor("G9", 1259460, 0,"http://www.g9.co.kr/Display/VIP/Index/1679650626?jaehuid=200008154&service_id=pcdn"));
            vendors4_1.add(new Vendor("11번가", 1259460, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2573889876&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors4_1.add(new Vendor("쿠팡", 1259460, 0, "https://www.coupang.com/vp/products/1073157144?itemId=2022232165&vendorItemId=70021898634&src=1032034&spec=10305201&addtag=400&ctag=1073157144&lptag=P1073157144&itime=20200614184430&pageType=PRODUCT&pageValue=1073157144&wPcid=15921278701852654063169&wRef=prod.danawa.com&wTime=20200614184430&redirect=landing&isAddedCart="));
            list4.add(new Item(user_key, "GF75 Thin 9SC", "MSI", "GF 시리즈", "", "85",
                    new Hardware(50, 60, 90, 100, 90, 80),
                    new Review(40, 50, 100, 100, 90, 70), vendors4_1));
            // 블레이드
            List<Vendor> vendors4_2 = new ArrayList<>();
            vendors4_2.add(new Vendor("11번가", 1899000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2885864840&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors4_2.add(new Vendor("G마켓", 2239050, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1822143684&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors4_2.add(new Vendor("옥션", 2337470, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B862342031&frm3=V2"));
            vendors4_2.add(new Vendor("위메프", 2390000, 0, "https://front.wemakeprice.com/product/1112487790?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=pcdn"));
            list4.add(new Item(user_key, "Base 9Gen", "Razer", "블레이드", "", "90",
                    new Hardware(50, 90, 90, 100, 100, 70),
                    new Review(40, 100, 100, 100, 90, 60), vendors4_2));



            List<Item> list5 = new ArrayList<>();
            // swift3
            List<Vendor> vendors5_1 = new ArrayList<>();
            vendors5_1.add(new Vendor("11번가", 969000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2856045833&service_id=pcdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors5_1.add(new Vendor("G마켓", 979200, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1825144276&GoodsSale=Y&jaehuid=200002657&service_id=pcdn"));
            vendors5_1.add(new Vendor("옥션", 979200, 0, "http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=B862662726&frm3=V2"));
            list5.add(new Item(user_key, "SF314-42", "Acer", "스위프트 3", "", "70",
                    new Hardware(80, 90, 60, 70, 80, 40),
                    new Review(60, 70, 80, 80, 70, 70), vendors5_1));
            // 아이디어패드
            List<Vendor> vendors5_2 = new ArrayList<>();
            vendors5_2.add(new Vendor("11번가", 349000, 0, "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2477919616&service_id=elecdn&utm_term=&utm_campaign=%B4%D9%B3%AA%BF%CDpc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B4%D9%B3%AA%BF%CD_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3"));
            vendors5_2.add(new Vendor("G마켓", 349000, 0, "http://item.gmarket.co.kr/DetailView/Item.asp?goodscode=1608083857&GoodsSale=Y&jaehuid=200002657&service_id=elecdn"));
            vendors5_2.add(new Vendor("옥션", 358990, 0, "http://prod.danawa.com/info/?pcode=9470151&keyword=%EC%95%84%EC%9D%B4%EB%94%94%EC%96%B4%ED%8C%A8%EB%93%9C&cate=122582"));
            vendors5_2.add(new Vendor("위메프", 365120, 0, "https://front.wemakeprice.com/product/181523237?utm_source=danawa&utm_medium=PRICE_af&utm_campaign=null&service_id=elecdn"));
            vendors5_2.add(new Vendor("티몬", 407500, 0, "http://www.tmon.co.kr/deal/1889186482?coupon_srl=2562278&utm_source=danawa&utm_medium=affiliate&utm_term=205009_%EB%8B%A4%EB%82%98%EC%99%80DB&utm_content=&utm_campaign=%EB%8B%A4%EB%82%98%EC%99%80"));
            list5.add(new Item(user_key, "D330-10IGM","Lenovo", "아이디어패드", "", "65",
                    new Hardware(90, 80, 60, 50, 70, 75),
                    new Review(90, 60, 70, 50, 75, 60), vendors5_2));


            homeCards.add(new HomeCard(R.drawable.card1, "# 사무용 노트북", list1));
            homeCards.add(new HomeCard(R.drawable.card2, "# 게이밍 노트북", list2));
            homeCards.add(new HomeCard(R.drawable.card5, "# 가벼운 노트북", list3));
            homeCards.add(new HomeCard(R.drawable.card3, "# 디자이너를 위한 노트북", list4));
            homeCards.add(new HomeCard(R.drawable.card4, "# 동영상/음악 감상용 노트북", list5));
        }

        homeStackAdapter = new HomeStackAdapter(getActivity(),homeCards);
        cardStackView.setAdapter(homeStackAdapter);
        homeStackAdapter.updateData(homeCards);



        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                cardStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(cardStackView));
                break;
            case R.id.menu_up_down:
                cardStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardStackView));
                break;
            case R.id.menu_up_down_stack:
                cardStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(cardStackView));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemExpend(boolean expend) { }
}



/*
License on Card Stack View
Copyright 2016 Loopeer

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/